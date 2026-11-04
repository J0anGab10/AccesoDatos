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

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Fuera del try para poder cerrarla en el finally
        Connection connection = null;
        try {
            // Abrimos la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Empieza la transacción: los cambios no se guardan hasta hacer commit()
            connection.setAutoCommit(false);

            // Statement con 2 opciones: moverse adelante/atrás y poder modificar filas
            try (Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,   // moverse en ambas direcciones
                    ResultSet.CONCUR_UPDATABLE)) {     // permite modificar desde el ResultSet

                // SELECT * incluye la clave primaria (DNI), necesaria para poder modificar
                ResultSet consulta = stmt.executeQuery("SELECT * FROM clientes");
                // Contador de filas cambiadas
                int nFil = 0;

                // Recorremos todas las filas
                while (consulta.next()) {
                    // Si el CP está vacío (NULL)...
                    if (consulta.getString("CP") == null) {
                        // ...le ponemos "00000" (de momento solo en el ResultSet)
                        consulta.updateString("CP", "00000");
                        // updateRow() manda el cambio a la base de datos
                        consulta.updateRow();
                        // Sumamos uno al contador
                        nFil++;
                    }
                }

                // Todo ha ido bien: guardamos definitivamente los cambios
                connection.commit();

                // Mostramos el resultado en consola y en una ventana
                String resultado = "Filas modificadas: " + nFil;
                System.out.println(resultado);
                JOptionPane.showMessageDialog(null, resultado);

            } catch (SQLException e) {
                // Algo falló: deshacemos todos los cambios de la transacción
                connection.rollback();
                muestraErrorSQL(e);
            }
        } catch (SQLException e) {
            // Error al conectar
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            // Se ejecuta siempre: cerramos la conexión
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Muestra mensaje, estado SQL y código de error
    private static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje de error: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error: " + e.getErrorCode());
    }
}
