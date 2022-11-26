package dao;

import config.ConnecctionDB;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import model.Login;

public class LoginDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public LoginDAO() {
        ConnecctionDB c = new ConnecctionDB();
        emf = c.getFactory();
    }

    public static EntityManager getEntityManager() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("SearchMeWebAppPU");
                System.out.println("Conectado.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return emf.createEntityManager();
    }

    public void create(Login login) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(login);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Login login) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(login);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idLogin) {
        EntityManager em = null;
        Login login;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeLogin;
            try {
                login = em.getReference(Login.class, idLogin);     //Obtiene el dato con la id que corresponde
                existeLogin = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idLogin + " no existe.");  //Imprime que la bodega de id digitada no existe
                existeLogin = false;
                login = null;
            }
            if (existeLogin) {
                em.remove(login);    //Elimina la bodega y las tiendas y departamentos bajo esta
                em.getTransaction().commit();    //Envia el dato a la bd
            } else {
                em.getTransaction().rollback(); //Cierra la transaccion sin subir nada
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Object> findAll() {    //Metodo que obtiene el listado de logins
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Login.class));  //Imprime listado de bodegas
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Login obtenerLogin(Integer idLogin) {    //Obtiene registro de una bodega por su id
        EntityManager em = null;
        Login login;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                login = em.getReference(Login.class, idLogin);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idLogin + " no existe.");  //Imprime que la bodega de id digitada no existe
                login = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return login;
    }

    public boolean buscarLogin(String usuario, String clave) throws NoSuchAlgorithmException {     //Busca una bodega por su nombre y telefono mediante jpql
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(clave.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String clavemd5 = no.toString(16);
        while (clavemd5.length() < 32) {
            clavemd5 = "0" + clavemd5;
        }
        Query q = getEntityManager().createNamedQuery("Login.findIdByUsuarioClave");
        q.setParameter("usuario", usuario);
        q.setParameter("clave", clavemd5);
        /*String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);*/
        return !q.getResultList().isEmpty();
    }

}
