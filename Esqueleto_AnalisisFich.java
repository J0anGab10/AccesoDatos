public class Esqueleto_AnalisisFich {

    // Constantes con los nombres de los dos ficheros.

    public static void main(String[] args) {
        // a) Mostrar el contenido inicial del log.
        // b) Añadir dos entradas nuevas al final del log.
        // c) Volver a mostrarlo para comprobar que se han añadido.
        // d) Contar líneas, palabras y caracteres.
        // e) Hacer una copia de respaldo.
        // f) Eliminar de la copia la palabra sensible y mostrar el resultado.
    }

    // ---- mostrarContenido(nombreFichero) ----
    // a) Abrir el fichero para leer líneas completas.
    // b) Leer la primera línea.
    // c) Mientras haya línea: mostrarla y leer la siguiente.
    // d) Tratar la excepción.

    // ---- anadirLineas(nombreFichero) ----
    // a) Abrir el fichero en modo AÑADIR (FileWriter con true).
    // b) Escribir cada entrada seguida de un salto de línea.
    // c) Tratar la excepción.

    // ---- contarContenido(nombreFichero) ----
    // a) Preparar los contadores de líneas, palabras y caracteres.
    // b) Abrir el fichero para leer línea a línea.
    // c) Por cada línea: actualizar los tres contadores.
    // d) Mostrar el resultado al terminar.

    // ---- copiarFichero(origen, destino) ----
    // a) Abrir los dos ficheros: uno para leer y otro para escribir.
    // b) Leer cada línea del origen y escribirla en el destino.
    // c) Tratar la excepción.

    // ---- limpiarPalabra(nombreFichero, palabra) ----
    // a) Preparar la lista donde guardar las líneas ya limpias.
    // b) Leer el fichero entero, quitando la palabra de cada línea.
    // c) Reescribir el fichero con las líneas limpias.
}
