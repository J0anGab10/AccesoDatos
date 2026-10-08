/**
 * @author Juan Gabriel Galarza Claros
 */

import java.io.*;

public class GestorDocumentos {
    public static final String DIRECTORIO = "Biblioteca";

    public static void main (String []args) {
        String documento = "Documento.txt";

        crearFichero(DIRECTORIO);
        documento = renombrarDocumento(DIRECTORIO, documento, "libros.txt");
        escribirLibrosAdicionales(DIRECTORIO, documento);
        leerContenidoArchivo(DIRECTORIO, documento);
    }

    // Funciones a crear...

    // EJERCICIO 1 : Operaciones con archivos y direcotorios
    // Ejercicio 1.1
    public static void crearFichero(String nombre) {
        File directorio = new File(nombre);

        if (!directorio.exists()) {
            directorio.mkdir();
            System.out.println("Directorio creado con exito");
        }else {
            System.out.println("Error: El direcotorio ya existe");
        }
    }
    // Ejercicio 1.2
    public static String renombrarDocumento(String ruta, String nombre, String nuevoNombre) {

        File archivo = new File(ruta, nombre);
        File archivoRenombre = new File(ruta, nuevoNombre);

        if (archivo.exists()) {
            archivo.renameTo(archivoRenombre);
            System.out.println("Archivo renombrado correctamente a : " + nuevoNombre);
        }else {
            System.out.println("Error: No se a encontrado el archivo " + nombre);
        }
        return nuevoNombre;
    }

    // EJERCICIO 2 : Escribir y leer en el archivo
    // Ejercicio 2.1
    public static void escribirLibrosAdicionales(String ruta, String documento) {
        File archivo = new File(ruta, documento);

        try {
            FileWriter libros = new FileWriter(archivo, true);
            BufferedWriter buffer = new BufferedWriter(libros);

            buffer.newLine();
            buffer.write("Primer libro: Manuscrito Voynich");
            buffer.newLine();
            buffer.write("Segundo libro: El Necronomicón");
            buffer.newLine();
            buffer.write("Tercer libro: Codex Gigas");

            buffer.close();
        }catch (IOException e ) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Ejercicio 2.2
    public static void leerContenidoArchivo(String ruta, String documento) {
        File archivo = new File(ruta, documento);

        try {
            FileReader leer = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leer);

            String texto;
            while ((texto = buffer.readLine()) != null) {
                System.out.println(texto);
            }

            buffer.close();
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // EJERCICIO 3 : Operaciones adicionales sobre el archivo
    // Ejercicio 3.1
    public static void contarPalabras() {

    }


}
