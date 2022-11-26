package controller;

import dao.BodegasDAO;
import dao.TiendasDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Tiendas;

@WebServlet("/TiendasController")
public class TiendasController extends HttpServlet {

    Tiendas tienda = new Tiendas();
    TiendasDAO dao = new TiendasDAO();
    BodegasDAO bDao = new BodegasDAO();
    String nombre;
    String direccion;
    String telefono;
    String idBodega;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editTienda.jsp?log=true").forward(request, response);
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
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Bodega");
            } else {
                dao.create(tienda);
                setAttributes(request);
                request.getRequestDispatcher("/edit/editTienda.jsp?log=true&msg=created&table=Tienda").forward(request, response);
            }
        }
        if (read) {
            int idTienda = 0;
            nombre = request.getParameter("nombreTienda");
            telefono = request.getParameter("telefonoTienda");
            boolean nombreBlank = nombre.isBlank();
            boolean telefonoBlank = telefono.isBlank();
            if (!nombreBlank && !telefonoBlank) {
                idTienda = dao.buscarTiendaNomTel(nombre, telefono);
            } else {
                if (!nombreBlank) {
                    idTienda = dao.buscarTiendaNom(nombre);
                }
                if (!telefonoBlank) {
                    idTienda = dao.buscarTiendaTel(telefono);
                }
            }
            if (idTienda == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Tienda");
            } else {
                getInfo(idTienda);
                setAttributes(request);
            }
        }
        if (update) {
            tienda.setIdTienda(dao.buscarTiendaNom(nombre));
            initComponents(request);
            setInfo();
            setAttributes(request);
            if (dao.bodegaExiste(idBodega)) {
                request.getRequestDispatcher("/edit/editTienda.jsp?log=true&msg=notFound&table=Bodega").forward(request, response);
            } else {
                dao.update(tienda);
                request.getRequestDispatcher("/edit/editTienda.jsp?log=true&msg=updated&table=Tienda").forward(request, response);
            }
        }
        if (delete) {
            dao.remove(dao.buscarTiendaNom(nombre));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Tienda");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initComponents(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        direccion = request.getParameter("direccion");
        telefono = request.getParameter("telefono");
        idBodega = request.getParameter("idBodega");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("nombre", nombre);
        request.setAttribute("direccion", direccion);
        request.setAttribute("telefono", telefono);
        request.setAttribute("idBodega", idBodega);
    }

    private void setInfo() {
        tienda.setNomTienda(nombre);
        tienda.setDirTienda(direccion);
        tienda.setTelTienda(telefono);
        tienda.setIdBodega(bDao.obtenerBodega(Integer.parseInt(idBodega)));
    }

    private void getInfo(int idTienda) {
        nombre = dao.obtenerTiendaNom(idTienda);
        direccion = dao.obtenerTiendaDir(idTienda);
        telefono = dao.obtenerTiendaTel(idTienda);
        idBodega = dao.obtenerTiendaIdBodega(idTienda);
    }
}
