/**
 * @author Juan Gabriel Galarza Claros
 */

// --- 1. LIBRERÍAS DE ENTRADA/SALIDA DE ARCHIVOS (java.io) ---

import java.io.File;               // Te permite usar 'new File()', '.exists()', '.mkdir()' y '.renameTo()'.
import java.io.FileWriter;         // Te permite abrir archivos para escribir en ellos.
import java.io.BufferedWriter;     // Te permite usar 'new BufferedWriter()', '.write()' y '.newLine()' de forma eficiente.
import java.io.FileReader;         // Te permite abrir archivos para leer su contenido.
import java.io.BufferedReader;     // Te permite usar 'new BufferedReader()' y su metodo '.readLine()' para leer línea a línea.
import java.io.RandomAccessFile;   // Te permite usar 'new RandomAccessFile()', '.seek()', y '.getFilePointer()' para saltar dentro del archivo.
import java.io.IOException;        // Es la excepción que captura los errores si un archivo no existe o falla al leer/escribir.

// --- 2. LIBRERÍAS PARA LEER EL XML (javax.xml.parsers) ---

import javax.xml.parsers.DocumentBuilderFactory; // Te permite usar 'DocumentBuilderFactory.newInstance()' para preparar la lectura.
import javax.xml.parsers.DocumentBuilder;        // Te permite usar el 'constructor.parse(archivo)' para leer físicamente el XML.

// --- 3. LIBRERÍAS PARA EL ÁRBOL DOM DEL XML (org.w3c.dom) ---

import org.w3c.dom.Document;       // Representa el archivo XML entero en memoria. Te permite usar '.getElementsByTagName()'.
import org.w3c.dom.NodeList;       // Representa una lista de etiquetas. Te permite usar '.getLength()' y '.item(i)' para recorrer los títulos.

// --- 4. LIBRERÍAS PARA TRANSFORMAR DE XML A HTML (javax.xml.transform) ---

import javax.xml.transform.TransformerFactory; // Te permite usar 'TransformerFactory.newInstance()' para preparar la transformación.
import javax.xml.transform.Transformer;        // Es el motor que hace el trabajo. Te permite usar el metodo '.transform()'.
import javax.xml.transform.TransformerException; // Captura los errores si la hoja XSLT está mal escrita o falla la transformación.
import javax.xml.transform.stream.StreamSource;  // Envuelve el archivo de origen (tu XML y tu XSL) para que el Transformer los entienda.
import javax.xml.transform.stream.StreamResult;  // Envuelve el archivo de destino (tu HTML) para que el Transformer sepa dónde guardar.

public class GestorDocumentos {
    public static final String DIRECTORIO = "Biblioteca";

    // Coordina las operaciones principales del programa.
    public static void main (String []args) {
        String documento = "Documento.txt";

        crearFichero(DIRECTORIO);
        documento = renombrarDocumento(DIRECTORIO, documento, "libros.txt");
        escribirLibrosAdicionales(DIRECTORIO, documento);
        leerContenidoArchivo(DIRECTORIO, documento);

        contarPalabras(DIRECTORIO, documento);
        copiarFichero(DIRECTORIO, documento, "copia_libros.txt");
        modificarLibro(DIRECTORIO, documento);

        leerXML(DIRECTORIO, "libros.xml");

        crearXSL(DIRECTORIO, "estilo.xsl");
        transformarXML(DIRECTORIO, "libros.xml", "estilo.xsl", "catalogo.html");
    }

    // Funciones a crear...

    // EJERCICIO 1 : Operaciones con archivos y directorios
    // Ejercicio 1.1
    // Crea un directorio si aun no existe.
    public static void crearFichero(String nombre) {
        File directorio = new File(nombre);

        if (!directorio.exists()) {
            directorio.mkdir();
            System.out.println("Directorio creado con exito");
        }else {
            System.out.println("Error: El direcotorio ya existe");
        }
    }
    // Ejercicio 1.2
    // Renombra un archivo dentro de la ruta indicada y devuelve el nombre nuevo.
    public static String renombrarDocumento(String ruta, String nombre, String nuevoNombre) {

        File archivo = new File(ruta, nombre);
        File archivoRenombre = new File(ruta, nuevoNombre);

        if (archivo.exists()) {
            archivo.renameTo(archivoRenombre);
            System.out.println("Archivo renombrado correctamente a : " + nuevoNombre);
        }else {
            System.out.println("Error: No se a encontrado el archivo " + nombre);
        }
        return nuevoNombre;
    }

    // EJERCICIO 2 : Escribir y leer en el archivo
    // Ejercicio 2.1
    // Anade tres titulos al final del documento.
    public static void escribirLibrosAdicionales(String ruta, String documento) {
        File archivo = new File(ruta, documento);

        try {
            FileWriter libros = new FileWriter(archivo, true);
            BufferedWriter buffer = new BufferedWriter(libros);

            buffer.newLine();
            buffer.write("Primer libro: Manuscrito Voynich");
            buffer.newLine();
            buffer.write("Segundo libro: El Necronomicón");
            buffer.newLine();
            buffer.write("Tercer libro: Codex Gigas");

            buffer.close();
        }catch (IOException e ) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Ejercicio 2.2
    // Imprime el contenido del documento, una linea cada vez.
    public static void leerContenidoArchivo(String ruta, String documento) {
        File archivo = new File(ruta, documento);

        try {
            FileReader leer = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leer);

            String texto;
            // Cada vuelta lee una linea; null indica que se alcanzo el final del archivo.
            while ((texto = buffer.readLine()) != null) {
                System.out.println(texto);
            }

            buffer.close();
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // EJERCICIO 3 : Operaciones adicionales sobre el archivo
    // Ejercicio 3.1
    public static void contarPalabras(String ruta, String documento) {
        int contador = 0;
        File archivo = new File(ruta, documento); // Adaptado a tu estilo

        try {
            FileReader leer = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leer);
            String linea;

            while ((linea = buffer.readLine()) != null) {
                String[] palabras = linea.trim().split("\\s+");

                if (!linea.trim().isEmpty()) {
                    contador += palabras.length;
                }
            }
            System.out.println("Número de palabras: " + contador);
            buffer.close();
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    // Ejercicio 3.2
    public static void copiarFichero(String ruta, String origen, String destino) {
        File archivoOrigen = new File(ruta, origen); // Adaptado a tu estilo
        File archivoDestino = new File(ruta, destino);

        try {
            FileReader leer = new FileReader(archivoOrigen);
            BufferedReader buffer = new BufferedReader(leer);

            FileWriter escribir = new FileWriter(archivoDestino);
            BufferedWriter bufferEscritura = new BufferedWriter(escribir);

            String linea;
            while ((linea = buffer.readLine()) != null) {
                bufferEscritura.write(linea);
                bufferEscritura.newLine();
            }
            System.out.println("Copia creada correctamente.");

            buffer.close();
            bufferEscritura.close();
        } catch (IOException e) {
            System.out.println("Error al copiar el fichero: " + e.getMessage());
        }
    }


    // EJERCICIO 4: ACCESO ALEATORIO AL ARCHIVO
    public static void modificarLibro(String ruta, String documento) {
        File archivo = new File(ruta, documento); // Adaptado a tu estilo

        try {
            RandomAccessFile fichero = new RandomAccessFile(archivo, "rw");

            String linea;
            long posicion;

            while (true) {
                posicion = fichero.getFilePointer();
                linea = fichero.readLine();

                if (linea == null) {
                    break;
                }

                if (linea.equals("Tercer libro: Codex Gigas")) {
                    fichero.seek(posicion + "Tercer libro: ".length());
                    fichero.write("Codex Major".getBytes());
                    System.out.println("Libro modificado correctamente");
                    break;
                }
            }
            fichero.close();

        } catch (IOException e) {
            System.out.println("Error al modificar el fichero: " + e.getMessage());
        }
    }


    // EJERCICIO 5: LECTURA DE UN ARCHIVO XML CON DOM
    public static void leerXML(String ruta, String documentoXML) {
        File archivo = new File(ruta, documentoXML); // Adaptado a tu estilo

        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(archivo);

            documento.getDocumentElement().normalize();

            NodeList titulos = documento.getElementsByTagName("titulo");

            if (titulos.getLength() == 0) {
                titulos = documento.getElementsByTagName("title");
            }

            System.out.println("Titulos del catalogo XML:");

            for (int i = 0; i < titulos.getLength(); i++) {
                System.out.println(titulos.item(i).getTextContent());
            }

        } catch (Exception e) {
            System.out.println("Error al leer el XML: " + e.getMessage());
        }
    }


    // EJERCICIO 6: TRANSFORMACION DE XML A HTML
    // Ejercicio 6.1
    public static void crearXSL(String ruta, String documentoXSL) {
        File archivo = new File(ruta, documentoXSL); // Adaptado a tu estilo

        try {
            FileWriter escribir = new FileWriter(archivo);
            BufferedWriter buffer = new BufferedWriter(escribir);

            // Cabecera estándar que indica que este documento es un XML válido.
            buffer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            buffer.newLine();

            // Etiqueta raíz de XSL. Define que es una hoja de estilo y enlaza el espacio de nombres oficial de XSLT.
            buffer.write("<xsl:stylesheet version=\"1.0\" ");
            buffer.write("xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
            buffer.newLine();

            // Le dice al transformador que el resultado final que queremos generar será un documento HTML.
            buffer.write("<xsl:output method=\"html\" encoding=\"UTF-8\"/>");
            buffer.newLine();

            // Define la plantilla principal. El match="/" indica que empezará a procesar desde la raíz del archivo XML.
            buffer.write("<xsl:template match=\"/\">");
            buffer.newLine();

            // Crea la estructura básica de una página web en HTML: head, title, body y el inicio de una lista desordenada (ul).
            buffer.write("<html><head><title>Catalogo de libros</title></head>");
            buffer.write("<body><h1>Catalogo de libros</h1><ul>");
            buffer.newLine();

            // Bucle XSLT. Busca en cualquier parte del XML (//) todos los elementos que se llamen 'libro' y los recorre uno a uno.
            buffer.write("<xsl:for-each select=\"//*[local-name()='libro']\">");
            buffer.newLine();

            // Etiqueta HTML 'li' para crear un punto en la lista y 'b' para poner la palabra "Titulo:" en negrita.
            buffer.write("<li><b>Titulo: </b>");
            // Extrae y escribe el texto del elemento 'titulo' (o 'title'). El [1] asegura que coja solo el primero si hay varios.
            buffer.write("<xsl:value-of select=\"*[local-name()='titulo' or local-name()='title'][1]\"/>");
            buffer.newLine();

            // Etiqueta HTML 'br' para hacer un salto de línea dentro del mismo punto de la lista.
            buffer.write("<br/><b>Autor: </b>");
            // Extrae y escribe el texto del elemento 'autor' (o 'author').
            buffer.write("<xsl:value-of select=\"*[local-name()='autor' or local-name()='author'][1]\"/>");
            buffer.newLine();

            // Cierra el punto de la lista HTML.
            buffer.write("</li>");
            buffer.newLine();

            // Cierra el bucle de XSLT. A partir de aquí ya no se procesan más 'libros'.
            buffer.write("</xsl:for-each>");
            buffer.newLine();

            // Cierra las etiquetas HTML abiertas al principio (la lista, el cuerpo y el documento).
            buffer.write("</ul></body></html>");
            buffer.newLine();

            // Cierra la plantilla principal de XSLT.
            buffer.write("</xsl:template>");
            buffer.newLine();

            // Cierra la hoja de estilos general. Fin del documento XSL.
            buffer.write("</xsl:stylesheet>");

            buffer.close();
            System.out.println("Archivo XSL creado correctamente");

        } catch (IOException e) {
            System.out.println("Error al crear el XSL: " + e.getMessage());
        }
    }

    // Ejercicio 6.2
    public static void transformarXML(String ruta, String docXML, String docXSL, String docHTML) {
        File archivoXML = new File(ruta, docXML);
        File archivoXSL = new File(ruta, docXSL);
        File archivoHTML = new File(ruta, docHTML);

        try {
            TransformerFactory fabrica = TransformerFactory.newInstance();

            Transformer transformador = fabrica.newTransformer(new StreamSource(archivoXSL));

            transformador.transform(
                    new StreamSource(archivoXML),
                    new StreamResult(archivoHTML)
            );

            System.out.println("Archivo HTML creado correctamente");

        } catch (TransformerException e) {
            System.out.println("Error al transformar el XML: " + e.getMessage());
        }
    }
}



