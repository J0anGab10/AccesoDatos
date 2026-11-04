package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * =====================================================================
 *  TAREA 5 - INSERTAR FILAS (DML de modificación: INSERT/UPDATE/DELETE)
 * =====================================================================
 *  QUÉ HACE: inserta 3 clientes en "clientes" con UNA sola sentencia
 *            y muestra cuántas filas se han insertado (en una ventana).
 *
 *  CLAVES PARA EL EXAMEN:
 *   - INSERT / UPDATE / DELETE -> statement.executeUpdate(sql)
 *     devuelve un INT = número de filas afectadas.
 *     (útil en UPDATE/DELETE: si devuelve 0, el WHERE no encontró nada)
 *   - Para meter NULL en SQL se escribe null sin comillas.
 *   - Si se ejecuta 2 veces -> error de clave primaria duplicada
 *     (Duplicate entry, código MySQL 1062).
 *   - Si los valores vinieran de variables -> usar PreparedStatement
 *     (INSERT ... VALUES (?, ?, ?) + setString/setInt + executeUpdate()).
 *   - JOptionPane.showMessageDialog = ventana emergente (Swing), es un extra.
 *
 *   RESUMEN DE MÉTODOS DE Statement:
 *     execute()        -> DDL (CREATE, ALTER, DROP)        -> boolean
 *     executeQuery()   -> SELECT                           -> ResultSet
 *     executeUpdate()  -> INSERT, UPDATE, DELETE           -> int (filas)
 *
 * @author Juan Gabriel Galarza Claros
 */
public class JDBCInsert {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // try-with-resources: Connection y Statement se cierran solos
        try (
                Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement s = c.createStatement()
        ) {
            // Un INSERT con varias filas -> executeUpdate devuelve 3
            int nFil = s.executeUpdate(
                    "INSERT INTO clientes (DNI, APELLIDOS, CP) VALUES " +
                            "('78901234X', 'NADALES', '44126'), " +
                            "('89012345E', 'HOJAS', null), " +      // CP vacío -> NULL
                            "('56789012B', 'SAMPER', '29730')"
            );

            JOptionPane.showMessageDialog(null, "Filas insertadas: " + nFil);
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /** Muestra mensaje, SQLState y código de error del fabricante. */
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("--- Se ha producido un error SQL ---");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
        System.err.println("------------------------------------");
    }
}
