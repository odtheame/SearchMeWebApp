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
import model.Cargos;
import model.Empleados;

/**
 *
 * @author Nova
 */
public class CargosDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public CargosDAO() {
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

    public void create(Cargos cargo) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(cargo);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Cargos cargo) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(cargo);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idCargo) {
        EntityManager em = null;
        Cargos cargo;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeCargo;
            try {
                cargo = em.getReference(Cargos.class, idCargo);     //Obtiene el dato con la id que corresponde
                existeCargo = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idCargo + " no existe.");  //Imprime que el cargo de id digitada no existe
                existeCargo = false;
                cargo = null;
            }
            if (existeCargo) {
                Collection<Empleados> listadoEmpleados = cargo.getEmpleadosCollection();    //Almacena el listado de empleados referenciados al cargo
                boolean listadoEmpleadosVacio = listadoEmpleados.isEmpty();     //Evalua si hay empleados referenciados al cargo
                if (listadoEmpleadosVacio) {
                    System.out.println("El registro con id: " + idCargo + " sera eliminado.");
                    em.remove(cargo);     //Si esta vacio entonces elimina el cargo
                } else {
                    System.out.println("Eliminar esta cargo, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles un nuevo cargo antes de continuar");
                    System.out.println("----Empleados----");
                    for (int i = 0; i < listadoEmpleados.size(); i++) {
                        System.out.println(listadoEmpleados.toArray()[i]);
                    }
                    em.remove(cargo);    //Elimina el cargo y los empleados bajo esta
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de cargos
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargos.class));  //Imprime listado de bodegas
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Cargos obtenerCargo(Integer idCargo) {     //Obtiene registro de un cargo por su id
        EntityManager em = null;
        Cargos cargo;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                cargo = em.getReference(Cargos.class, idCargo);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idCargo + " no existe.");  //Imprime que el cargo de id digitada no existe
                cargo = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return cargo;
    }

    public int buscarCargoNom(String nombreCargo) {     //Busca un cargo por su nombre mediante jpql
        Query q = getEntityManager().createNamedQuery("Cargos.findIdByNomCargo");
        q.setParameter("nomCargo", nombreCargo);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerCargoNom(int idCargo) {    //Obtiene el nombre de un cargo por su id
        String cargo = obtenerCargo(idCargo) + "";
        String[] cargoComponentes = cargo.split(" , ");
        String[] cargoInfo = cargoComponentes[0].split(" : ");
        String cargoNom = cargoInfo[1];
        return cargoNom;
    }

    public String obtenerCargoDesc(int idCargo) {   //Obtiene la descripcion de un cargo por su id
        String cargo = obtenerCargo(idCargo) + "";
        String[] cargoComponentes = cargo.split(" , ");
        String cargoDesc = cargoComponentes[1];
        return cargoDesc;
    }

    public String obtenerCargoSala(int idCargo) {   //Obtiene el salario de un cargo por su id
        String cargo = obtenerCargo(idCargo) + "";
        String[] cargoComponentes = cargo.split(" , ");
        String cargoSala = cargoComponentes[2];
        return cargoSala;
    }
}
