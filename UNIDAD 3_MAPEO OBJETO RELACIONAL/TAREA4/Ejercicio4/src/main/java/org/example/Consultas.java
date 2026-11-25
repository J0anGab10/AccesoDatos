package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * @author Juan Gabriel Galarza Claros
 */

public class Consultas {
    static EntityManagerFactory enf;

    public static void main(String[] args) {
        enf = Persistence.createEntityManagerFactory("default");

        ConsultaNativaSimple();
        ConsultaNativaFiltrada();
        ConsultaJpqlBasica();
        ConsultaJpqlCondicion();

        enf.close();
    }

    // 1. SQL Nativo - Consulta simple (Object[])
    static void ConsultaNativaSimple() {
        EntityManager em = enf.createEntityManager();
        System.out.println("1. SQL NATIVO: CONSULTA SIMPLE");

        String sql = "SELECT SIM_LLAMANTE, NUMERO_LLAMADO, IMPORTE_LLAMADA FROM LLAMADAS_EMITIDAS";
        Query q = em.createNativeQuery(sql);
        List<Object[]> resultados = q.getResultList();

        for (Object[] fila : resultados) {
            System.out.println("SIM: " + fila[0] + " | Destino: " + fila[1] + " | Importe: " + fila[2] + " €");
        }
        em.close();
    }

    // 2. SQL Nativo - Consulta con filtrado (Entidades)
    static void ConsultaNativaFiltrada() {
        EntityManager em = enf.createEntityManager();
        System.out.println("\n2. SQL NATIVO: DURACION > 300");

        String sql = "SELECT * FROM LLAMADAS_EMITIDAS WHERE DURACION_LLAMADA > 300";
        Query q = em.createNativeQuery(sql, LlamadaEmitida.class);
        List<LlamadaEmitida> llamadas = q.getResultList();

        for (LlamadaEmitida llamada : llamadas) {
            System.out.println("Código: " + llamada.getId()
                    + " | SIM: " + llamada.getSimLlamante().getId()
                    + " | Destino: " + llamada.getNumeroLlamado()
                    + " | Duración: " + llamada.getDuracionLlamada() + "s"
                    + " | Importe: " + llamada.getImporteLlamada() + " €");
        }
        em.close();
    }

    // 3. JPQL - Consulta básica
    static void ConsultaJpqlBasica() {
        EntityManager em = enf.createEntityManager();
        System.out.println("\n3. JPQL: CONSULTA BASICA");

        String jpql = "SELECT l FROM LlamadaEmitida l";
        TypedQuery<LlamadaEmitida> q = em.createQuery(jpql, LlamadaEmitida.class);
        List<LlamadaEmitida> llamadas = q.getResultList();

        for (LlamadaEmitida llamada : llamadas) {
            System.out.println("SIM: " + llamada.getSimLlamante().getId()
                    + " | Destino: " + llamada.getNumeroLlamado()
                    + " | Importe: " + llamada.getImporteLlamada() + " €");
        }
        em.close();
    }

    // 4. JPQL - Consulta con condición (importe > 10)
    static void ConsultaJpqlCondicion() {
        EntityManager em = enf.createEntityManager();
        System.out.println("\n4. JPQL: IMPORTE > 10");

        String jpql = "SELECT l FROM LlamadaEmitida l WHERE l.importeLlamada > :minimo";
        TypedQuery<LlamadaEmitida> q = em.createQuery(jpql, LlamadaEmitida.class);
        q.setParameter("minimo", 10.0f);
        List<LlamadaEmitida> llamadas = q.getResultList();

        for (LlamadaEmitida llamada : llamadas) {
            System.out.println("Código: " + llamada.getId()
                    + " | SIM: " + llamada.getSimLlamante().getId()
                    + " | Destino: " + llamada.getNumeroLlamado()
                    + " | Importe: " + llamada.getImporteLlamada() + " €");
        }
        em.close();
    }
}