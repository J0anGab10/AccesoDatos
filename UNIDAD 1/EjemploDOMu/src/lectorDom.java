import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class lectorDom {
    public static void main(String[] args) {
        try {
            // Pasos 1 y 2: fábrica y analizador
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder analizador = fabrica.newDocumentBuilder();

            // Pasos 3 y 4: análisis del fichero y normalización del árbol
            Document doc = analizador.parse(new File("usuarios.xml"));
            doc.getDocumentElement().normalize();
            System.out.println("Raíz: " + doc.getDocumentElement().getNodeName());

            // Paso 5: recorrido de los elementos <usuario>
            NodeList usuarios = doc.getElementsByTagName("usuario");
            for (int i = 0; i < usuarios.getLength(); i++) {
                Element u = (Element) usuarios.item(i);
                String id = u.getAttribute("id");
                String nombre = u.getElementsByTagName("nombre")
                        .item(0).getTextContent();
                String rol = u.getElementsByTagName("rol")
                        .item(0).getTextContent();
                System.out.println(id + ": " + nombre + " (" + rol + ")");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error al leer el XML: " + e.getMessage());
        }
    }
}

