import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        System.out.println("Intentando conectar a la base de datos...");

        EntityManagerFactory emf = null;
        try {
            // Intenta crear el EntityManagerFactory.
            // "default" debe coincidir con el <persistence-unit name="default"> de tu persistence.xml
            emf = Persistence.createEntityManagerFactory("default");
            System.out.println("EntityManagerFactory creado: " + emf.getClass().getName());

            // Crear el factory no garantiza la conexión: lanzamos una consulta mínima.
            try (EntityManager em = emf.createEntityManager()) {
                em.createNativeQuery("SELECT 1").getSingleResult();
            }

            // Si llega aquí, la conexión fue exitosa.
            System.out.println("¡CONEXIÓN EXITOSA!");

        } catch (Exception e) {
            // Si falla, imprimirá el error exacto.
            System.err.println("ERROR AL CONECTAR:");
            e.printStackTrace();

        } finally {
            // Cierra el factory si se llegó a crear.
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory cerrado.");
            }
        }
    }
}
