package org.example;

import java.sql.*;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class Examen2 {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        // Codigo llamada buscar
        int codigoLlamadaBuscar = 1000054;

        // Consulta que queremos lanzar
        String sql1 = "SELECT NUMERO_LLAMADO FROM LLAMADAS_EMITIDAS WHERE CODIGO_LLAMDA = 1000017";
        String sql2 = "SELECT DURACION_LLAMADA FROM LLAMADAS_EMITIDAS WHERE CODIGO_LLAMDA = ?";

        // Abrimos conexión, Statement y ResultSet; los tres se cierran solos al acabar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Creamos el Statement para poder enviar la consulta
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,   // moverse en ambas direcciones
                     ResultSet.CONCUR_UPDATABLE);
             // SELECT se ejecuta con executeQuery() y devuelve las filas en un ResultSet
             ResultSet resultSet = statement.executeQuery(sql1)) {

            System.out.println("Número de telefono = " + resultSet);

            ResultSet consulta = statement.executeQuery("SELECT CODIGO_LLAMADA, LLAMADAS_EMITIDAS, DURACION_LLAMDA FROM LLAMADAS_EMITIDAS WHERE CODIGO_LLAMADA = 1000054");
            // Contador de filas cambiadas
            int nFil = 0;

            // Recorremos todas las filas
            while (consulta.next()) {
                // Si el CP está vacío (NULL)...
                if (consulta.getInt("CODIGO_LLAMADA") == 1000054) {
                    // ...le ponemos "111" (de momento solo en el ResultSet)
                    consulta.updateString("CODIGO_LLAMADA", "111");
                    // updateRow() manda el cambio a la base de datos
                    consulta.updateRow();
                    // Sumamos uno al contador
                    nFil++;
                }
            }
            System.out.println("Número de modificaciones = " + nFil);
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // prepareStatement recibe la SQL con el ? ya escrita
             PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {

            // Rellenamos el primer ? (posición 1) con el código a buscar
            preparedStatement.setInt(1, codigoLlamadaBuscar);

            // Ejecutamos (sin pasar la SQL, ya la tiene) en otro try para que el ResultSet se cierre solo
            try (ResultSet resultado = preparedStatement.executeQuery()) {

                if (resultado.next()) {
                    System.out.println("Código llamada: " + resultado.getInt("CODIGO_LLAMDA")
                            + ", Duración llamada: " + resultado.getInt("DURACION_LLAMADA"));
                } else {
                    System.out.println("No existe ninguna llamada con código " + codigoLlamadaBuscar + ".");
                }
            }
        }
        catch (SQLException e) {
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