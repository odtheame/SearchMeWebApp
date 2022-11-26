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
import model.Clientes;
import model.Recibos;

/**
 *
 * @author Nova
 */
public class ClientesDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public ClientesDAO() {
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

    public void create(Clientes cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(cliente);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Clientes cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(cliente);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idCliente) {
        EntityManager em = null;
        Clientes cliente;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeCliente;
            try {
                cliente = em.getReference(Clientes.class, idCliente);     //Obtiene el dato con la id que corresponde
                existeCliente = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idCliente + " no existe.");  //Imprime que el cliente de id digitada no existe
                existeCliente = false;
                cliente = null;
            }
            if (existeCliente) {
                Collection<Recibos> listadoRecibos = cliente.getRecibosCollection();    //Almacena el listado de Recibos referenciados al cliente
                boolean listadoRecibosVacio = listadoRecibos.isEmpty();     //Evalua si hay recibos referenciados al cliente
                if (listadoRecibosVacio) {
                    System.out.println("El registro con id: " + idCliente + " sera eliminado.");
                    em.remove(cliente);     //Si el listado esta vacios entonces elimina el cliente
                } else {
                    System.out.println("Eliminar este cliente, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles un nuevo cliente antes de continuar");
                    System.out.println("----Recibos----");
                    for (int i = 0; i < listadoRecibos.size(); i++) {
                        System.out.println(listadoRecibos.toArray()[i]);
                    }
                    em.remove(cliente);    //Elimina la bodega y las tiendas y departamentos bajo esta
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
            cq.select(cq.from(Clientes.class));  //Imprime listado de bodegas
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public String mostrarListadoInfo(List<Object> listadoInfo) {    //Metodo que obtiene el listado de cargos
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        return mostrar;
    }

    public Clientes obtenerCliente(Integer idCliente) {     //Obtiene registro de un cliente por su id
        EntityManager em = null;
        Clientes cliente;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                cliente = em.getReference(Clientes.class, idCliente);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idCliente + " no existe.");  //Imprime que el cliente de id digitada no existe
                cliente = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return cliente;
    }

    public int buscarClienteCorreo(String correoCliente) {      //Busca un cliente por su correo mediante jpql
        Query q = getEntityManager().createNamedQuery("Clientes.findIdByCorreoClien");
        q.setParameter("correoClien", correoCliente);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarClienteTel(String telefonoCliente) {       //Busca un cliente por su telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Clientes.findIdByTelClien");
        q.setParameter("telClien", telefonoCliente);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarClienCorreoTel(String correoCliente, String telefonoCliente) {     //Busca un cliente por su correo y telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Clientes.findIdByCorreoTelClien");
        q.setParameter("correoClien", correoCliente);
        q.setParameter("telClien", telefonoCliente);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerClientePrNom(int idCliente) {      //Obtiene el primer nombre de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] idInfo = cliente.split(" : ");
        String[] clienteComponentes = idInfo[1].split(" , ");
        String clientePrNom = clienteComponentes[0];
        return clientePrNom;
    }

    public String obtenerClienteSdNom(int idCliente) {      //Obtiene el segundo nombre de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteSdNom = clienteComponentes[1];
        return clienteSdNom;
    }

    public String obtenerClientePrApell(int idCliente) {        //Obtiene el primer apellido de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clientePrApell = clienteComponentes[2];
        return clientePrApell;
    }

    public String obtenerClienteSdApell(int idCliente) {        //Obtiene el segundo apellido de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteSdApell = clienteComponentes[3];
        return clienteSdApell;
    }

    public String obtenerClienteCorreo(int idCliente) {     //Obtiene el correo de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteCorreo = clienteComponentes[4];
        return clienteCorreo;
    }

    public String obtenerClienteTel(int idCliente) {        //Obtiene el telefono de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteTel = clienteComponentes[5];
        return clienteTel;
    }

    public String obtenerClienteDir(int idCliente) {        //Obtiene la direccion de un cliente por su id
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteDir = clienteComponentes[6];
        return clienteDir;
    }
}
