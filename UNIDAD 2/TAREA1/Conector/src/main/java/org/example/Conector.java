package org.example;

import java.sql.*;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class Conector {
    // ImportLibraries
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establecer conexión con la base de datos MySQL
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Conexión a la base de datos ut2 correcta.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}