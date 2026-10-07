import jakarta.xml.bind.*;
import java.io.File;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class DeserializarUsuario {
    public static void main(String[] args) {
        try {
            // 1. Contexto JAXB para la clase Usuario
            JAXBContext contexto = JAXBContext.newInstance(Usuario.class);

            // 2. Unmarshaller a partir del contexto
            Unmarshaller unmarshaller = contexto.createUnmarshaller();

            // 3. Deserialización: unmarshal devuelve Object, hay que convertirlo
            Usuario usuario = (Usuario) unmarshaller.unmarshal(
                    new File("usuario.xml"));

            // 4. Mostrar los datos en consola accediendo con los getters
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Rol: " + usuario.getRol());

        } catch (JAXBException e) {
            System.err.println("Error al realizar el Unmarshalling: " + e.getMessage());
        }
    }
}
