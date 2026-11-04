package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class Examen1 {

    // Datos de conexión a la BD ut2
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {

        // 1) CREAREMOS EN ESTE BLOQUE LA TABLA

        // Sentencia SQL que crea la tabla (ojo con los espacios al concatenar)
        String sql = "CREATE TABLE examen ("
                + "DNI CHAR(9) NOT NULL PRIMARY KEY, "   // clave primaria: no se puede repetir
                + "APELLIDOS VARCHAR(32) NOT NULL, "     // obligatorio
                + "NOTA DECIMAL(5,2) NOT NULL"           // puede estar vacío
                + ")";

        // try-with-resources: la conexión y el Statement se cierran solos al acabar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // Statement: objeto que sirve para enviar sentencias SQL
             Statement statement = connection.createStatement()) {

            // CREATE TABLE es DDL, por eso usamos execute()
            statement.execute(sql);
            System.out.println("¡Tabla 'examen' creada correctamente en la base de datos!");

            // AHORA INSERTAMOS DATOS EN LA TABLA
            // INSERT se ejecuta con executeUpdate(), que devuelve cuántas filas ha insertado
            int nFil = statement.executeUpdate(
                    "INSERT INTO examen (DNI, APELLIDOS, NOTA) VALUES " +
                            "('78878234X', 'GALARZA CLAROS', '8,00')"
            );
            System.out.println("Número de filas insertadas = " + nFil);


        } catch (SQLException e) {
            // Error de la BD (por ejemplo, si la tabla ya existe)
            muestraErrorSQL(e);
        } catch (Exception e) {
            // Cualquier otro error no previsto
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    // Muestra la información del error SQL
    public static void muestraErrorSQL(SQLException e) {
        System.out.println("\n--- ERROR DE BASE DE DATOS (SQLException) ---");
        // Texto que explica el error
        System.out.println("Mensaje de error: " + e.getMessage());
        // Código estándar del error (igual en todos los gestores)
        System.out.println("Estado SQL (SQLState): " + e.getSQLState());
        // Código propio de MySQL (ej: 1050 = la tabla ya existe)
        System.out.println("Código de error del proveedor: " + e.getErrorCode());
        System.out.println("----------------------------------------------\n");
    }
}