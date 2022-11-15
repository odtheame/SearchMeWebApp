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
import model.Empleados;

/**
 *
 * @author Nova
 */
public class EmpleadosDAO {

    private static EntityManagerFactory emf = null;
    private static final Scanner entrada = new Scanner(System.in);

    public EmpleadosDAO() {
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

    public void create(Empleados empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.persist(empleado);    //Persiste el dato en el objeto
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Empleados empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            em.merge(empleado);    //Actualiza el dato que corresponde a la id dada
            em.getTransaction().commit();   //Envia el dato a la bd
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer idEmpleado) {
        EntityManager em = null;
        Empleados empleado;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            boolean existeEmpleado;
            try {
                empleado = em.getReference(Empleados.class, idEmpleado);     //Obtiene el dato con la id que corresponde
                existeEmpleado = true;
            } catch (Exception e) {
                System.out.println("El registro con id: " + idEmpleado + " no existe.");  //Imprime que el empleado de id digitada no existe
                existeEmpleado = false;
                empleado = null;
            }
            if (existeEmpleado) {
                System.out.println("El registro con id: " + idEmpleado + " sera eliminado.");
                em.remove(empleado);     //Elimina el empleado
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

    public List<Object> findAll() {    //Metodo que obtiene el listado de empleados
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));  //Imprime listado de empleados
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Empleados obtenerEmpleado(Integer idEmpleado) {      //Obtiene registro de un empleado por su id
        EntityManager em = null;
        Empleados empleado;
        try {
            em = getEntityManager();    //Guarda la conexión a la BD en em
            em.getTransaction().begin();    //Inicia una transaccion que se aloja en el area de conexion
            try {
                empleado = em.getReference(Empleados.class, idEmpleado);     //Obtiene el dato con la id que corresponde
            } catch (Exception e) {
                System.out.println("El registro con id: " + idEmpleado + " no existe.");  //Imprime que el empleado de id digitada no existe
                empleado = null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return empleado;
    }

    public int buscarEmpleadoCorreo(String correoEmpleado) {    //Busca un empleado por su correo mediante jpql
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByCorreoEmpl");
        q.setParameter("correoEmpl", correoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarEmpleadoTel(String telefonoEmpleado) {     //Busca un empleado por su telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByTelEmpl");
        q.setParameter("telEmpl", telefonoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarEmplCorreoTel(String correoEmpleado, String telefonoEmpleado) {    //Busca un empleado por su correo y telefono mediante jpql
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByCorreoTelEmpl");
        q.setParameter("correoEmpl", correoEmpleado);
        q.setParameter("telEmpl", telefonoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerEmpleadoPrNom(int idEmpleado) {    //Obtiene el primer nombre de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] idInfo = empleado.split(" : ");
        String[] empleadoComponentes = idInfo[1].split(" , ");
        String empleadoPrNom = empleadoComponentes[0];
        return empleadoPrNom;
    }

    public String obtenerEmpleadoSdNom(int idEmpleado) {    //Obtiene el segundo nombre de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoSdNom = empleadoComponentes[1];
        return empleadoSdNom;
    }

    public String obtenerEmpleadoPrApell(int idEmpleado) {      //Obtiene el primer apellido de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoPrApell = empleadoComponentes[2];
        return empleadoPrApell;
    }

    public String obtenerEmpleadoSdApell(int idEmpleado) {      //Obtiene el segundo apellido de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoSdApell = empleadoComponentes[3];
        return empleadoSdApell;
    }

    public String obtenerEmpleadoDir(int idEmpleado) {      //Obtiene la direccion de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoDir = empleadoComponentes[4];
        return empleadoDir;
    }

    public String obtenerEmpleadoTel(int idEmpleado) {      //Obtiene el telefono de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoTel = empleadoComponentes[5];
        return empleadoTel;
    }

    public String obtenerEmpleadoCorreo(int idEmpleado) {       //Obtiene el correo de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoCorreo = empleadoComponentes[6];
        return empleadoCorreo;
    }

    public String obtenerEmpleadoFechaNaci(int idEmpleado) {        //Obtiene la fecha de nacimiento de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoFechaNaci = empleadoComponentes[7];
        return empleadoFechaNaci;
    }

    public String obtenerEmpleadoCiudad(int idEmpleado) {       //Obtiene la ciudad de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoCiudad = empleadoComponentes[8];
        return empleadoCiudad;
    }

    public String obtenerEmpleadoPais(int idEmpleado) {     //Obtiene el pais de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoPais = empleadoComponentes[9];
        return empleadoPais;
    }

    public String obtenerEmpleadoIdCargo(int idEmpleado) {      //Obtiene el id del cargo asociado de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String[] empleadoCargo = empleadoComponentes[10].split("\\[");
        String[] cargoPartes = empleadoCargo[1].split("\\]");
        String empleadoIdCargo = cargoPartes[0];
        return empleadoIdCargo;
    }

    public String obtenerEmpleadoIdDept(int idEmpleado) {       //Obtiene el id del departamento asociado de un empleado por su id
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String[] empleadoCargo = empleadoComponentes[10].split("\\[");
        String[] deptoPartes = empleadoCargo[1].split("\\]");
        String empleadoIdDepto = deptoPartes[0];
        return empleadoIdDepto;
    }

    public boolean departamentoExiste(String idDept) {      //Confirma que el departamento que se va a asociar existe
        DepartamentosDAO dDao = new DepartamentosDAO();
        return (dDao.obtenerDepartamento(Integer.parseInt(idDept)) == null);
    }

    public boolean cargoExiste(String idCargo) {    //Confirma que el cargo que se va a asociar existe
        CargosDAO cDao = new CargosDAO();
        return (cDao.obtenerCargo(Integer.parseInt(idCargo)) == null);
    }
}
