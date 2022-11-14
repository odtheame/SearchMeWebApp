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
//import javax.persistence.criteria.Root;

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
                //if (continuarOperacion()) {
                em.remove(empleado);     //Elimina el empleado
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

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");  //Concatena los registros en un string y lo imprime
        }
        return mostrar;
    }

    /*public int getEmpleadosCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
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

    public Empleados obtenerEmpleado(Integer idEmpleado) {
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

    public int buscarEmpleadoCorreo(String correoEmpleado) {
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByCorreoEmpl");
        q.setParameter("correoEmpl", correoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarEmpleadoTel(String telefonoEmpleado) {
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByTelEmpl");
        q.setParameter("telEmpl", telefonoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public int buscarEmplCorreoTel(String correoEmpleado, String telefonoEmpleado) {
        Query q = getEntityManager().createNamedQuery("Empleados.findIdByCorreoTelEmpl");
        q.setParameter("correoEmpl", correoEmpleado);
        q.setParameter("telEmpl", telefonoEmpleado);
        String[] quitandoCorchete1 = q.getResultList().toString().split("\\[");
        String[] quitandoCorchete2 = quitandoCorchete1[1].split("\\]");
        return (quitandoCorchete2.length == 0) ? 0 : Integer.parseInt(quitandoCorchete2[0]);
    }

    public String obtenerEmpleadoPrNom(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] idInfo = empleado.split(" : ");
        String[] empleadoComponentes = idInfo[1].split(" , ");
        String empleadoPrNom = empleadoComponentes[0];
        return empleadoPrNom;
    }

    public String obtenerEmpleadoSdNom(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoSdNom = empleadoComponentes[1];
        return empleadoSdNom;
    }

    public String obtenerEmpleadoPrApell(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoPrApell = empleadoComponentes[2];
        return empleadoPrApell;
    }

    public String obtenerEmpleadoSdApell(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoSdApell = empleadoComponentes[3];
        return empleadoSdApell;
    }

    public String obtenerEmpleadoDir(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoDir = empleadoComponentes[4];
        return empleadoDir;
    }

    public String obtenerEmpleadoTel(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoTel = empleadoComponentes[5];
        return empleadoTel;
    }

    public String obtenerEmpleadoCorreo(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoCorreo = empleadoComponentes[6];
        return empleadoCorreo;
    }

    public String obtenerEmpleadoFechaNaci(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoFechaNaci = empleadoComponentes[7];
        return empleadoFechaNaci;
    }

    public String obtenerEmpleadoCiudad(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoCiudad = empleadoComponentes[8];
        return empleadoCiudad;
    }

    public String obtenerEmpleadoPais(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String empleadoPais = empleadoComponentes[9];
        return empleadoPais;
    }

    public String obtenerEmpleadoIdCargo(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String[] empleadoCargo = empleadoComponentes[10].split("\\[");
        String[] cargoPartes = empleadoCargo[1].split("\\]");
        String empleadoIdCargo = cargoPartes[0];
        return empleadoIdCargo;
    }

    public String obtenerEmpleadoIdDept(int idEmpleado) {
        String empleado = obtenerEmpleado(idEmpleado) + "";
        String[] empleadoComponentes = empleado.split(" , ");
        String[] empleadoCargo = empleadoComponentes[10].split("\\[");
        String[] deptoPartes = empleadoCargo[1].split("\\]");
        String empleadoIdDepto = deptoPartes[0];
        return empleadoIdDepto;
    }

    public boolean departamentoExiste(String idDept) {
        DepartamentosDAO dDao = new DepartamentosDAO();
        String[] depts = dDao.mostrarListadoInfo(dDao.findAll()).split("\\[");
        List<String> idDepts = new ArrayList();
        for (int i = 1; i <= dDao.findAll().size(); i++) {
            String[] deptsId = depts[i].split("\\] :");
            idDepts.add(deptsId[0]);
        }
        return idDepts.contains(idDept);
    }

    public boolean cargoExiste(String idCargo) {
        CargosDAO cDao = new CargosDAO();
        String[] cargos = cDao.mostrarListadoInfo(cDao.findAll()).split("\\[");
        List<String> idCargos = new ArrayList();
        for (int i = 1; i <= cDao.findAll().size(); i++) {
            String[] cargosId = cargos[i].split("\\] :");
            idCargos.add(cargosId[0]);
        }
        return idCargos.contains(idCargo);
    }
}
