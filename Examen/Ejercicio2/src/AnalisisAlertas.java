import java.io.*;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class AnalisisAlertas {

    public static void main (String[] args) throws IOException {

        // A) Lectura del fichero
        // Con FileReader leemos el archivo alertas.txt y con el Buffer lo almacenamos en la memoria
        FileReader leer = new FileReader("alertas.txt");
        BufferedReader buffer = new BufferedReader(leer);

        String texto;

        // Creamos un bucle con el que nos muestre las lineas por pantalla
        while ((texto = buffer.readLine()) != null) {
            System.out.println(texto);
        }
        // Cerramos el Buffer
        buffer.close();

        // B) Conteo de caracteres
        int contador = 0;
        File archivo = new File("alertas.txt");

        try {
            leer = new FileReader(archivo);
            buffer = new BufferedReader(leer);
            String linea;

            while ((linea = buffer.readLine()) != null) {
                String[] caracteres = linea.trim().split("\\s+");

                if (!linea.trim().isEmpty()) {
                    contador += caracteres.length;
                }
            }
            System.out.println("Número de caracteres: " + contador);
            buffer.close();
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }

        // C) Creacion del archivo nuevo y limpieza de contenido
        File archivoCopiado = new File("alertas_limpio.txt");
        try {
            leer = new FileReader(archivo);
            buffer = new BufferedReader(leer);

            FileWriter escribir = new FileWriter(archivoCopiado);
            BufferedWriter bufferEscritura = new BufferedWriter(escribir);

            String linea;
            // Bucle que escribe en el nuevo documento linea por linea
            while ((linea = buffer.readLine()) != null) {
                bufferEscritura.write(linea);
                bufferEscritura.newLine();
            }
            System.out.println("Copia creada correctamente.");

            // Cerramos los buffers
            buffer.close();
            bufferEscritura.close();
        } catch (IOException e) {
            System.out.println("Error al copiar el fichero: " + e.getMessage());
        }

        // D)  Acceso aleatorio

    }
}
