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
                    //if (continuarOperacion()) {
                    em.remove(departamento);     //Si estae vacio entonces elimina el departamento
                    //} else {
                    //    System.out.println("Operacion cancelada.");
                    //}
                } else {
                    System.out.println("Eliminar este departamento, eliminara los siguientes registros."
                            + " Si no esta de acuerdo, asigneles un nuevo departamento antes de continuar");
                    System.out.println("----Empleados----");
                    for (int i = 0; i < listadoEmpleados.size(); i++) {
                        System.out.println(listadoEmpleados.toArray()[i]);
                    }
                    //if (continuarOperacion()) {
                    em.remove(departamento);    //Elimina el departamentos y los empleados bajo este
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

    public String mostrarListadoInfo(List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");  //Concatena los registros en un string y lo imprime
        }
        return mostrar;
    }

    /*public int getBodegasCount() { //Si se va a utilizar el metodo, descomentar el import de root
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamentos> rt = cq.from(Departamentos.class);
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

    public Departamentos obtenerDepartamento(Integer idDept) {
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

    public int buscarDepartamentoNom(String nombreDepartamento, List<Object> listadoInfo) {
        String mostrar = "";
        for (int i = 0; i < listadoInfo.size(); i++) {
            mostrar = mostrar.concat(listadoInfo.get(i) + "\n");
        }
        String[] listadoDepartamentos = mostrar.split("\n");
        for (String departamento : listadoDepartamentos) {
            String[] departamentoIdInfo = departamento.split(": ");
            String[] idDepartamentoSeccion = departamento.split("] ");
            String[] idDepartamentoString = idDepartamentoSeccion[0].split("\\[");
            int idDepartamento = Integer.parseInt(idDepartamentoString[1]);
            String[] departamentoComponentes = departamentoIdInfo[1].split(" , ");
            String nomDepartamentoBuscado = departamentoComponentes[0];
            if (nombreDepartamento.equals(nomDepartamentoBuscado)) {
                return idDepartamento;
            }
        }
        return 0;
    }

    public String obtenerDepartamentoNom(int idDepartamento) {
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String[] departamentoInfo = departamentoComponentes[0].split(" : ");
        String departamentoNom = departamentoInfo[1];
        return departamentoNom;
    }

    public String obtenerDepartamentoDir(int idDepartamento) {
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String departamentoDir = departamentoComponentes[1];
        return departamentoDir;
    }

    public String obtenerDepartamentoIdBodega(int idDepartamento) {
        String departamento = obtenerDepartamento(idDepartamento) + "";
        String[] departamentoComponentes = departamento.split(" , ");
        String[] departamentoBodega = departamentoComponentes[2].split("\\[");
        String[] bodegaPartes = departamentoBodega[1].split("\\]");
        String departamentoIdBodega = bodegaPartes[0];
        return departamentoIdBodega;
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
