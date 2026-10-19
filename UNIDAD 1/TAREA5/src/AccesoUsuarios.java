import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Juan Gabriel Galarza Claros
 */
public class AccesoUsuarios {
    // Nombre del archivo de datos
    private static final String ARCHIVO = "usuarios.dat";

    // Bytes reservados para el nombre
    private static final int TAM_NOMBRE = 10;

    // Bytes que ocupa cada registro: ID (3) + espacio (1) + nombre (10) + salto (1) = 15 bytes.
    private static final int TAM_REGISTRO = 3 + 1 + TAM_NOMBRE + 1;

    public static void main(String[] args) throws IOException {
        // 0) Crear y añadir los usuarios iniciales
        crearFichero();

        // 1) Mostrar contenido inicial
        System.out.println("--- CONTENIDO INICIAL DEL ARCHIVO ---");
        mostrarFichero();

        // 2) Lectura aleatoria: Leamos el usuario número 3
        System.out.println("\n--- LECTURA ALEATORIA ---");
        leerUsuario(3);

        // 3) Modificación de datos: Cambiar el nombre del usuario ID 2 a "Pedro"
        System.out.println("\n--- MODIFICACIÓN DE DATOS ---");
        modificarNombre(2, "Pedro");
        mostrarFichero();

        // 4) Añadir un nuevo registro: Añadir usuario (006 Ana) al final
        System.out.println("\n--- AÑADIR REGISTRO AL FINAL ---");
        añadirUsuario(6, "Ana");
        mostrarFichero();

        // 5) Eliminar un registro: Sobrescribir el usuario ID 4 con espacios
        System.out.println("\n--- ELIMINACIÓN DE REGISTRO ---");
        eliminiarUsuario(4);
        mostrarFichero();
    }

    /**
     * Formatea un registro a exactamente 15 bytes según la estructura:
     * %03d (ID 3 dígitos) + " " + %-10s (Nombre relleno a la derecha) + "\n"
     */
    private static String formatearRegistro(int id, String nombre) {
        if (nombre.length() > TAM_NOMBRE) {
            nombre = nombre.substring(0, TAM_NOMBRE);
        }
        return String.format("%03d %-10s\n", id, nombre);
    }

    /**
     * Crea el fichero 'usuarios.dat' con los 5 usuarios iniciales.
     */
    public static void crearFichero() throws IOException {
        File f = new File(ARCHIVO);
        if (f.exists()) {
            f.delete();
        }

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            raf.writeBytes(formatearRegistro(1, "Juan"));
            raf.writeBytes(formatearRegistro(2, "Maria"));
            raf.writeBytes(formatearRegistro(3, "Mila"));
            raf.writeBytes(formatearRegistro(4, "Abraham"));
            raf.writeBytes(formatearRegistro(5, "Carlos"));
        }
    }

    /**
     * Lee un usuario específico en base a su posición lógica (1-based index).
     */
    public static void leerUsuario(int posicion) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
            long bytePos = (long) (posicion - 1) * TAM_REGISTRO;

            if (bytePos >= raf.length()) {
                System.out.println("El registro especificado no existe.");
                return;
            }

            raf.seek(bytePos);
            String linea = raf.readLine();

            if (linea != null && linea.length() >= 14) {
                String id = linea.substring(0, 3);
                String nombre = linea.substring(4, 14).trim();
                System.out.println("Usuario en posición " + posicion + " [ID: " + id + "] -> Nombre: " + nombre);
            }
        }
    }

    /**
     * Modifica el nombre de un usuario por su posición/ID.
     */
    public static void modificarNombre(int posicion, String nuevoNombre) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            long bytePos = (long) (posicion - 1) * TAM_REGISTRO;

            if (bytePos >= raf.length()) {
                System.out.println("No se puede modificar: El registro no existe.");
                return;
            }

            raf.seek(bytePos);
            raf.writeBytes(formatearRegistro(posicion, nuevoNombre));
            System.out.println("Nombre del usuario " + posicion + " cambiado a: " + nuevoNombre);
        }
    }

    /**
     * Añade un nuevo usuario posicionándose directamente al final del fichero con seek().
     */
    public static void añadirUsuario(int id, String nombre) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(formatearRegistro(id, nombre));
            System.out.println("Usuario 00" + id + " (" + nombre + ") añadido al final.");
        }
    }

    /**
     * Simula la eliminación de un usuario sobrescribiendo su registro con espacios.
     */
    public static void eliminiarUsuario(int posicion) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            long bytePos = (long) (posicion - 1) * TAM_REGISTRO;

            if (bytePos >= raf.length()) {
                System.out.println("No se puede eliminar: Registro inexistente.");
                return;
            }

            raf.seek(bytePos);
            String borrado = "              \n"; // 14 espacios + \n
            raf.writeBytes(borrado);
            System.out.println("Registro en la posición " + posicion + " eliminado (sobrescrito).");
        }
    }

    /**
     * Muestra todo el contenido actual del archivo por consola.
     */
    public static void mostrarFichero() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
            raf.seek(0);
            String linea;
            int numLinea = 1;
            while ((linea = raf.readLine()) != null) {
                System.out.println("Reg " + numLinea + ": [" + linea + "]");
                numLinea++;
            }
        }
    }
}