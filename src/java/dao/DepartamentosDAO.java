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
import model.Departamentos;
import model.Empleados;

/**
 *
 * @author Nova
 */
public class DepartamentosDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public DepartamentosDAO() {
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

    public void create(Departamentos departamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(departamento);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Departamentos departamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(departamento);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idDepartamento) {
        EntityManager em = null;
        Departamentos departamento;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeDepartamento;
            try {
                departamento = em.getReference(Departamentos.class, idDepartamento);     //Obtiene el dato con la id que corresponde
                existeDepartamento = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idDepartamento + " no existe.");  //Imprime que el departamento de id digitado no existe
                existeDepartamento = false;
                departamento = null;
            }
            if (existeDepartamento) {
                Collection<Empleados> listadoEmpleados = departamento.getEmpleadosCollection();    //Almacena el listado de Empleados referenciadas al departamento
                boolean listadoEmpleadosVacio = listadoEmpleados.isEmpty();     //Evalua si hay empleados referenciados al departamento
                if (listadoEmpleadosVacio) {
                    System.out.println("El registro con id: " + idDepartamento + " sera eliminado.");
                    em.remove(departamento);     //Si estae vacio entonces elimina el departamento
                } else {
                    System.out.println("Eliminar este departamento, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles un nuevo departamento antes de continuar");
                    System.out.println("----Empleados----");
                    for (int i = 0; i < listadoEmpleados.size(); i++) {
                        System.out.println(listadoEmpleados.toArray()[i]);
                    }
                    em.remove(departamento);    //Elimina el departamentos y los empleados bajo este
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de departamentos
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamentos.class));  //Imprime listado de departamentos
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Departamentos obtenerDepartamento(Integer idDept) {      //Obtiene registro de un departamento por su id
        EntityManager em = null;
        Departamentos departamento;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                departamento = em.getReference(Departamentos.class, idDept);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idDept + " no existe.");  //Imprime que el departamento de id digitada no existe
                departamento = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return departamento;
    }

    public int buscarDepartamentoNom(String nombreDepartamento) {       //Busca un departamento por su nombre mediante jpql
        Query q = getEntityManager().createNamedQuery("Departamentos.findIdByNomDept");
        q.setParameter("nomDept", nombreDepartamento);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerDepartamentoNom(int idDepartamento) {      //Obtiene el nombre de un departamento por su id
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String[] departamentoInfo = departamentoComponentes[0].split(" : ");
        String departamentoNom = departamentoInfo[1];
        return departamentoNom;
    }

    public String obtenerDepartamentoDir(int idDepartamento) {      //Obtiene la direccion de un departamento por su id
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String departamentoDir = departamentoComponentes[1];
        return departamentoDir;
    }

    public String obtenerDepartamentoIdBodega(int idDepartamento) {     //Obtiene la id de bodega asociada a un departamento por su id
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String[] departamentoBodega = departamentoComponentes[2].split("\\[");
        String[] bodegaPartes = departamentoBodega[1].split("\\]");
        String departamentoIdBodega = bodegaPartes[0];
        return departamentoIdBodega;
    }

    public boolean bodegaExiste(String idBodega) {      //Confirma que la bodega que se va a asociar existe
        BodegasDAO bDao = new BodegasDAO();
        return (bDao.obtenerBodega(Integer.parseInt(idBodega)) == null);
    }
}
