import java.io.*;

public class LogActividad3 {
    static void main (String[]args) throws IOException {
        // Creamos un objeto con el que se crea el archivo a escribir
        FileWriter seguridad = new FileWriter("seguridad_actividad3.log");

        // Insertamos el contenido dentro del archivo se seguridad junto con su separador de linea
        seguridad.write("Intento de acceso fallido" + System.lineSeparator());
        seguridad.write("Usuario autenticado correctamente");

        seguridad.close();

        // Con FileReader leemos el archivo creado anteriormente y con el Buffer lo almacenamos en la memoria
        FileReader leer = new FileReader("seguridad_actividad3.log");
        BufferedReader buffer = new BufferedReader(leer);

        String texto;

        // Creamos un bucle con el que nos muestre las lineas por pantalla
        while ((texto = buffer.readLine()) != null) {
            System.out.println(texto);
        }
        // Cerramos el Buffer
        buffer.close();
    }
}
