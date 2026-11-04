package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Types sirve para indicar el tipo SQL del parámetro de salida
import java.sql.Types;

public class ConsultaPrecipitaciones {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Abrimos una conexión y la usamos en los dos métodos; se cierra sola
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 1º: media con una consulta normal
            mostrarPrecipitacionMedia(connection);
            // 2º: total llamando al procedimiento almacenado
            mostrarPrecipitacionTotal(connection, "Algemesi");
        } catch (SQLException e) {
            // Error de la BD
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Calcula la precipitación media de octubre con un Statement
    public static void mostrarPrecipitacionMedia(Connection connection) throws SQLException {
        // AVG = media; MONTH(Fecha) = 10 filtra solo octubre
        String sql = "SELECT AVG(Precipitacion) AS media FROM datos_climaticos "
                   + "WHERE MONTH(Fecha) = 10 AND Estacion = 'Algemesi'";

        // Statement y ResultSet se cierran solos al acabar
        try (Statement statement = connection.createStatement();
             // SELECT se ejecuta con executeQuery()
             ResultSet rs = statement.executeQuery(sql)) {
            // AVG devuelve una sola fila, por eso if
            if (rs.next()) {
                // Leemos la media por el alias "media"
                double media = rs.getDouble("media");
                // %.2f muestra el número con 2 decimales
                System.out.printf("Precipitación media en octubre: %.2f mm%n", media);
            }
        }
    }

    // Llama al procedimiento almacenado CalcularPrecipitacionTotal
    public static void mostrarPrecipitacionTotal(Connection connection, String estacion) throws SQLException {
        // prepareCall con {call Nombre(?, ?)}: un ? por cada parámetro del procedimiento
        try (CallableStatement cs = connection.prepareCall("{call CalcularPrecipitacionTotal(?, ?)}")) {

            // 1er ? es de entrada (IN): le damos el nombre de la estación
            cs.setString(1, estacion);
            // 2º ? es de salida (OUT): hay que registrarlo ANTES de ejecutar, con su tipo
            cs.registerOutParameter(2, Types.DECIMAL);

            // Con parámetro de salida se ejecuta con execute()
            cs.execute();

            // DESPUÉS de ejecutar, leemos el valor de salida
            double total = cs.getDouble(2);
            System.out.printf("Precipitación total en octubre (%s): %.2f mm%n", estacion, total);
        }
    }

    // Muestra mensaje, estado SQL y código de error
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
