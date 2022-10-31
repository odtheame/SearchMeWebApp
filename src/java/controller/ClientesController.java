package controller;

import dao.ClientesDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Cliente</title>"
                + "     <meta name = \"viewport\" content = \"width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0\">\n"
                + "     <link rel = \"stylesheet\" href = \"https://use.fontawesome.com/releases/v5.6.3/css/all.css\">\n"
                + "     <link rel = \"stylesheet\" href = \"https://use.fontawesome.com/releases/v5.6.3/css/all.css\">\n"
                + "     <link rel = \"stylesheet\" href = \"https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css\">\n"
                + "     <link rel = \"stylesheet\" type=\"text/css\" href = \"/SearchMeWebApp/css/style-index-lvl2.css\">\n"
                + "     <link rel = \"shortcut icon\" href = \"/SearchMeWebApp/_img/_un-optimized/iconSearchMe.png\">\n"
                + "</head>\n"
                + "<body>\n"
                + "     <header class=\"panel-navegacion\">\n"
                + "         <img class=\"iconSearchMe\" src=\"/SearchMeWebApp/_img/_un-optimized/iconSearchMe.png\" alt=\"Icono SearchMe\" title=\"SearchMe.\">\n"
                + "         <button class=\"botonNav\" id=\"inicioBtn\" onclick=\"location.href='index.html'\">Inicio</button>\n"
                + "         <button class=\"botonNav\" id=\"salirBtn\" onclick=\"location.href='index.html'\">Salir</button>\n"
                + "     </header>\n"
                + "     <header class=\"panel-crear-elemento\">\n"
                + "         <h1>Actualizar Cliente</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de un cliente. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/ClientesController\" method=\"post\">\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Primer Nombre\" name=\"prNombre\" value=\""+ prNombre +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Segundo Nombre\" name=\"sdNombre\" autocomplete=\"off\" value=\""+ sdNombre +"\">\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Primer Apellido\" name=\"prApell\" value=\""+ prApell +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Segundo Apellido\" name=\"sdApell\" value=\""+ sdApell +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"email\" maxlength=\"40\" placeholder=\"Correo\" name=\"correo\" value=\""+ correo +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Teléfono\" name=\"telefono\" value=\""+ telefono +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Dirección\" name=\"direccion\" value=\""+ direccion +"\" autocomplete=\"off\" required>\n"
                + "             <input type=\"submit\" value=\"Actualizar\" name=\"btnOperacion\">\n"
                + "             <input type=\"submit\" value=\"Eliminar\" name=\"btnOperacion\">\n"
                + "         </form>\n"
                + "     </div>\n"
                + "<body>\n"
                + "</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter ou = response.getWriter();
        String metodoCRUD = request.getParameter("btnOperacion");
        boolean create = "Crear".equals(metodoCRUD);
        boolean read = "Buscar".equals(metodoCRUD);
        boolean update = "Actualizar".equals(metodoCRUD);
        boolean delete = "Eliminar".equals(metodoCRUD);
        if (create) {
            initComponents(request);
            setInfo();
            dao.create(cliente);
        }
        if (read) {
            int idCliente = 0;
            correo = request.getParameter("correoCliente");
            telefono = request.getParameter("telefonoCliente");
            if (!"".equals(correo)) {
                idCliente = dao.buscarClienteCorreo(correo, dao.findAll());
            }
            if (!"".equals(telefono)) {
                idCliente = dao.buscarClienteTel(telefono, dao.findAll());
            }
            if (idCliente == 0) {
                ou.print("<script>alert(\"Cliente no encontrado\");"
                        + "location.href=\"index.html\" </script>");

            } else {
                getInfo(idCliente);
            }
        }
        if (update) {
            cliente.setIdCliente(dao.buscarClienteCorreo(correo, dao.findAll()));
            initComponents(request);
            setInfo();
            dao.update(cliente);
            ou.print("<script>alert(\"Cliente actualizado con éxito\");"
                    + "location.href=\"index.html\"</script>");

        }
        if (delete) {
            dao.remove(dao.buscarClienteCorreo(correo, dao.findAll()));
            ou.print("<script>"
                    + "alert('Cliente eliminado con éxito');"
                    + "location.href=\"index.html\"</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        processRequest(request, response);
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
