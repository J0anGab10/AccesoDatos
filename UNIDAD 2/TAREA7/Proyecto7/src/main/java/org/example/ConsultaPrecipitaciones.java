package org.example;
import java.sql.*;

public class ConsultaPrecipitaciones {
    private static final String url = "jdbc:mysql://localhost:3306/ut2";
    private static final String user = "dam";
    private static final String password = "123456";

    public static void main(String[] args) {
        // 1. consulta de la precipitación media mensual con statement
        String consulta = "select avg(precipitacion) as media from datos_climaticos " +
                "where estacion = 'algemesi' and month(fecha) = 10;";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultset = statement.executeQuery(consulta)) {

            if (resultset.next()) {
                double media = resultset.getDouble("media");
                System.out.println("Precipitación media en octubre: " + media + " mm");
            }

        } catch (SQLException e) {
            muestraErrorSQL(e);
        }

        // 2. ejecución del procedimiento almacenado con callablestatement
        String procedimiento = "{call CalcularPrecipitacionTotal(?, ?)}";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             CallableStatement CalcularPrecipitacionTotal = connection.prepareCall(procedimiento)) {

            // definir parámetro de entrada
            CalcularPrecipitacionTotal.setString(1, "algemesi");

            // registrar parámetro de salida
            CalcularPrecipitacionTotal.registerOutParameter(2, Types.DECIMAL);

            // ejecutar el procedimiento
            CalcularPrecipitacionTotal.execute();

            // obtener el valor del parámetro de salida
            double totalPrecipitacion = CalcularPrecipitacionTotal.getDouble(2);
            System.out.println("precipitación total en octubre (procedimiento): " + totalPrecipitacion + " mm");

        } catch (SQLException e) {
            muestraErrorSQL(e);
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.out.println("\n--- ERROR DE BASE DE DATOS (SQLException) ---");
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL (SQLState): " + e.getSQLState());
        System.out.println("Código de error del proveedor: " + e.getErrorCode());
        System.out.println("----------------------------------------------\n");
    }
}