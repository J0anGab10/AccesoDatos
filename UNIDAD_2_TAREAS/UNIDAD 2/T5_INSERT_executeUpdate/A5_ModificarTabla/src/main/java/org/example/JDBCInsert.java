package org.example;

// Para la ventana emergente (JOptionPane)
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class JDBCInsert {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Abrimos conexión y Statement; se cierran solos al acabar
        try (
                Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement s = c.createStatement()
        ) {
            // INSERT se ejecuta con executeUpdate(), que devuelve cuántas filas ha insertado
            int nFil = s.executeUpdate(
                    "INSERT INTO clientes (DNI, APELLIDOS, CP) VALUES " +
                            "('78901234X', 'NADALES', '44126'), " +
                            "('89012345E', 'HOJAS', null), " +      // null sin comillas = campo vacío
                            "('56789012B', 'SAMPER', '29730')"
            );

            // Mostramos en una ventana cuántas filas se insertaron
            JOptionPane.showMessageDialog(null, "Filas insertadas: " + nFil);
        } catch (SQLException e) {
            // Error de la BD (ej: DNI repetido si se ejecuta dos veces)
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            e.printStackTrace(System.err);
        }
    }

    // Muestra mensaje, estado SQL y código de error
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("--- Se ha producido un error SQL ---");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
        System.err.println("------------------------------------");
    }
}
