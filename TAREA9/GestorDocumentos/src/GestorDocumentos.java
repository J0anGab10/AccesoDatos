/**
 * @author Juan Gabriel Galarza Claros
 */

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class GestorDocumentos {
    public static final String DIRECTORIO = "Biblioteca";

    public static void main (String []args) {
        String documento = "Documento.txt";

        crearFichero(DIRECTORIO);
        documento = renombrarDocumento(DIRECTORIO, documento, "libros.txt");
        escribirLibrosAdicionales(DIRECTORIO, documento);
        leerContenidoArchivo(DIRECTORIO, documento);
    }

    // Funciones a crear...

    // EJERCICIO 1 : Operaciones con archivos y directorios
    // Ejercicio 1.1
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
    public static void leerContenidoArchivo(String ruta, String documento) {
        File archivo = new File(ruta, documento);

        try {
            FileReader leer = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leer);

            String texto;
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
    // Lee el archivo línea a línea y suma las palabras separadas por espacios.
    public static void contarPalabras(String archivo) {
        int contador = 0;

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
    // Copia el contenido línea a línea sin cargar el archivo entero en memoria.
    public static void copiarFichero(String archivoOrigen, String archivoDestino) {

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

    // Busca el tercer libro y reemplaza su título directamente en el archivo.
    public static void modificarLibro(String archivo) {

        try {
            RandomAccessFile fichero = new RandomAccessFile(archivo, "rw");

            String linea;
            long posicion;

            while (true) {

                // Guarda el inicio de la línea para poder volver a esa posición.
                posicion = fichero.getFilePointer();
                linea = fichero.readLine();

                if (linea == null) {
                    break;
                }

                if (linea.equals("Tercer libro: Codex Gigas")) {

                    // Salta el prefijo para sobrescribir solo el título del libro.
                    fichero.seek(posicion + "Tercer libro: ".length());

                    // Los dos nombres tienen la misma longitud
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

    // Lee el XML y muestra los títulos, aceptando las etiquetas titulo o title.
    public static void leerXML(String archivo) {

        try {
            // DOM carga el XML como un árbol para poder buscar sus elementos.
            DocumentBuilderFactory fabrica =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder constructor =
                    fabrica.newDocumentBuilder();

            Document documento = constructor.parse(new File(archivo));

            documento.getDocumentElement().normalize();

            NodeList titulos =
                    documento.getElementsByTagName("titulo");

            // Acepta XML que use el nombre del título en español o en inglés.
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

    // Ejercicio 6.1: crear la hoja de estilo XSL
    // Crea una hoja XSL que convierte los datos de libros del XML en una lista HTML.
    public static void crearXSL(String archivo) {

        try {
            FileWriter escribir = new FileWriter(archivo);
            BufferedWriter buffer = new BufferedWriter(escribir);

            buffer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            buffer.newLine();

            buffer.write("<xsl:stylesheet version=\"1.0\" ");
            buffer.write("xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
            buffer.newLine();

            buffer.write("<xsl:output method=\"html\" encoding=\"UTF-8\"/>");
            buffer.newLine();

            buffer.write("<xsl:template match=\"/\">");
            buffer.newLine();

            buffer.write("<html><head><title>Catalogo de libros</title></head>");
            buffer.write("<body><h1>Catalogo de libros</h1><ul>");
            buffer.newLine();

            buffer.write("<xsl:for-each select=\"//*[local-name()='libro']\">");
            buffer.newLine();

            buffer.write("<li><b>Titulo: </b>");
            buffer.write("<xsl:value-of select=\"*[local-name()='titulo' or local-name()='title'][1]\"/>");
            buffer.newLine();

            buffer.write("<br/><b>Autor: </b>");
            buffer.write("<xsl:value-of select=\"*[local-name()='autor' or local-name()='author'][1]\"/>");
            buffer.newLine();

            buffer.write("</li>");
            buffer.newLine();

            buffer.write("</xsl:for-each>");
            buffer.newLine();

            buffer.write("</ul></body></html>");
            buffer.newLine();

            buffer.write("</xsl:template>");
            buffer.newLine();

            buffer.write("</xsl:stylesheet>");

            buffer.close();

            System.out.println("Archivo XSL creado correctamente");

        } catch (IOException e) {
            System.out.println("Error al crear el XSL: " + e.getMessage());
        }
    }

    // Ejercicio 6.2: transformar XML a HTML
    // Aplica la hoja XSL al XML y guarda el resultado como un archivo HTML.
    public static void transformarXML(
            String archivoXML, String archivoXSL, String archivoHTML) {

        try {
            TransformerFactory fabrica = TransformerFactory.newInstance();

            // Crea el transformador a partir de la hoja XSL indicada.
            Transformer transformador = fabrica.newTransformer(
                    new StreamSource(new File(archivoXSL))
            );

            // Aplica la XSL al XML y guarda el resultado en el archivo HTML.
            transformador.transform(
                    new StreamSource(new File(archivoXML)),
                    new StreamResult(new File(archivoHTML))
            );

            System.out.println("Archivo HTML creado correctamente");

        } catch (TransformerException e) {
            System.out.println("Error al transformar el XML: " + e.getMessage());
        }
    }
}



