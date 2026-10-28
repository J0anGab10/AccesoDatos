package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class ModificarClientesResultSet {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Deshabilitar auto-commit
            connection.setAutoCommit(false);

            // Configurar desplazamiento bidireccional y actualización
            try (Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {

                ResultSet consulta = stmt.executeQuery("SELECT * FROM clientes");
                int nFil = 0;

                // Recorrer el ResultSet
                while (consulta.next()) {
                    // Comprobar si el CP es nulo
                    if (consulta.getString("CP") == null) {
                        consulta.updateString("CP", "00000"); // Actualizar valor
                        consulta.updateRow();                 // Guardar base de datos
                        nFil++;
                    }
                }

                // Confirmar la transacción para guardar todos los cambios
                connection.commit();

                // Mostrar el resultado en consola
                String resultado = "Filas modificadas: " + nFil;
                System.out.println(resultado);

                JOptionPane.showMessageDialog(null, resultado);

            } catch (SQLException e) {
                // Deshacer cambios en caso de error
                connection.rollback();
                muestraErrorSQL(e);
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Metodo para mostrar detalles del error SQL
    private static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje de error: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error: " + e.getErrorCode());
    }
}
