package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearTablaClima {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Las 6 columnas del CSV en el mismo orden
        String sql = "CREATE TABLE datos_climaticos ("
                + "Provincia VARCHAR(20), "          // texto corto
                + "Estacion VARCHAR(15), "           // texto corto (igual que en el procedimiento)
                + "Fecha DATE, "                     // fecha, permite usar MONTH()
                + "Temperatura DECIMAL(5,2), "       // número con 2 decimales exactos
                + "Humedad DECIMAL(5,2), "           // número con 2 decimales exactos
                + "Precipitacion DECIMAL(6,2)"       // mm de lluvia con decimales
                + ")";

        // Abrimos conexión y Statement; se cierran solos al acabar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // CREATE TABLE es DDL, por eso usamos execute()
            statement.execute(sql);
            System.out.println("Tabla 'datos_climaticos' creada correctamente.");

        } catch (SQLException e) {
            // Error de la BD (ej: la tabla ya existe)
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Muestra mensaje, estado SQL y código de error
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
