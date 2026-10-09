import java.io.*;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class GestionFicheros {

    public static void main (String []args) throws IOException {
        //  A1 Creación del fichero: crea un directorio llamado accesos/seguridad
        File directorio = new File("accesos/seguridad");

        // Si existe, mandar un aviso
        if(!directorio.isDirectory()){
            System.out.println("El directorio no existe");
            directorio.mkdirs();
            System.out.println("El directorio se ha creado" +  directorio.getAbsolutePath());
        }else {
            System.out.println("El directorio ya existe" + directorio.getAbsolutePath());
        }

        // A2  Genera un fichero llamado accesos.txt
        // Crear el fichero
        File txt= new File(directorio, "acceso.txt");

        // Crear el fichero en el disco
        // Comprobamos si existe el fichero: si no existe lo cramos y si existe, lo indicamos con un mensaje
        try {
            if(!txt.exists()){
                System.out.println("El archivo log no existe");
                txt.createNewFile();
                System.out.println("El archivo log se ha creado" +  txt.getAbsolutePath());
            }else {
                System.out.println("El directorio ya existe" + directorio.getAbsolutePath());
            }
        }catch (IOException e) {
            System.out.println("ERROR AL CREAR EL FICHERO" + e.getMessage());
            throw new RuntimeException(e);
        }

        // B1 Renombrar fichero
        // a) Crear objeto File con el nombre nuevo, en el mismo directorio
        File nombreNuevo = new File(directorio, "accesos_Galarza.txt");

        // c) Renombrear el fichero y comprobar si la operación ha funcionado
        if (txt.renameTo(nombreNuevo)) {
            System.out.println("Archivo renombrado: " + txt.getName() + " --> " + nombreNuevo.getName());
        }else{
            System.out.println("ERROR: no se a podido renombrar el archivo.");
        }

        // C1  Escritura en el fichero
        // Creamos un objeto con el que se crea el archivo a escribir
        FileWriter seguridad = new FileWriter(txt);

        // Insertamos el contenido dentro del archivo se seguridad junto con su separador de linea
        seguridad.write("[INFO] Inicio de registro de actividad." + System.lineSeparator());
        seguridad.write("[INFO] Registro generado correctamente.");

        seguridad.close();
        System.out.println("Documento escrito correctamente.");

        // D1 Eliminación
        // Borrar el fichero YA RENOMBRADO y comprobar si se ha borrado
        if(nombreNuevo.delete()) {
            System.out.println("Eliminación correcta");
        }else{
            System.out.println("ERROR: no se a podido eliminar.");
        }
    }
}
