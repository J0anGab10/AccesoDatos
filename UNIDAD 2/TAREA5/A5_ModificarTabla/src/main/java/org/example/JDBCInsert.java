package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class JDBCInsert {

// Constantes de conexión a la base de datos
private static final String URL = "jdbc:mysql://localhost:3306/ut2";
private static final String USER = "dam";
private static final String PASSWORD = "123456";

    public static void main(String[] args) {
// Se omite la declaración explícita de variables para los datos de conexión
        try (
                Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement s = c.createStatement()
        ) {
// Ejecución de sentencias INSERT con múltiples valores
            int nFil = s.executeUpdate(
                    "INSERT INTO clientes (DNI, APELLIDOS, CP) VALUES " +
                            "('78901234X', 'NADALES', '44126'), " +
                            "('89012345E', 'HOJAS', null), " +
                            "('56789012B', 'SAMPER', '29730'); "
            );

            JOptionPane.showMessageDialog(null, "Filas insertadas: " + nFil);
        } catch (SQLException e) {
            muestraErrorSQL(e);
            System.err.println("Código de error SQL específico: " + e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

public static void muestraErrorSQL(SQLException e) {
    System.err.println("--- Se ha producido un error SQL ---");
    System.err.println("Mensaje: " + e.getMessage());
    System.err.println("Estado SQL: " + e.getSQLState());
    System.err.println("Código de error del proveedor: " + e.getErrorCode());
    System.err.println("------------------------------------");
}
}

