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
                emf = Persistence.createEntityManagerFactory("PRUEBALOLPU");
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
                //if (continuarOperacion()) {
                em.remove(recibo);     //Si esta vacio entonces elimina el listado
                //} else {
                //    System.out.println("Operacion cancelada.");
                //}
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

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        return mostrar;
    }

    /*public int getRecibosCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recibos> rt = cq.from(Recibos.class);
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

    public Recibos obtenerRecibo(Integer idRecibo) {
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

    public int buscarReciboNum(String reciboNum) {
        Query q = getEntityManager().createNamedQuery("Recibos.findIdByNumRecibo");
        q.setParameter("numRecibo", Integer.parseInt(reciboNum));
        String [] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String [] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length==0)?0:Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerReciboNum(int idRecibo) {
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[0].split(" : ");
        String reciboNum = reciboInfo[1];
        System.out.println("recibo " + reciboNum);
        return reciboNum;
    }

    public String obtenerReciboFecha(int idRecibo) {
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String reciboFecha = reciboComponentes[1];
        return reciboFecha;
    }

    public String obtenerReciboValor(int idRecibo) {
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String reciboValor = reciboComponentes[2];
        return reciboValor;
    }

    public String obtenerReciboIdTienda(int idRecibo) {
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[3].split("\\[");
        String[] TiendaPartes = reciboInfo[1].split("\\]");
        String reciboIdTienda = TiendaPartes[0];
        return reciboIdTienda;
    }

    public String obtenerReciboIdCliente(int idRecibo) {
        String recibo = obtenerRecibo(idRecibo) + "";
        String[] reciboComponentes = recibo.split(" , ");
        String[] reciboInfo = reciboComponentes[6].split("\\[");
        String[] ClientePartes = reciboInfo[1].split("\\]");
        String reciboIdCliente = ClientePartes[0];
        return reciboIdCliente;
    }

    public boolean tiendaExiste(String idTienda) {
        TiendasDAO tDao = new TiendasDAO();
        String[] tiendas = tDao.mostrarListadoInfo(tDao.findAll()).split("\\[");
        List<String> idTiendas = new ArrayList();
        for (int i = 1; i <= tDao.findAll().size(); i++) {
            String[] tiendasId = tiendas[i].split("\\] :");
            idTiendas.add(tiendasId[0]);
        }
        return idTiendas.contains(idTienda);
    }

    public boolean clienteExiste(String idCliente) {
        ClientesDAO cDao = new ClientesDAO();
        String[] clientes = cDao.mostrarListadoInfo(cDao.findAll()).split("\\[");
        List<String> idClientes = new ArrayList();
        for (int i = 1; i <= cDao.findAll().size(); i++) {
            String[] clientesId = clientes[i].split("\\] :");
            idClientes.add(clientesId[0]);
        }
        return idClientes.contains(idCliente);
    }
}
