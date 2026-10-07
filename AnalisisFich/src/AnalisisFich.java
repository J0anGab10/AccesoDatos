import java.io.*;

public class AnalisisFich {
    private static final String ORIGEN = "seguridad.txt";

    // Metodo lectura del archivo
    public static void mostrarContenido(String nombreFichero) throws IOException {
        FileReader leer = new FileReader(nombreFichero);
        BufferedReader buffer = new BufferedReader(leer);

        String linea;

        while ((linea = buffer.readLine()) != null) {
            System.out.println(linea);
        }
        buffer.close();
    }

    //Creamos el metodo que lee el archivo fichero y copia lina por linea en el ficheroCopia
    public static void crearCopia(String fichero, String ficheroCopia) throws IOException {
        FileWriter copia = new FileWriter(ficheroCopia);

        FileReader leer = new FileReader(fichero);
        BufferedReader buffer = new BufferedReader(leer);

        String linea;

        while ((linea = buffer.readLine()) != null) {
            copia.write(linea + System.lineSeparator());
        }
        buffer.close();
        copia.close();
    }

    public static void main (String[]args) throws IOException {

        // Primera lectura del archivo
        System.out.println(" ---> PRIMERA LECTURA DEL ARCHIVO <--- ");
        mostrarContenido(ORIGEN);


        // Escritura en el archivo de 2 nuevas lineas
        BufferedWriter escribir = new BufferedWriter(new FileWriter("seguridad.txt", true));

        escribir.write("[INFO] Nuevo intento de inicio de sesión" + System.lineSeparator());
        escribir.write("[WARNING] Contraseña incorrecta introducida tres veces" + System.lineSeparator());

        escribir.close();


        // Segunda lectura del archivo
        System.out.println(" ---> SEGUNDA LECTURA DEL ARCHIVO <--- ");
        mostrarContenido(ORIGEN);

        // Contador de lineas del archivo
        FileReader contar = new FileReader("seguridad.txt");
        BufferedReader lecturas = new BufferedReader(contar);

        String lies;
        int contador = 0;
        int totalCaracteres = 0;
        int totalPalabras = 0;

        while ((lies = lecturas.readLine()) != null) {
            contador ++;

            totalCaracteres += lies.length();

            String[] bloques = lies.trim().split("\\s+");
            totalPalabras += bloques.length;
        }
        System.out.println(
                "\nNúmero de lineas = " + contador +
                "\nNúmero de palabras = " + totalPalabras +
                "\nNúmero de caracteres = " + totalCaracteres
        );
        lecturas.close();


        // Llamamos al metodo que crea la copia de seguridad
        System.out.println("\n ---> ARCHIVO COPIADO CORRECTAMENTE <--- ");
        crearCopia(ORIGEN, "seguridad_copia.txt");
    }
}
