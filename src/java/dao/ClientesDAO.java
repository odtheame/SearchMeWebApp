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
                emf = Persistence.createEntityManagerFactory("PRUEBALOLPU");
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
                    //if (continuarOperacion()) {
                        em.remove(cliente);     //Si el listado esta vacios entonces elimina el cliente
                    //} else {
                    //    System.out.println("Operacion cancelada.");
                    //}
                } else {
                    System.out.println("Eliminar este cliente, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles un nuevo cliente antes de continuar");
                    System.out.println("----Recibos----");
                    for (int i = 0; i < listadoRecibos.size(); i++) {
                        System.out.println(listadoRecibos.toArray()[i]);
                    }
                    //if (continuarOperacion()) {
                        em.remove(cliente);    //Elimina la bodega y las tiendas y departamentos bajo esta
                    //} else {
                    //    System.out.println("Proceso cancelado.");
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

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        return mostrar;
    }

    /*public int getClientesCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
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

    public Clientes obtenerCliente(Integer idCliente) {
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

    public int buscarClienteCorreo(String correoCliente, List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        String[] listadoClientes = mostrar.split("\n");
        for (String cliente : listadoClientes) {
            String[] clienteIdInfo = cliente.split(": ");
            String[] idClienteSeccion = cliente.split("] ");
            String[] idClienteString = idClienteSeccion[0].split("\\[");
            int idCliente = Integer.parseInt(idClienteString[1]);
            String[] clienteComponentes = clienteIdInfo[1].split(" , ");
            String correoClienteBuscado = clienteComponentes[4];
            if (correoCliente.equals(correoClienteBuscado)) {
                return idCliente;
            }
        }
        return 0;
    }

    public int buscarClienteTel(String telefonoCliente, List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        String[] listadoClientes = mostrar.split("\n");
        for (String cliente : listadoClientes) {
            String[] clienteIdInfo = cliente.split(": ");
            String[] idClienteSeccion = cliente.split("] ");
            String[] idClienteString = idClienteSeccion[0].split("\\[");
            int idCliente = Integer.parseInt(idClienteString[1]);
            String[] clienteComponentes = clienteIdInfo[1].split(" , ");
            String telClienteBuscado = clienteComponentes[5];
            if (telefonoCliente.equals(telClienteBuscado)) {
                return idCliente;
            }
        }
        return 0;
    }

    public String obtenerClientePrNom(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] idInfo = cliente.split(" : ");
        String[] clienteComponentes = idInfo[1].split(" , ");
        String clientePrNom = clienteComponentes[0];
        return clientePrNom;
    }
    
    public String obtenerClienteSdNom(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteSdNom = clienteComponentes[1];
        return clienteSdNom;
    }
    
    public String obtenerClientePrApell(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clientePrApell = clienteComponentes[2];
        return clientePrApell;
    }
    
    public String obtenerClienteSdApell(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteSdApell = clienteComponentes[3];
        return clienteSdApell;
    }

    public String obtenerClienteCorreo(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteCorreo = clienteComponentes[4];
        return clienteCorreo;
    }

    public String obtenerClienteTel(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteTel = clienteComponentes[5];
        return clienteTel;
    }
    
    public String obtenerClienteDir(int idCliente) {
        String cliente = obtenerCliente(idCliente) + "";
        String[] clienteComponentes = cliente.split(" , ");
        String clienteDir = clienteComponentes[6];
        return clienteDir;
    }
}
