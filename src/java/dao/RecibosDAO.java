package dao;

import config.ConnecctionDB;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Scanner;
import model.Recibos;

/**
 *
 * @author Nova
 */
public class RecibosDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public RecibosDAO() {
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

    public void create(Recibos recibo) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(recibo);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Recibos recibo) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(recibo);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idRecibo) {
        EntityManager em = null;
        Recibos recibo;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeRecibo;
            try {
                recibo = em.getReference(Recibos.class, idRecibo);     //Obtiene el dato con la id que corresponde
                existeRecibo = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idRecibo + " no existe.");  //Imprime que el recibo de id digitada no existe
                existeRecibo = false;
                recibo = null;
            }
            if (existeRecibo) {
                System.out.println("El registro con id: " + idRecibo + " sera eliminado.");
                em.remove(recibo);     //Si esta vacio entonces elimina el listado
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de listados
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq
                    .select(cq.from(Recibos.class
                    ));  //Imprime listado de listados
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Recibos obtenerRecibo(Integer idRecibo) {    //Obtiene registro de un recibo por su id
        EntityManager em = null;
        Recibos recibo;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion

            try {
                recibo = em.getReference(Recibos.class,
                        idRecibo);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idRecibo + " no existe.");  //Imprime que el recibo de id digitada no existe
                recibo = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return recibo;
    }

    public int buscarReciboNum(String reciboNum) {      //Busca un recibo por su numero mediante jpql
        Query q = getEntityManager().createNamedQuery("Recibos.findIdByNumRecibo");
        q.setParameter("numRecibo", Integer.parseInt(reciboNum));
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerReciboNum(int idRecibo) {      //Obtiene el numero de un recibo por su id
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[0].split(" : ");
        String reciboNum = reciboInfo[1];
        System.out.println("recibo " + reciboNum);
        return reciboNum;
    }

    public String obtenerReciboFecha(int idRecibo) {    //Obtiene la fecha de un recibo por su id
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String reciboFecha = reciboComponentes[1];
        return reciboFecha;
    }

    public String obtenerReciboValor(int idRecibo) {    //Obtiene el valor total de un recibo por su id
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String reciboValor = reciboComponentes[2];
        return reciboValor;
    }

    public String obtenerReciboIdTienda(int idRecibo) {     //Obtiene la id de tienda asociada a un recibo por su id
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[3].split("\\[");
        String[] TiendaPartes = reciboInfo[1].split("\\]");
        String reciboIdTienda = TiendaPartes[0];
        return reciboIdTienda;
    }

    public String obtenerReciboIdCliente(int idRecibo) {    //Obtiene la id de cliente asociada a un recibo por su id
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[6].split("\\[");
        String[] ClientePartes = reciboInfo[1].split("\\]");
        String reciboIdCliente = ClientePartes[0];
        return reciboIdCliente;
    }

    public boolean tiendaExiste(String idTienda) {  //Confirma que la tienda que se va a asociar existe
        TiendasDAO tDao = new TiendasDAO();
        return (tDao.obtenerTienda(Integer.parseInt(idTienda)) == null);
    }

    public boolean clienteExiste(String idCliente) {    //Confirma que el cliente que se va a asociar existe
        ClientesDAO cDao = new ClientesDAO();
        return (cDao.obtenerCliente(Integer.parseInt(idCliente)) == null);
    }
}
