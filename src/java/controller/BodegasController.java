package controller;

import dao.BodegasDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Bodegas;

@WebServlet("/BodegasController")
public class BodegasController extends HttpServlet {

    Bodegas bodega = new Bodegas();
    BodegasDAO dao = new BodegasDAO();
    String nombre;
    String direccion;
    String telefono;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editBodega.jsp?log=true").forward(request, response);
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
            dao.create(bodega);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editBodega.jsp?log=true&msg=created&table=Bodega").forward(request, response);
        }
        if (read) {
            int idBodega = 0;
            nombre = request.getParameter("nombreBodega");
            telefono = request.getParameter("telefonoBodega");
            boolean nombreBlank = nombre.isBlank();
            boolean telefonoBlank = telefono.isBlank();
            if (!nombreBlank && !telefonoBlank) {
                idBodega = dao.buscarBodegaNomTel(nombre, telefono);
            } else {
                if (!nombreBlank) {
                    idBodega = dao.buscarBodegaNom(nombre);
                }
                if (!telefonoBlank) {
                    idBodega = dao.buscarBodegaTel(telefono);
                }
            }
            if (idBodega == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Bodega");
            } else {
                getInfo(idBodega);
                setAttributes(request);
            }
        }
        if (update) {
            bodega.setIdBodega(dao.buscarBodegaNom(nombre));
            initComponents(request);
            setInfo();
            dao.update(bodega);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editBodega.jsp?log=true&msg=updated&table=Bodega").forward(request, response);
        }
        if (delete) {
            dao.remove(dao.buscarBodegaNom(nombre));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Bodega");
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
        telefono = request.getParameter("telefono");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("nombre", nombre);
        request.setAttribute("direccion", direccion);
        request.setAttribute("telefono", telefono);
    }

    private void setInfo() {
        bodega.setNomBodega(nombre);
        bodega.setDirBodega(direccion);
        bodega.setTelBodega(telefono);
    }

    private void getInfo(int idBodega) {
        nombre = dao.obtenerBodegaNom(idBodega);
        direccion = dao.obtenerBodegaDir(idBodega);
        telefono = dao.obtenerBodegaTel(idBodega);
    }
}
