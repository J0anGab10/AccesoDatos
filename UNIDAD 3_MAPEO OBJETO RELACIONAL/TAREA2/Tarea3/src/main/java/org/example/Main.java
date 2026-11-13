package org.example;

import jakarta.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Conexión: lee persistence.xml (la unidad "default") y abre una "sesión" de trabajo
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        // ---------- 1. INSERTAR una llamada nueva ----------
        em.getTransaction().begin();               // empieza la transacción
        LlamadasEmitida nueva = new LlamadasEmitida();
        // TODO: rellena TODOS los atributos con sus métodos set.
        //       - Código: uno que NO exista en LLAMADAS_EMITIDAS.
        //       - SIM: una que SÍ exista en TARJETAS_TELEFONICAS (columna NUMERO_SIM).

        nueva.setId(1000073);
        nueva.setSimLlamante(606252627);
        nueva.setNumerosLlamado(123561562);
        nueva.setDuracionLlamada(37);
        nueva.setImporteLlamada(5.50f);


        em.persist(nueva);                         // le pide a Hibernate que la guarde
        em.getTransaction().commit();              // aquí se ejecuta realmente el INSERT
        System.out.println("Insertada: " + nueva);

        // ---------- 2. ACTUALIZAR el importe de una llamada existente ----------
        em.getTransaction().begin();
        // find busca por clave primaria; devuelve null si no existe
        LlamadasEmitida llamada = em.find(LlamadasEmitida.class, 1000073 /* TODO: código que SÍ exista */);
        if (llamada != null) {
            // TODO: cambia el importe con su método set
            llamada.setImporteLlamada(6.72f);
            // em.getTransaction().commit();

            System.out.println("Actualizada: " + llamada);
        } else {
            System.out.println("No existe ninguna llamada con ese código.");
        }
        em.getTransaction().commit();              // Hibernate detecta el cambio y lanza el UPDATE

        // ---------- 3. CONSULTAR todas las llamadas ----------
        List<LlamadasEmitida> lista = em.createQuery(
                "SELECT l FROM LlamadasEmitida l", LlamadasEmitida.class).getResultList();
        // TODO: recorre la lista y muestra cada llamada por consola

        System.out.println("<--- Todas las llamadas ( " + lista.size() + " ) -->");
        for (LlamadasEmitida l : lista) {
            System.out.println(l);
        }

        // Cierre de recursos
        em.close();
        emf.close();
    }
}