package dao;

import config.ConnecctionDB;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import model.Recibos;
import model.Tiendas;

/**
 *
 * @author Nova
 */
public class TiendasDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public TiendasDAO() {
        ConnecctionDB c = new ConnecctionDB();
        emf = c.getFactory();
    }

    public static EntityManager getEntityManager() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("PRUEBALOLPU");
                System.out.println("Conectado.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return emf.createEntityManager();
    }

    public void create(Tiendas tienda) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(tienda);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Tiendas tienda) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(tienda);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idTienda) {
        EntityManager em = null;
        Tiendas tienda;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeTienda;
            try {
                tienda = em.getReference(Tiendas.class, idTienda);     //Obtiene el dato con la id que corresponde
                existeTienda = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idTienda + " no existe.");  //Imprime que la tienda de id digitada no existe
                existeTienda = false;
                tienda = null;
            }
            if (existeTienda) {
                Collection<Recibos> listadoRecibos = tienda.getRecibosCollection();    //Almacena el listado de recibos referenciadas a la tienda
                boolean listadoRecibosVacio = listadoRecibos.isEmpty();     //Evalua si hay recibos referenciados a la tienda
                if (listadoRecibosVacio) {
                    System.out.println("El registro con id: " + idTienda + " sera eliminado.");
                    //if (continuarOperacion()) {
                    em.remove(tienda);     //Si estae vacio entonces elimina el departamento
                    // } else {
                    //    System.out.println("Operacion cancelada.");
                    //}
                } else {
                    System.out.println("Eliminar esta tienda, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles una nueva tienda antes de continuar");
                    System.out.println("----Recibos----");
                    for (int i = 0; i < listadoRecibos.size(); i++) {
                        System.out.println(listadoRecibos.toArray()[i]);

                    }
                    //if (continuarOperacion()) {
                    em.remove(tienda);    //Elimina la tienda y los recibos bajo esta
                    //} else {
                    //   System.out.println("Proceso cancelado.");
                    //}
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de tiendas
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiendas.class));  //Imprime listado de tiendas
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");  //Concatena los registros en un string y lo imprime
        }
        return mostrar;
    }

    /*public int getTiendasCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiendas> rt = cq.from(Tiendas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }*/
    public boolean continuarOperacion() {
        String opc;
        boolean continuarPreguntando;
        do {
            System.out.println("¿Desea continuar? (Y/N)");
            opc = entrada.next();
            continuarPreguntando = !"Y".equals(opc) && !"y".equals(opc) && !"N".equals(opc) && !"n".equals(opc); //evalua si la opcion se digita correctamente
        } while (continuarPreguntando);
        boolean si = "Y".equals(opc) || "y".equals(opc);
        return si;  //retorna el valor de la confirmacion
    }

    public Tiendas obtenerTienda(Integer idTienda) {
        EntityManager em = null;
        Tiendas tienda;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                tienda = em.getReference(Tiendas.class, idTienda);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idTienda + " no existe.");  //Imprime que la tienda de id digitada no existe
                tienda = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tienda;
    }

    public int buscarTiendaNom(String nombreTienda) {
        Query q = getEntityManager().createNamedQuery("Tiendas.findIdByNomTienda");
        q.setParameter("nomTienda", nombreTienda);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarTiendaTel(String telefonoTienda) {
        Query q = getEntityManager().createNamedQuery("Tiendas.findIdByTelTienda");
        q.setParameter("telTienda", telefonoTienda);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarTiendaNomTel(String nombreTienda, String telefonoTienda) {
        Query q = getEntityManager().createNamedQuery("Tiendas.findIdByNomTelTienda");
        q.setParameter("nomTienda", nombreTienda);
        q.setParameter("telTienda", telefonoTienda);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerTiendaNom(int idTienda) {
        String tienda = obtenerTienda(idTienda) + "";
        String[] tiendaComponentes = tienda.split(" , ");
        String[] tiendaInfo = tiendaComponentes[0].split(" : ");
        String tiendaNom = tiendaInfo[1];
        return tiendaNom;
    }

    public String obtenerTiendaDir(int idTienda) {
        String tienda = obtenerTienda(idTienda) + "";
        String[] tiendaComponentes = tienda.split(" , ");
        String tiendaDir = tiendaComponentes[1];
        return tiendaDir;
    }

    public String obtenerTiendaTel(int idTienda) {
        String tienda = obtenerTienda(idTienda) + "";
        String[] tiendaComponentes = tienda.split(" , ");
        String tiendaTel = tiendaComponentes[2];
        return tiendaTel;
    }

    public String obtenerTiendaIdBodega(int idTienda) {
        String tienda = obtenerTienda(idTienda) + "";
        String[] tiendaComponentes = tienda.split(" , ");
        String[] tiendaBodega = tiendaComponentes[3].split("\\[");
        String[] bodegaPartes = tiendaBodega[1].split("\\]");
        String tiendaIdBodega = bodegaPartes[0];
        return tiendaIdBodega;
    }

    public boolean bodegaExiste(String idBodega) {
        BodegasDAO bDao = new BodegasDAO();
        String[] bodegas = bDao.mostrarListadoInfo(bDao.findAll()).split("\\[");
        List<String> idBodegas = new ArrayList();
        for (int i = 1; i <= bDao.findAll().size(); i++) {
            String[] bodegasId = bodegas[i].split("\\] :");
            idBodegas.add(bodegasId[0]);
        }
        return idBodegas.contains(idBodega);
    }
}
