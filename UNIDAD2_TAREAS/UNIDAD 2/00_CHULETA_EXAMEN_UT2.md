# UT2 – Manejo de conectores (JDBC) · Chuleta de examen

## Mapa de las tareas

| Carpeta | Qué se practica | Método clave |
|---|---|---|
| T1_Conexion_DriverManager | Abrir y cerrar conexión (con `finally`) | `DriverManager.getConnection()` |
| T2_CrearTabla_DDL_execute | CREATE TABLE (DDL) | `execute()` |
| T3_SELECT_Statement_ResultSet | SELECT y recorrer filas | `executeQuery()` + `while (rs.next())` |
| T4_SELECT_PreparedStatement | SELECT con parámetro `?` | `prepareStatement()` + `setInt()` |
| T5_INSERT_executeUpdate | INSERT (DML) | `executeUpdate()` → nº filas |
| T6_ResultSet_Actualizable_Transaccion | Modificar con ResultSet + transacción | `updateString()` + `updateRow()` + `commit()/rollback()` |
| T7_ProcedimientoAlmacenado_CallableStatement | AVG/MONTH + procedimiento IN/OUT | `prepareCall()` + `registerOutParameter()` |

## ¿Qué método uso? (pregunta típica)

| Sentencia | Objeto | Método | Devuelve |
|---|---|---|---|
| CREATE / ALTER / DROP (DDL) | Statement | `execute(sql)` | boolean |
| SELECT | Statement | `executeQuery(sql)` | ResultSet |
| INSERT / UPDATE / DELETE | Statement | `executeUpdate(sql)` | int (filas afectadas) |
| Cualquiera con variables | PreparedStatement | igual pero **sin** sql en el paréntesis | |
| Procedimiento almacenado | CallableStatement | SELECT→`executeQuery()`, modifica→`executeUpdate()`, con OUT→`execute()` | |

## Esqueleto que sirve para casi todo

```java
private static final String URL = "jdbc:mysql://localhost:3306/ut2";
private static final String USER = "dam";
private static final String PASSWORD = "123456";

try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
     PreparedStatement ps = con.prepareStatement("SELECT ... WHERE id = ?")) {
    ps.setInt(1, 7);                       // el primer ? es el 1
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {                // cursor empieza ANTES de la 1ª fila
            rs.getString("COLUMNA");
        }
    }
} catch (SQLException e) {
    e.getMessage(); e.getSQLState(); e.getErrorCode();
}
```

URL: `jdbc` (protocolo) : `mysql` (subprotocolo/gestor) : `//localhost:3306` (servidor:puerto) / `ut2` (BD).
SQLite (embebido): `jdbc:sqlite:agenda.db`, sin usuario ni contraseña.

## Ideas sueltas que suelen caer

- **try-with-resources**: lo declarado en `try (...)` se cierra solo, en orden inverso. Sustituye al `finally` (T1 y T6 usan `finally`, el resto try-with-resources). Criterio de evaluación “eliminar los objetos tras su uso”.
- **SQLException**: `getMessage()` (texto), `getSQLState()` (5 caracteres, estándar), `getErrorCode()` (del fabricante: 1050 tabla ya existe, 1062 clave duplicada).
- **Errores de conexión**: *Communications link failure* (servidor apagado/puerto), *Access denied* (usuario/contraseña), *No suitable driver* (falta driver en pom o URL mal).
- **`Class.forName(...)`** ya no hace falta desde JDBC 4.0.
- **El SQL va en un String**: el compilador no lo revisa, los errores salen al ejecutar.
- **PreparedStatement**: evita inyección SQL (`' OR '1'='1`) y reutiliza el plan de ejecución. El `?` va sin comillas. `setNull(pos, Types.INTEGER)` para NULL.
- **ResultSet**: tipo `TYPE_FORWARD_ONLY` (defecto) / `SCROLL_INSENSITIVE` / `SCROLL_SENSITIVE` (MySQL lo trata como INSENSITIVE). Concurrencia `CONCUR_READ_ONLY` (defecto) / `CONCUR_UPDATABLE`. Con SCROLL: `previous, first, last, absolute(n), beforeFirst, afterLast, getRow`.
- **Actualizar con ResultSet**: situar cursor → `updateXXX` → `updateRow()`. Borrar `deleteRow()`. Insertar `moveToInsertRow()` + `updateXXX` + `insertRow()`. En MySQL: una tabla, sin funciones y con clave primaria.
- **Procedimientos**: IN / OUT / INOUT. `{call Nombre(?, ?)}`. `registerOutParameter` ANTES de ejecutar, `getXXX(pos)` DESPUÉS. `DELIMITER //` al crearlos en phpMyAdmin. SQLite no los tiene.
- **Transacciones**: `setAutoCommit(false)` → operaciones → `commit()` / `rollback()`. ACID = Atomicidad, Consistencia, Aislamiento, Durabilidad. Ejemplo típico: transferencia bancaria.
- **Dinero → BigDecimal**, no double (0.1 + 0.2 = 0.30000000000000004).

## Teoría

- **Desfase objeto-relacional**: BD en tablas vs Java en objetos. 3 aspectos: paradigma (tabla↔clase, fila↔objeto, clave ajena↔referencia), tipos de datos (una columna no guarda una `List`), doble modelo (E/R y diagrama de clases). Soluciones: ORM (Hibernate/JPA, UT3) y BD orientadas a objetos (UT4).
- **Conector**: conjunto de clases que implementan la API de acceso a una BD. El **driver** traduce las llamadas estándar al protocolo de cada gestor.
- **ODBC** (Microsoft, API para C) y **JDBC** (Sun/Oracle, Java). El puente JDBC-ODBC se eliminó en Java 8.
- **Componentes JDBC**: API (`java.sql`, `javax.sql`), DriverManager, drivers, suite de pruebas.
- **Tipos de driver**: 1 puente JDBC-ODBC (eliminado) · 2 parcialmente nativo · 3 middleware (Java puro + servidor intermedio) · **4 protocolo nativo, Java puro, el mejor** (MySQL Connector/J).
- **Arquitectura**: 2 capas (app ↔ BD, la de clase) y 3 capas (app ↔ servidor intermedio ↔ BD: más seguridad, escalabilidad, separación).
- **Ventajas conectores**: independencia del gestor, estándar, control del SQL, base de los ORM. **Inconvenientes**: SQL en Strings sin revisar, dialectos distintos, no resuelve el desfase, código repetitivo.
- **Elegir conector**: el del gestor (MySQL → Connector/J, SQLite → sqlite-jdbc), tipo 4 del fabricante, versión compatible.
- **Embebido vs independiente**: embebido (SQLite, H2, Derby) va dentro de la app en un fichero, normalmente 1 usuario; independiente (MySQL, PostgreSQL, Oracle) es un servidor por red, muchos usuarios.
- **Procedimientos – ventajas**: rendimiento, reutilización, seguridad. **Inconvenientes**: dependen del gestor, lógica repartida entre Java y BD.
