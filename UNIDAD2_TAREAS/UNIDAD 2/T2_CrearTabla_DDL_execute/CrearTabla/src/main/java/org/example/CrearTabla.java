package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * =====================================================================
 *  TAREA 2 - CREAR UNA TABLA DESDE JAVA (sentencia DDL)
 * =====================================================================
 *  QUÉ HACE: crea la tabla "clientes" (DNI, APELLIDOS, CP) en la BD ut2.
 *
 *  CLAVES PARA EL EXAMEN:
 *   - DDL = CREATE / ALTER / DROP  -> se ejecuta con  statement.execute(sql)
 *     (execute devuelve boolean: true si hay ResultSet, false si no -> aquí false)
 *   - Statement se obtiene con connection.createStatement()
 *   - try-with-resources: lo que se abre en los ( ) del try se CIERRA SOLO
 *     al salir, en orden inverso (Statement y luego Connection). No hace falta finally.
 *   - Si lo ejecutas 2 veces -> error "Table 'clientes' already exists"
 *     (SQLState 42S01, código MySQL 1050). Solución: CREATE TABLE IF NOT EXISTS.
 *   - muestraErrorSQL: los 3 datos de una SQLException:
 *        getMessage()   -> texto del error
 *        getSQLState()  -> código estándar de 5 caracteres (igual en todos los gestores)
 *        getErrorCode() -> código propio del fabricante (MySQL)
 *   - El SQL va en un String: el compilador NO lo revisa, el fallo sale al ejecutar.
 *
 * @author Juan Gabriel Galarza Claros
 */
public class CrearTabla {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Sentencia DDL. Ojo a los espacios al concatenar (",  " al final de cada línea)
        String sql = "CREATE TABLE clientes ("
                + "DNI CHAR(9) NOT NULL PRIMARY KEY, "  // clave primaria: no se repite
                + "APELLIDOS VARCHAR(32) NOT NULL, "
                + "CP CHAR(5) NULL"                     // puede quedar vacío (NULL)
                + ")";

        // try-with-resources: Connection y Statement se cierran solos
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // DDL -> execute()
            statement.execute(sql);
            System.out.println("¡Tabla 'clientes' creada correctamente en la base de datos!");

        } catch (SQLException e) {
            // Errores de BD (tabla ya existe, SQL mal escrito, sin conexión...)
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error no previsto
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /** Muestra los 3 datos de una SQLException (lo piden en todas las tareas). */
    public static void muestraErrorSQL(SQLException e) {
        System.out.println("\n--- ERROR DE BASE DE DATOS (SQLException) ---");
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL (SQLState): " + e.getSQLState());
        System.out.println("Código de error del proveedor: " + e.getErrorCode());
        System.out.println("----------------------------------------------\n");
    }
}
