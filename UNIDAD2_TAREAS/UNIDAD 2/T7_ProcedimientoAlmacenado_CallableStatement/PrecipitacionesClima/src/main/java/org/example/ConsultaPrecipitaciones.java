package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * =====================================================================
 *  TAREA 7 (parte 2) - CONSULTA AVG + PROCEDIMIENTO ALMACENADO (CallableStatement)
 * =====================================================================
 *  QUÉ HACE:
 *   1) Con Statement calcula la precipitación MEDIA de octubre   -> 8.19 mm
 *   2) Con CallableStatement llama a CalcularPrecipitacionTotal  -> 254.00 mm
 *   (antes hay que crear el procedimiento en phpMyAdmin: ver procedimiento.sql)
 *
 *  CLAVES PARA EL EXAMEN:
 *   - AVG(col)      -> función de agregado: media de la columna.
 *   - MONTH(Fecha)  -> saca el número de mes de un DATE (10 = octubre).
 *   - Procedimiento almacenado: código SQL guardado EN la BD. Desde Java solo se llama.
 *     Parámetros: IN (entrada), OUT (salida), INOUT (ambos).
 *   - CallableStatement cs = connection.prepareCall("{call Nombre(?, ?)}")
 *       · Sintaxis estándar JDBC con llaves, un ? por parámetro.
 *       · IN  -> cs.setString(1, ...)  (como PreparedStatement)
 *       · OUT -> cs.registerOutParameter(2, Types.DECIMAL)  ANTES de ejecutar
 *       · ejecutar con execute()  (hay parámetro OUT)
 *       · leer el OUT DESPUÉS con cs.getDouble(2) / getBigDecimal(2)...
 *   - Qué método de ejecución usar en un procedimiento:
 *       devuelve filas (SELECT)   -> executeQuery()  -> ResultSet
 *       modifica datos            -> executeUpdate() -> int
 *       tiene OUT o no se sabe    -> execute()
 *   - SQLite (embebido) NO tiene procedimientos almacenados.
 */
public class ConsultaPrecipitaciones {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            mostrarPrecipitacionMedia(connection);
            mostrarPrecipitacionTotal(connection, "Algemesi");
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    /** 1) Consulta normal con Statement: media de octubre en Algemesi. */
    public static void mostrarPrecipitacionMedia(Connection connection) throws SQLException {
        String sql = "SELECT AVG(Precipitacion) AS media FROM datos_climaticos "
                   + "WHERE MONTH(Fecha) = 10 AND Estacion = 'Algemesi'";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {   // SELECT -> executeQuery
            if (rs.next()) {                                  // AVG devuelve 1 sola fila
                double media = rs.getDouble("media");
                System.out.printf("Precipitación media en octubre: %.2f mm%n", media);
            }
        }
    }

    /** 2) Llamada al procedimiento almacenado con 1 parámetro IN y 1 OUT. */
    public static void mostrarPrecipitacionTotal(Connection connection, String estacion) throws SQLException {
        // prepareCall + sintaxis {call ...}
        try (CallableStatement cs = connection.prepareCall("{call CalcularPrecipitacionTotal(?, ?)}")) {

            cs.setString(1, estacion);                       // ?1 = IN  estacionNombre
            cs.registerOutParameter(2, Types.DECIMAL);       // ?2 = OUT totalPrecipitacion (registrar ANTES)

            cs.execute();                                    // con OUT -> execute()

            double total = cs.getDouble(2);                  // leer el OUT DESPUÉS de ejecutar
            System.out.printf("Precipitación total en octubre (%s): %.2f mm%n", estacion, total);
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
