package controller;

import dao.CargosDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Cargo</title>"
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
                + "         <h1>Actualizar cargo</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de un cargo. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/CargosController\" method=\"post\">\n"
                + "             <input type=\"text\" maxlength=\"20\" placeholder=\"Nombre\" name=\"nombre\" value=\"" + nombre + "\" autocomplete=\"off\" required>\n"
                + "            <input type=\"number\" step=\"0.01\" placeholder=\"Salario\" name=\"salario\" value=\"" + salario + "\" autocomplete=\"off\" required>\n"
                + "            <textarea maxlength=\"256\" placeholder=\"Descripción\" name=\"descripcion\" autocomplete=\"off\" required>" + descripcion + "</textarea>\n"
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
        switch (metodoCRUD) {
            case "Crear":
                initComponents(request);
                setInfo();
                dao.create(cargo);
                break;
        }
        if (create) {
            initComponents(request);
            setInfo();
            dao.create(cargo);
        }
        if (read) {
            int idCargo = 0;
            nombre = request.getParameter("nombreCargo");
            if (!"".equals(nombre)) {
                idCargo = dao.buscarCargoNom(nombre);
            }
            if (idCargo == 0) {
                ou.print("<script>alert(\"Cargo no encontrado\");"
                        + "location.href=\"index.html\" </script>");
            } else {
                getInfo(idCargo);
            }
        }
        if (update) {
            cargo.setIdCargo(dao.buscarCargoNom(nombre));
            initComponents(request);
            setInfo();
            dao.update(cargo);
            ou.print("<script>alert(\"Cargo actualizado con éxito\");"
                    + "location.href=\"index.html\"</script>");
        }
        if (delete) {
            dao.remove(dao.buscarCargoNom(nombre));
            ou.print("<script>"
                    + "alert('Cargo eliminado con éxito');"
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
        salario = request.getParameter("salario");
        descripcion = request.getParameter("descripcion");
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
