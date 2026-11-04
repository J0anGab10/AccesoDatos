package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class ConsultaSELECT {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Consulta que queremos lanzar (sin variables, por eso vale Statement)
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE FROM agentes";

        // Abrimos conexión, Statement y ResultSet; los tres se cierran solos al acabar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Creamos el Statement para poder enviar la consulta
             Statement statement = connection.createStatement();
             // SELECT se ejecuta con executeQuery() y devuelve las filas en un ResultSet
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Contador para saber cuántos agentes hay (detalle extra)
            int contadorAgentes = 0;

            System.out.println("--- Listado de Agentes ---");

            // next() pasa a la siguiente fila; devuelve false cuando no hay más
            while (resultSet.next()) {
                // Leemos cada columna de la fila actual con el get del tipo correcto
                int codigoAgente = resultSet.getInt("CODIGO_AGENTE");
                String nombreAgente = resultSet.getString("NOMBRE_AGENTE");
                String fraseClave = resultSet.getString("FRASE_CLAVE");

                // Mostramos la fila con el formato que pide el enunciado
                System.out.println("Código Agente: " + codigoAgente +
                        ", Nombre Agente: " + nombreAgente +
                        ", Frase Clave: " + fraseClave);

                // Sumamos uno al contador
                contadorAgentes++;
            }

            // Mostramos el total al final
            System.out.println("--------------------------");
            System.out.println("Total de agentes recuperados: " + contadorAgentes);

        } catch (SQLException e) {
            // Error de la BD
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error
            System.err.println("Error inesperado: " + e.getMessage());
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
