package controller;

import dao.BodegasDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import model.Bodegas;

@WebServlet("/BodegasController")
public class BodegasController extends HttpServlet {

    Bodegas bodega = new Bodegas();
    BodegasDAO dao = new BodegasDAO();
    String nombre;
    String direccion;
    String telefono;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Bodega</title>"
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
                + "         <h1>Actualizar bodega</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de una bodega. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/BodegasController\" method=\"post\">\n"
                + "             <input type=\"text\" maxlength=\"20\" placeholder=\"Nombre\" name=\"nombre\" value=\"" + nombre + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"30\" placeholder=\"Direccion\" name=\"direccion\" value=\"" + direccion + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Telefono\" name=\"telefono\" value=\"" + telefono + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"submit\" value=\"Actualizar\" name=\"btnOperacion\">\n"
                + "            <input type=\"submit\" value=\"Eliminar\" name=\"btnOperacion\"\">\n"
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
            dao.create(bodega);
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
                ou.print("<script>alert(\"Bodega no encontrada\");"
                        + "location.href=\"index.html\" </script>");
            } else {
                getInfo(idBodega);
            }
        }
        if (update) {
            bodega.setIdBodega(dao.buscarBodegaNom(nombre));
            initComponents(request);
            setInfo();
            dao.update(bodega);
            ou.print("<script>alert(\"Bodega actualizada con éxito\");"
                    + "location.href=\"index.html\"</script>");
        }
        if (delete) {
            dao.remove(dao.buscarBodegaNom(nombre));
            ou.print("<script>"
                    + "alert('Bodega eliminada con éxito');"
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
