package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * =====================================================================
 *  TAREA 7 (parte 1) - CREAR LA TABLA datos_climaticos (DDL, como la Tarea 2)
 * =====================================================================
 *  QUÉ HACE: crea la tabla vacía. Ejecutar UNA sola vez.
 *  Después: phpMyAdmin -> tabla -> Importar -> CSV -> omitir 1 línea (cabecera)
 *           -> comprobar que hay 31 filas.
 *
 *  TIPOS ELEGIDOS (6 columnas, mismo orden que el CSV):
 *   Provincia     VARCHAR(20)   texto corto de longitud variable
 *   Estacion      VARCHAR(15)   igual que el parámetro del procedimiento
 *   Fecha         DATE          lo pide el enunciado; permite usar MONTH(Fecha)
 *   Temperatura   DECIMAL(5,2)  decimales exactos (ej. 20.72)
 *   Humedad       DECIMAL(5,2)  (ej. 99.90)
 *   Precipitacion DECIMAL(6,2)  mm de lluvia (máx. del CSV: 169.4)
 *   -> DECIMAL mejor que FLOAT/DOUBLE porque guarda los decimales exactos.
 */
public class CrearTablaClima {

    private static final String URL = "jdbc:mysql://localhost:3306/ut2";
    private static final String USER = "dam";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        String sql = "CREATE TABLE datos_climaticos ("
                + "Provincia VARCHAR(20), "
                + "Estacion VARCHAR(15), "
                + "Fecha DATE, "
                + "Temperatura DECIMAL(5,2), "
                + "Humedad DECIMAL(5,2), "
                + "Precipitacion DECIMAL(6,2)"
                + ")";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute(sql); // DDL -> execute()
            System.out.println("Tabla 'datos_climaticos' creada correctamente.");

        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error del proveedor: " + e.getErrorCode());
    }
}
