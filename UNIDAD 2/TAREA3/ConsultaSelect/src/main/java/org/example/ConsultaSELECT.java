package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaSELECT {

    // Constantes de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Consulta original solicitada en el enunciado
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE FROM AGENTES;";

        // Apertura de recursos con try-with-resources para su cierre automático
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int contadorAgentes = 0; // Funcionalidad extra: contador de registros procesados

            System.out.println("--- Listado de Agentes ---");

            // Recorrer el conjunto de resultados
            while (resultSet.next()) {
                int codigoAgente = resultSet.getInt("CODIGO_AGENTE");
                String nombreAgente = resultSet.getString("NOMBRE_AGENTE");
                String fraseClave = resultSet.getString("FRASE_CLAVE");

                // Formato de impresión exacto exigido en la tarea
                System.out.println("Código Agente: " + codigoAgente +
                        ", Nombre Agente: " + nombreAgente +
                        ", Frase Clave: " + fraseClave);

                contadorAgentes++; // Incremento del detalle extra
            }

            // Impresión del detalle innovador
            System.out.println("--------------------------");
            System.out.println("Total de agentes recuperados: " + contadorAgentes);

        } catch (SQLException e) {
            // Captura de excepción SQL y llamada al método de error personalizado
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Captura de cualquier otra excepción no prevista
            System.err.println("Error inesperado: " + e.getMessage());
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