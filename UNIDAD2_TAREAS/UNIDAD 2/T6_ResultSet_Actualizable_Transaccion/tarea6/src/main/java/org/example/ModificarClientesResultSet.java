package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * =====================================================================
 *  TAREA 6 - MODIFICAR DATOS CON UN ResultSet ACTUALIZABLE + TRANSACCIÓN
 * =====================================================================
 *  QUÉ HACE: recorre "clientes" y a los que tienen CP = NULL les pone "00000",
 *            SIN escribir ningún UPDATE. Todo dentro de una transacción.
 *
 *  CLAVES PARA EL EXAMEN:
 *   - createStatement(TIPO, CONCURRENCIA):
 *       TIPO:  TYPE_FORWARD_ONLY (defecto, solo avanza)
 *              TYPE_SCROLL_INSENSITIVE (adelante/atrás, "foto" fija)
 *              TYPE_SCROLL_SENSITIVE  (adelante/atrás y ve cambios)
 *              -> OJO: MySQL no soporta SENSITIVE, lo trata como INSENSITIVE.
 *       CONCURRENCIA: CONCUR_READ_ONLY (defecto) / CONCUR_UPDATABLE (se puede modificar)
 *   - Con SCROLL se puede usar: previous(), first(), last(), absolute(n),
 *     beforeFirst(), afterLast(), getRow().
 *   - Modificar una fila (3 pasos):
 *       1) situar el cursor (next(), absolute(n)...)
 *       2) rs.updateXXX("COL", valor)  -> cambia SOLO en el ResultSet
 *       3) rs.updateRow()              -> lo manda a la BD
 *     Borrar: deleteRow(). Insertar: moveToInsertRow() + updateXXX() + insertRow().
 *   - MySQL: para que sea actualizable la SELECT debe ser de UNA tabla,
 *     sin funciones (SUM, COUNT...) e incluir la CLAVE PRIMARIA.
 *     Si no: "Result Set not updatable".
 *
 *   TRANSACCIÓN (todo o nada, propiedades ACID):
 *       1) connection.setAutoCommit(false)  -> empieza la transacción
 *       2) operaciones
 *       3) connection.commit()   si todo va bien
 *          connection.rollback() si hay error (deshace todo)
 *
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

            // TRANSACCIÓN paso 1: desactivar auto-commit (ya no se guarda cada cambio solo)
            connection.setAutoCommit(false);

            // ResultSet desplazable en ambas direcciones y ACTUALIZABLE
            try (Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,   // en MySQL = INSENSITIVE
                    ResultSet.CONCUR_UPDATABLE)) {     // permite updateXXX + updateRow

                // SELECT * incluye la clave primaria (DNI) -> requisito para poder actualizar
                ResultSet consulta = stmt.executeQuery("SELECT * FROM clientes");
                int nFil = 0;

                while (consulta.next()) {
                    if (consulta.getString("CP") == null) {     // CP vacío
                        consulta.updateString("CP", "00000");   // paso 2: cambiar en el ResultSet
                        consulta.updateRow();                   // paso 3: enviar a la BD
                        nFil++;
                    }
                }

                // TRANSACCIÓN paso 3a: todo bien -> confirmar cambios
                connection.commit();

                String resultado = "Filas modificadas: " + nFil;
                System.out.println(resultado);
                JOptionPane.showMessageDialog(null, resultado);

            } catch (SQLException e) {
                // TRANSACCIÓN paso 3b: error -> deshacer TODO lo hecho desde setAutoCommit(false)
                connection.rollback();
                muestraErrorSQL(e);
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            // Aquí la conexión se cierra "a la antigua" con finally
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /** Muestra mensaje, SQLState y código de error del fabricante. */
    private static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje de error: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error: " + e.getErrorCode());
    }
}
