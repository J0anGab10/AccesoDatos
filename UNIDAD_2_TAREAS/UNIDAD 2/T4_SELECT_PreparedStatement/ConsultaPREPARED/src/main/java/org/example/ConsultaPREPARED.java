package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class ConsultaPREPARED {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Código del agente que buscamos (poner 99 para probar que no existe)
        int codigoAgenteBuscar = 7;

        // El ? es un hueco que rellenaremos después (evita inyección SQL)
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE "
                   + "FROM AGENTES WHERE CODIGO_AGENTE = ?";

        // Abrimos conexión y PreparedStatement; se cierran solos al acabar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // prepareStatement recibe la SQL con el ? ya escrita
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Rellenamos el primer ? (posición 1) con el código a buscar
            preparedStatement.setInt(1, codigoAgenteBuscar);

            // Ejecutamos (sin pasar la SQL, ya la tiene) en otro try para que el ResultSet se cierre solo
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                // if y no while: como mucho hay un agente con ese código
                if (resultado.next()) {
                    // Si hay fila, mostramos sus datos
                    System.out.println("Código Agente: " + resultado.getInt("CODIGO_AGENTE")
                            + ", Nombre Agente: " + resultado.getString("NOMBRE_AGENTE")
                            + ", Frase Clave: " + resultado.getString("FRASE_CLAVE"));
                } else {
                    // Si no hay fila, el agente no existe
                    System.out.println("No existe ningún agente con código " + codigoAgenteBuscar + ".");
                }
            }

        } catch (SQLException e) {
            // Error de la BD
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Muestra mensaje, estado SQL y código de error
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("--- Se ha producido un error SQL ---");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
