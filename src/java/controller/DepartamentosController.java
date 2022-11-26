package controller;

import dao.BodegasDAO;
import dao.DepartamentosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Departamentos;

@WebServlet("/DepartamentosController")
public class DepartamentosController extends HttpServlet {

    Departamentos departamento = new Departamentos();
    DepartamentosDAO dao = new DepartamentosDAO();
    BodegasDAO bDao = new BodegasDAO();
    String nombre;
    String direccion;
    String idBodega;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editDepartamento.jsp?log=true").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String metodoCRUD = request.getParameter("btnOperacion");
        boolean create = "Crear".equals(metodoCRUD);
        boolean read = "Buscar".equals(metodoCRUD);
        boolean update = "Actualizar".equals(metodoCRUD);
        boolean delete = "Eliminar".equals(metodoCRUD);
        if (create) {
            initComponents(request);
            setInfo();
            if (dao.bodegaExiste(idBodega)) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Bodegas");
            } else {
                dao.create(departamento);
                setAttributes(request);
                request.getRequestDispatcher("/edit/editDepartamento.jsp?log=true&msg=created&table=Departamento").forward(request, response);
            }
        }
        if (read) {
            int idDepartamento = 0;
            nombre = request.getParameter("nombreDepartamento");
            if (!"".equals(nombre)) {
                idDepartamento = dao.buscarDepartamentoNom(nombre);
            }
            if (idDepartamento == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Departamento");
            } else {
                getInfo(idDepartamento);
                setAttributes(request);
            }
        }
        if (update) {
            departamento.setIdDept(dao.buscarDepartamentoNom(nombre));
            initComponents(request);
            setInfo();
            setAttributes(request);
            if (dao.bodegaExiste(idBodega)) {
                request.getRequestDispatcher("/edit/editDepartamento.jsp?log=true&msg=notFound&table=Bodega").forward(request, response);
            } else {
                dao.update(departamento);
                request.getRequestDispatcher("/edit/editDepartamento.jsp?log=true&msg=updated&table=Departamento").forward(request, response);
            }
        }
        if (delete) {
            dao.remove(dao.buscarDepartamentoNom(nombre));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Departamento");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);
            processRequest(request, response);
        } catch (Exception e) {
        }
    }

    private void initComponents(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        direccion = request.getParameter("direccion");
        idBodega = request.getParameter("idBodega");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("nombre", nombre);
        request.setAttribute("direccion", direccion);
        request.setAttribute("idBodega", idBodega);
    }

    private void setInfo() {
        departamento.setNomDept(nombre);
        departamento.setDirDept(direccion);
        departamento.setIdBodega(bDao.obtenerBodega(Integer.parseInt(idBodega)));
    }

    private void getInfo(int idDepartamento) {
        nombre = dao.obtenerDepartamentoNom(idDepartamento);
        direccion = dao.obtenerDepartamentoDir(idDepartamento);
        idBodega = dao.obtenerDepartamentoIdBodega(idDepartamento);
    }
}
