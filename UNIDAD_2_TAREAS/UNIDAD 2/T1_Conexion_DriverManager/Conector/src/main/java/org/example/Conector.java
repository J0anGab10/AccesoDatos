package org.example;

// Importamos las clases de JDBC (Connection, DriverManager, SQLException...)
import java.sql.*;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class Conector {

    // Dirección de la BD: jdbc:mysql://servidor:puerto/nombreBD
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    // Usuario de MySQL
    private static final String USER = "dam";
    // Contraseña de MySQL
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // La declaramos fuera del try para poder cerrarla luego en el finally
        Connection connection = null;
        try {
            // Abrimos la conexión con la BD usando URL, usuario y contraseña
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Si no es null, la conexión ha funcionado
            if (connection != null) {
                System.out.println("Conexión a la base de datos ut2 correcta.");
            }
        } catch (SQLException e) {
            // Entra aquí si no se puede conectar (servidor apagado, contraseña mal...)
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            // finally se ejecuta siempre: lo usamos para cerrar la conexión
            if (connection != null) {
                try {
                    // Cerramos la conexión para liberar recursos
                    connection.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException ex) {
                    // close() también puede dar error, por eso otro try
                    ex.printStackTrace();
                }
            }
        }
    }
}
