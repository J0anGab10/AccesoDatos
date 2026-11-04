package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * =====================================================================
 *  TAREA 3 - CONSULTA SELECT CON Statement Y RECORRER UN ResultSet
 * =====================================================================
 *  QUÉ HACE: lee todos los agentes de la tabla "agentes" y los muestra.
 *
 *  CLAVES PARA EL EXAMEN:
 *   - SELECT -> statement.executeQuery(sql) -> devuelve un ResultSet (las filas).
 *   - El ResultSet tiene un CURSOR que empieza ANTES de la 1ª fila.
 *     rs.next() avanza una fila y devuelve false cuando no quedan
 *     -> por eso SIEMPRE:  while (rs.next()) { ... }
 *   - Leer columnas de la fila actual: getInt("COL"), getString("COL"),
 *     getDouble, getDate... (por nombre o por posición, empezando en 1).
 *   - try-with-resources con 3 recursos: Connection, Statement, ResultSet.
 *     Se cierran solos en orden inverso.
 *   - Por defecto el ResultSet es TYPE_FORWARD_ONLY (solo hacia delante)
 *     y CONCUR_READ_ONLY (solo lectura).
 *
 * @author Juan Gabriel Galarza Claros
 */
public class ConsultaSELECT {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Consulta fija (sin variables) -> basta con Statement
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE FROM agentes";

        // Los 3 recursos en el try: se abren en orden y se cierran al revés
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) { // SELECT -> executeQuery

            int contadorAgentes = 0; // extra: contar filas

            System.out.println("--- Listado de Agentes ---");

            // Recorrer fila a fila. next() = "¿hay otra fila? pues ponte en ella"
            while (resultSet.next()) {
                // getXXX según el tipo de la columna
                int codigoAgente = resultSet.getInt("CODIGO_AGENTE");
                String nombreAgente = resultSet.getString("NOMBRE_AGENTE");
                String fraseClave = resultSet.getString("FRASE_CLAVE");

                System.out.println("Código Agente: " + codigoAgente +
                        ", Nombre Agente: " + nombreAgente +
                        ", Frase Clave: " + fraseClave);

                contadorAgentes++;
            }

            System.out.println("--------------------------");
            System.out.println("Total de agentes recuperados: " + contadorAgentes);

        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /** Muestra mensaje, SQLState y código de error del fabricante. */
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("--- Se ha producido un error SQL ---");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
        System.err.println("------------------------------------");
    }
}
