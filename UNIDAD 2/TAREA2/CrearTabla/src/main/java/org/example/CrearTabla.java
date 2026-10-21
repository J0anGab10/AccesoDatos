package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class CrearTabla {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Sentencia SQL para crear la tabla clientes (sin usar IF NOT EXISTS)
        String sql = "CREATE TABLE clientes ("
                + "DNI CHAR(9) NOT NULL PRIMARY KEY, "
                + "APELLIDOS VARCHAR(32) NOT NULL, "
                + "CP CHAR(5) NULL"
                + ")";

        // try-with-resources: la conexión y el Statement se cierran solos al salir del bloque
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Las sentencias DDL
            statement.execute(sql);
            System.out.println("¡Tabla 'clientes' creada correctamente en la base de datos!");

        } catch (SQLException e) {
            // Gestión de errores SQL utilizando el método requerido
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Captura de cualquier otra excepción general
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
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

