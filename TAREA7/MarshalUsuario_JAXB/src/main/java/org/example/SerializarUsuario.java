package org.example;

import jakarta.xml.bind.*;

import java.io.File;

public class SerializarUsuario {
    private static final String FICHERO= "usuario.xml";

    public static void main (String []args) {
        Usuario usuario = new Usuario(1,"Loren", "loren@gmail.com","administrador");

        try {
            // 2. Crear el contexto de JAXB asociándolo a la clase Usuario
            JAXBContext context = JAXBContext.newInstance(Usuario.class);

            // 3. Crear el Marshaller
            Marshaller marshaller = context.createMarshaller();

            // Configurar para que el XML tenga sangría / formato legible
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // 4. Definir el archivo de salida 'usuario.xml'
            File archivo = new File("usuario.xml");

            // Convertir el objeto a XML y guardarlo en el archivo
            marshaller.marshal(usuario, archivo);

            // También lo mostramos por consola para confirmar
            System.out.println("--- XML generado con éxito ---");
            marshaller.marshal(usuario, System.out);

        } catch (JAXBException e) {
            System.err.println("Error durante la serialización a XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
