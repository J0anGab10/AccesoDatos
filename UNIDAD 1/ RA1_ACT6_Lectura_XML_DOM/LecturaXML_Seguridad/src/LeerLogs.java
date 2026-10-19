import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class LeerLogs {

    static final String RUTA= "logs.xml";

    public static void main(String[] args) {
        try {
            // ---- PASO 1: cargar el XML en memoria ----
            // a) Crear la fábrica (DocumentBuilderFactory) y el constructor (DocumentBuilder).
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder analizador = fabrica.newDocumentBuilder();

            // b) Leer el fichero y convertirlo en un árbol DOM (Document).
            Document doc = analizador.parse(new File(RUTA));
            doc.getDocumentElement().normalize();

            // c) Obtener el elemento raíz y mostrar su nombre.
            System.out.println("Raíz: " + doc.getDocumentElement().getNodeName());

            // ---- PASO 2: recorrer los registros <log> ----
            // a) Obtener la lista de todos los elementos <log>.
            NodeList logs = doc.getElementsByTagName("log");

            // b) Recorrer la lista con un bucle.
            for (int i = 0; i < logs.getLength(); i++) {
                // c) Convertir cada nodo en Element para poder leer sus datos.
                Element u = (Element) logs.item(i);
                // d) Leer el atributo id.
                String id = u.getAttribute("id");

                // e) Leer el texto de <nivel>, <mensaje> y <usuario>.
                String nivel = u.getElementsByTagName("nivel")
                        .item(0).getTextContent();
                String mensaje = u.getElementsByTagName("mensaje")
                        .item(0).getTextContent();
                String usuario = u.getElementsByTagName("usuario")
                        .item(0).getTextContent();

                // f) Mostrar los datos por consola.
                System.out.println(id + ": " + nivel + " (" + mensaje + ")" + " (" + usuario + ")");
            }
            // ---- PASO 3: tratar las excepciones ----
            // a) Error al configurar el parser.
            // b) Error al abrir el fichero.
            // c) El fichero no es un XML válido.
        }catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error al leer el XML: " + e.getMessage());
        }
    }
    // ---- obtenerTexto(elemento, etiqueta) ----
    // a) Buscar dentro del elemento las etiquetas con ese nombre.
    // b) Si no hay ninguna, devolver "(sin dato)".
    // c) Si la hay, devolver el texto de la primera.
    public static String obtenerTexto( Element elemento, String etiqueta) {
        NodeList lista = elemento.getElementsByTagName(etiqueta);
        if (lista.getLength() == 0) {
            return "(sin dato)";
        }
        return lista.item(0).getTextContent();
    }
}
