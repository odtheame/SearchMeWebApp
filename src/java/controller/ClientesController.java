package controller;

import dao.ClientesDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Clientes;

@WebServlet("/ClientesController")
public class ClientesController extends HttpServlet {

    Clientes cliente = new Clientes();
    ClientesDAO dao = new ClientesDAO();
    String prNombre;
    String sdNombre = " ";
    String prApell;
    String sdApell;
    String correo;
    String direccion;
    String telefono;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editCliente.jsp?log=true").forward(request, response);
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
            dao.create(cliente);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editCliente.jsp?log=true&msg=created&table=Cliente").forward(request, response);
        }
        if (read) {
            int idCliente = 0;
            correo = request.getParameter("correoCliente");
            telefono = request.getParameter("telefonoCliente");
            boolean correoBlank = correo.isBlank();
            boolean telefonoBlank = telefono.isBlank();
            if (!correoBlank && !telefonoBlank) {
                idCliente = dao.buscarClienCorreoTel(correo, telefono);
            } else {
                if (!correoBlank) {
                    idCliente = dao.buscarClienteCorreo(correo);
                }
                if (!telefonoBlank) {
                    idCliente = dao.buscarClienteTel(telefono);
                }
            }
            if (idCliente == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Cliente");
            } else {
                getInfo(idCliente);
                setAttributes(request);
            }
        }
        if (update) {
            cliente.setIdCliente(dao.buscarClienteCorreo(correo));
            initComponents(request);
            setInfo();
            dao.update(cliente);
            setAttributes(request);
            request.getRequestDispatcher("/edit/editCliente.jsp?log=true&msg=updated&table=Cliente").forward(request, response);

        }
        if (delete) {
            dao.remove(dao.buscarClienteCorreo(correo));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Cliente");
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
        prNombre = request.getParameter("prNombre");
        sdNombre = request.getParameter("sdNombre");
        prApell = request.getParameter("prApell");
        sdApell = request.getParameter("sdApell");
        correo = request.getParameter("correo");
        telefono = request.getParameter("telefono");
        direccion = request.getParameter("direccion");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("prNombre", prNombre);
        request.setAttribute("sdNombre", sdNombre);
        request.setAttribute("prApell", prApell);
        request.setAttribute("sdApell", sdApell);
        request.setAttribute("correo", correo);
        request.setAttribute("telefono", telefono);
        request.setAttribute("direccion", direccion);
    }

    private void setInfo() {
        cliente.setPrNomClien(prNombre);
        cliente.setSdNomClien(sdNombre);
        cliente.setPrApellClien(prApell);
        cliente.setSdApellClien(sdApell);
        cliente.setCorreoClien(correo);
        cliente.setTelClien(telefono);
        cliente.setDirClien(direccion);
    }

    private void getInfo(int idCliente) {
        prNombre = dao.obtenerClientePrNom(idCliente);
        sdNombre = dao.obtenerClienteSdNom(idCliente);
        prApell = dao.obtenerClientePrApell(idCliente);
        sdApell = dao.obtenerClienteSdApell(idCliente);
        correo = dao.obtenerClienteCorreo(idCliente);
        telefono = dao.obtenerClienteTel(idCliente);
        direccion = dao.obtenerClienteDir(idCliente);
    }
}
