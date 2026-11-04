package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * =====================================================================
 *  TAREA 4 - SELECT CON PARÁMETRO USANDO PreparedStatement
 * =====================================================================
 *  QUÉ HACE: busca el agente con CODIGO_AGENTE = 7 (Ethan Hunt).
 *            Si pones 99 -> "No existe ningún agente con código 99."
 *
 *  CLAVES PARA EL EXAMEN:
 *   - Cuando la consulta depende de una VARIABLE -> PreparedStatement, NO concatenar.
 *       MAL:  "... WHERE DNI='" + dni + "'"   -> inyección SQL (' OR '1'='1)
 *       BIEN: "... WHERE DNI = ?"  + ps.setString(1, dni)
 *   - 3 pasos:
 *       1) SQL con ? (sin comillas, aunque sea texto)
 *       2) connection.prepareStatement(sql)
 *       3) ps.setXXX(posición, valor)  (el primer ? es la posición 1) y ejecutar
 *   - executeQuery() va SIN parámetro (la SQL ya se dio en prepareStatement).
 *   - Ventajas: seguridad (evita inyección) y rendimiento (reutiliza el plan).
 *   - El ResultSet se abre en un 2º try-with-resources DENTRO del primero,
 *     porque antes de ejecutar hay que hacer el setInt().
 *   - Buscar UNA fila -> if (rs.next())  /  varias filas -> while (rs.next())
 *
 * @author Juan Gabriel Galarza Claros
 */
public class ConsultaPREPARED {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        int codigoAgenteBuscar = 7; // cambiar a 99 para probar "no encontrado"

        // 1) SQL con marcador de posición ?
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE "
                   + "FROM AGENTES WHERE CODIGO_AGENTE = ?";

        // 2) Conexión + PreparedStatement en el try-with-resources
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // 3) Dar valor al primer (y único) ?
            preparedStatement.setInt(1, codigoAgenteBuscar);

            // 4) Ejecutar. 2º try-with-resources para que el ResultSet también se cierre solo
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                // Como el código es clave primaria, como mucho hay 1 fila -> if
                if (resultado.next()) {
                    System.out.println("Código Agente: " + resultado.getInt("CODIGO_AGENTE")
                            + ", Nombre Agente: " + resultado.getString("NOMBRE_AGENTE")
                            + ", Frase Clave: " + resultado.getString("FRASE_CLAVE"));
                } else {
                    System.out.println("No existe ningún agente con código " + codigoAgenteBuscar + ".");
                }
            }

        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
        // Sin finally: los 3 objetos (Connection, PreparedStatement, ResultSet) se cierran solos
    }

    /** Muestra mensaje, SQLState y código de error del fabricante. */
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("--- Se ha producido un error SQL ---");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
