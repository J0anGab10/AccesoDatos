package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class Main {
    static EntityManagerFactory enf;

    public static void main(String[] args) {
        enf = Persistence.createEntityManagerFactory("default");

        Insertar();
        //Consultar();
        //Modificar();
        //Eliminar();
        //Estados();

        enf.close();
    }
    static void Insertar() {
        EntityManager em = enf.createEntityManager();
        em.getTransaction().begin();

        TarjetaTelefonica tarjeta = em.find(TarjetaTelefonica.class,617478396);

        LlamadaEmitida nueva = new LlamadaEmitida();
        nueva.setId(10000100);
        nueva.setSimLlamante(tarjeta);
        nueva.setNumeroLlamado(987654321);
        nueva.setDuracionLlamada(300);
        nueva.setImporteLlamada(12.50f);

        em.persist(nueva);

        em.getTransaction().commit();

        System.out.println("Insertada: " + nueva);
        em.close();
    }
}
