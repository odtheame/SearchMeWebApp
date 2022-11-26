package controller;

import dao.CargosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cargos;

@WebServlet("/CargosController")
public class CargosController extends HttpServlet {

    Cargos cargo = new Cargos();
    CargosDAO dao = new CargosDAO();
    String nombre;
    String descripcion;
    String salario;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editCargo.jsp?log=true").forward(request, response);
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
            dao.create(cargo);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editCargo.jsp?log=true&msg=created&table=Cargo").forward(request, response);
        }
        if (read) {
            int idCargo = 0;
            nombre = request.getParameter("nombreCargo");
            if (!"".equals(nombre)) {
                idCargo = dao.buscarCargoNom(nombre);
            }
            if (idCargo == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Cargo");
            } else {
                getInfo(idCargo);
                setAttributes(request);
            }
        }
        if (update) {
            cargo.setIdCargo(dao.buscarCargoNom(nombre));
            initComponents(request);
            setInfo();
            dao.update(cargo);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editCargo.jsp?log=true&msg=updated&table=Cargo").forward(request, response);
        }
        if (delete) {
            dao.remove(dao.buscarCargoNom(nombre));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Cargo");
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
        salario = request.getParameter("salario");
        descripcion = request.getParameter("descripcion");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("nombre", nombre);
        request.setAttribute("salario", salario);
        request.setAttribute("descripcion", descripcion);
    }

    private void setInfo() {
        cargo.setNomCargo(nombre);
        cargo.setSalarioCargo(Double.parseDouble(salario));
        cargo.setDescCargo(descripcion);
    }

    private void getInfo(int idCargo) {
        nombre = dao.obtenerCargoNom(idCargo);
        salario = dao.obtenerCargoSala(idCargo);
        descripcion = dao.obtenerCargoDesc(idCargo);
    }
}
