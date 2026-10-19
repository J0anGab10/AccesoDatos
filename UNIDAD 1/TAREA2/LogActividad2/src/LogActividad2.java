import java.io.File;
import java.io.IOException;

public class LogActividad2 {
    static void main(String[]args) {

        // Paso 2: Crear el directorio de logs.
        // Comprobar si existe
        // Crearlo si no existe
        // a)Hemos creado el objeto File en Java, No el directorio.
        File directorio = new File("logs/seguridad");

        // b) Si existe, mandar un aviso
        if(!directorio.isDirectory()){
            System.out.println("El directorio no existe");
            directorio.mkdirs();
            System.out.println("El directorio se ha creado" +  directorio.getAbsolutePath());
        }else {
            System.out.println("El directorio ya existe" + directorio.getAbsolutePath());
        }

        // PASO 3:  CREAR EL FICHERO DE LOGS
        // a) Crear el fichero que hace referencia al fichero
        File log= new File(directorio, "seguridad_actividad2.log");

        // b) Crear el fichero en el disco
        //comprobamos si existe el fichero: si no existe lo cramos y si existe, lo indicamos con un mensaje
        try {
            if(!log.exists()){
                System.out.println("El archivo log no existe");
                log.createNewFile();
                System.out.println("El archivo log se ha creado" +  log.getAbsolutePath());
            }else {
                System.out.println("El directorio ya existe" + directorio.getAbsolutePath());
            }
        }catch (IOException e) {
            System.out.println("ERROR AL CREAR EL FICHERO" + e.getMessage());
            throw new RuntimeException(e);
        }

        // PASO 4: Renombrar fichero
        // a) Crear objeto File con el nombre nuevo, en el mismo directorio
        File logRotado = new File(directorio, "seguridad_actividad2_20260918_1137.log");

        // c) Renombrear el fichero yt comprobar si la operación ha funcionado
        if (log.renameTo(logRotado)) {
            System.out.println("Archivo renombreado: " + log.getName() + " --> " + logRotado.getName());
        }else{
            System.out.println("ERROR: no se a podido renombrar el archivo.");
        }

        //PASO 5: Eliminación
        // a) Borrar el fichero YA RENOMBRADO y comprobar si se ha borrado
        if(logRotado.delete()) {
            System.out.println("Eliminación correcta");
        }else{
            System.out.println("ERROR: no se a podido eliminar.");
        }
    }
}
