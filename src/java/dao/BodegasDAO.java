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
                emf = Persistence.createEntityManagerFactory("PRUEBALOLPU");
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
                    //if (continuarOperacion()) {
                        em.remove(bodega);     //Si ambos estan vacios entonces elimina la bodega
                    //} else {
                        //System.out.println("Operacion cancelada.");
                    //}
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
                   // if (continuarOperacion()) {
                        em.remove(bodega);    //Elimina la bodega y las tiendas y departamentos bajo esta
                    //} else {
                   //     System.out.println("Proceso cancelado.");
                   // }
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

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        return mostrar;
    }

    /*public int getBodegasCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodegas> rt = cq.from(Bodegas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            //System.out.println(((Long) q.getSingleResult()).intValue());
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

    public Bodegas obtenerBodega(Integer idBodega) {
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

    public int buscarBodegaNom(String nombreBodega, List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        String[] listadoBodegas = mostrar.split("\n");
        for (String bodega : listadoBodegas) {
            String[] bodegaIdInfo = bodega.split(": ");
            String[] idBodegaSeccion = bodega.split("] ");
            String[] idBodegaString = idBodegaSeccion[0].split("\\[");
            int idBodega = Integer.parseInt(idBodegaString[1]);
            String[] bodegaComponentes = bodegaIdInfo[1].split(" , ");
            String nomBodegaBuscada = bodegaComponentes[0];
            if (nombreBodega.equals(nomBodegaBuscada)) {
                return idBodega;
            }
        }
        return 0;
    }

    public int buscarBodegaTel(String telefonoBodega, List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        String[] listadoBodegas = mostrar.split("\n");
        for (String bodega : listadoBodegas) {
            String[] bodegaIdInfo = bodega.split(": ");
            String[] idBodegaSeccion = bodega.split("] ");
            String[] idBodegaString = idBodegaSeccion[0].split("\\[");
            int idBodega = Integer.parseInt(idBodegaString[1]);
            String[] bodegaComponentes = bodegaIdInfo[1].split(" , ");
            String telBodegaBuscada = bodegaComponentes[2];
            if (telefonoBodega.equals(telBodegaBuscada)) {
                return idBodega;
            }
        }
        return 0;
    }

    public String obtenerBodegaNom(int idBodega) {
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String[] bodegaInfo = bodegaComponentes[0].split(" : ");
        String bodegaNom = bodegaInfo[1];
        return bodegaNom;
    }

    public String obtenerBodegaDir(int idBodega) {
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String bodegaDir = bodegaComponentes[1];
        return bodegaDir;
    }

    public String obtenerBodegaTel(int idBodega) {
        String bodega = obtenerBodega(idBodega) + "";
        String[] bodegaComponentes = bodega.split(" , ");
        String bodegaTel = bodegaComponentes[2];
        return bodegaTel;
    }
}
