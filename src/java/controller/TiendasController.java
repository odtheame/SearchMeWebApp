package controller;

import dao.BodegasDAO;
import dao.TiendasDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Tienda</title>"
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
                + "         <h1>Actualizar tienda</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de una tienda. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/TiendasController\" method=\"post\">\n"
                + "             <input type=\"text\" maxlength=\"20\" placeholder=\"Nombre\" name=\"nombre\" value=\"" + nombre + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"40\" placeholder=\"Dirección\" name=\"direccion\" value=\"" + direccion + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Telefono\" name=\"telefono\" value=\"" + telefono + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"number\" max=\"100\" min=\"1\" placeholder=\"ID Bodega\" name=\"idBodega\" value=\"" + idBodega + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"submit\" value=\"Actualizar\" name=\"btnOperacion\">\n"
                + "             <input type=\"submit\" value=\"Eliminar\" name=\"btnOperacion\"\">\n"
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
            if (!dao.bodegaExiste(idBodega)) {
                ou.print("<script>alert(\"La bodega que intenta referenciar no existe\");"
                        + "location.href=\"index.html\" </script>");
            }
            dao.create(tienda);
        }
        if (read) {
            int idTienda = 0;
            nombre = request.getParameter("nombreTienda");
            telefono = request.getParameter("telefonoTienda");
            if (!"".equals(nombre)) {
                idTienda = dao.buscarTiendaNom(nombre, dao.findAll());
            }
            if (!"".equals(telefono)) {
                idTienda = dao.buscarTiendaTel(telefono, dao.findAll());
            }
            if (idTienda == 0) {
                ou.print("<script>alert(\"Tienda no encontrada\");"
                        + "location.href=\"index.html\" </script>");
            } else {
                getInfo(idTienda);
            }
        }
        if (update) {
            tienda.setIdTienda(dao.buscarTiendaNom(nombre, dao.findAll()));
            initComponents(request);
            setInfo();
            dao.update(tienda);
            ou.print("<script>alert(\"Tienda actualizada con éxito\");"
                    + "location.href=\"index.html\"</script>");
        }
        if (delete) {
            dao.remove(dao.buscarTiendaNom(nombre, dao.findAll()));
            ou.print("<script>"
                    + "alert('Tienda eliminada con exito');"
                    + "location.href=\"index.html\"</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        processRequest(request, response);
    }

    private void initComponents(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        direccion = request.getParameter("direccion");
        telefono = request.getParameter("telefono");
        idBodega = request.getParameter("idBodega");
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
