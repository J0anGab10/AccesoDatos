package org.example;

import java.beans.Statement;
import java.sql.*;

public class ConsultaSELECT {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.execute();

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                System.out.println("Nombre: " + nombre + ", Edad: " + edad);
            }

        } catch (Exception e) {
            // Captura de cualquier otra excepción general
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}
