package org.example;

import java.sql.*; // java.sql = API JDBC (viene con Java). El DRIVER (mysql-connector-j) va en el pom.xml

/**
 * =====================================================================
 *  TAREA 1 - CONECTARSE A MYSQL (lo más básico de JDBC)
 * =====================================================================
 *  QUÉ HACE: abre una conexión con la BD "ut2", dice si ha ido bien
 *            y la cierra.
 *
 *  CLAVES PARA EL EXAMEN:
 *   - DriverManager.getConnection(URL, USER, PASSWORD) -> devuelve un Connection.
 *   - URL JDBC:  jdbc : mysql : //localhost:3306 / ut2
 *                proto  gestor   servidor:puerto   base de datos
 *   - Si falla lanza SQLException (servidor apagado, contraseña mal,
 *     driver no está en el pom -> "No suitable driver found").
 *   - Aquí se cierra la conexión con FINALLY (forma "clásica", larga).
 *     La forma moderna es try-with-resources (ver Tarea 2 en adelante).
 *   - Ya NO hace falta Class.forName("com.mysql.cj.jdbc.Driver") (desde JDBC 4.0).
 *
 * @author Juan Gabriel Galarza Claros
 */
public class Conector {

    // Datos de conexión como constantes (static final)
    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection connection = null; // se declara fuera del try para poder cerrarla en finally
        try {
            // 1) Abrir la conexión. DriverManager busca el driver que entiende "jdbc:mysql"
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Conexión a la base de datos ut2 correcta.");
            }
        } catch (SQLException e) {
            // 2) Error al conectar
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            // 3) finally se ejecuta SIEMPRE (haya error o no) -> liberar recursos
            if (connection != null) {
                try {
                    connection.close(); // close() también puede lanzar SQLException
                    System.out.println("Conexión cerrada.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
