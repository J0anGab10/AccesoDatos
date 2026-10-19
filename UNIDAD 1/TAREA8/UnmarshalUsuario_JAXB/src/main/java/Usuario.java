import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;

/**
 * @author Juan Gabriel Galarza Claros
 */

@XmlRootElement
public class Usuario {
    @XmlAttribute
    int id;
    String nombre;
    String email;
    String rol;

    public Usuario() {

    }

    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters
    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    @XmlElement(name = "rol")
    public String getRol() {
        return rol;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
