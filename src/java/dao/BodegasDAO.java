package dao;

import config.ConnecctionDB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import model.Bodegas;
import model.Departamentos;
import model.Tiendas;

/**
 *
 * @author Nova
 */
public class BodegasDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public BodegasDAO() {
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

    public void create(Bodegas bodega) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(bodega);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Bodegas bodega) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(bodega);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idBodega) {
        EntityManager em = null;
        Bodegas bodega;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeBodega;
            try {
                bodega = em.getReference(Bodegas.class, idBodega);     //Obtiene el dato con la id que corresponde
                existeBodega = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idBodega + " no existe.");  //Imprime que la bodega de id digitada no existe
                existeBodega = false;
                bodega = null;
            }
            if (existeBodega) {
                Collection<Tiendas> listadoTiendas = bodega.getTiendasCollection();    //Almacena el listado de Tiendas referenciadas a la bodega
                Collection<Departamentos> listadoDepartamentos = bodega.getDepartamentosCollection();    //Almacena el listado de Departamentos referenciadas a la bodega
                boolean listadoTiendasVacio = listadoTiendas.isEmpty();     //Evalua si hay tiendas referenciadas a la bodega
                boolean listadoDepartamentosVacio = listadoDepartamentos.isEmpty();     //Evalua si hay departamentos referenciados a la bodega
                if (listadoTiendasVacio && listadoDepartamentosVacio) {
                    System.out.println("El registro con id: " + idBodega + " sera eliminado.");
                    em.remove(bodega);     //Si ambos estan vacios entonces elimina la bodega
                } else {
                    System.out.println("Eliminar esta bodega, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles una nueva bodega antes de continuar");
                    if (!listadoTiendasVacio) {
                        System.out.println("----Tiendas----");
                        for (int i = 0; i < listadoTiendas.size(); i++) {
                            System.out.println(listadoTiendas.toArray()[i]);
                        }
                    }
                    if (!listadoDepartamentosVacio) {
                        System.out.println("----Departamentos----");
                        for (int i = 0; i < listadoDepartamentos.size(); i++) {
                            System.out.println(listadoDepartamentos.toArray()[i]);
                        }
                    }
                    em.remove(bodega);    //Elimina la bodega y las tiendas y departamentos bajo esta
                }
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de bodegas
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodegas.class));  //Imprime listado de bodegas
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Bodegas obtenerBodega(Integer idBodega) {    //Obtiene registro de una bodega por su id
        EntityManager em = null;
        Bodegas bodega;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                bodega = em.getReference(Bodegas.class, idBodega);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idBodega + " no existe.");  //Imprime que la bodega de id digitada no existe
                bodega = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return bodega;
    }

    public int buscarBodegaNom(String nombreBodega) {   //Busca una bodega por su nombre mediante jpql
        Query q = getEntityManager().createNamedQuery("Bodegas.findIdByNomBodega");
        q.setParameter("nomBodega", nombreBodega);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarBodegaTel(String telefonoBodega) {     //Busca una bodega por su telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Bodegas.findIdByTelBodega");
        q.setParameter("telBodega", telefonoBodega);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarBodegaNomTel(String nombreBodega, String telefonoBodega) {     //Busca una bodega por su nombre y telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Bodegas.findIdByNomTelBodega");
        q.setParameter("nomBodega", nombreBodega);
        q.setParameter("telBodega", telefonoBodega);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerBodegaNom(int idBodega) {      //Obtiene el nombre de una bodega por su id
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String[] bodegaInfo = bodegaComponentes[0].split(" : ");
        String bodegaNom = bodegaInfo[1];
        return bodegaNom;
    }

    public String obtenerBodegaDir(int idBodega) {      //Obtiene el direccion de una bodega por su id
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String bodegaDir = bodegaComponentes[1];
        return bodegaDir;
    }

    public String obtenerBodegaTel(int idBodega) {      //Obtiene el telefono de una bodega por su id
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String bodegaTel = bodegaComponentes[2];
        return bodegaTel;
    }
}
