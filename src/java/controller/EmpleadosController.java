package controller;

import dao.CargosDAO;
import dao.DepartamentosDAO;
import dao.EmpleadosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Empleados;

@WebServlet("/EmpleadosController")
public class EmpleadosController extends HttpServlet {

    Empleados empleado = new Empleados();
    EmpleadosDAO dao = new EmpleadosDAO();
    CargosDAO cDao = new CargosDAO();
    DepartamentosDAO dDao = new DepartamentosDAO();
    String prNombre;
    String sdNombre = " ";
    String prApell;
    String sdApell;
    String direccion;
    String telefono;
    String correo;
    String fechaNaci;
    String ciudad;
    String pais;
    String idCargo;
    String idDept;
    String fechaString;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Empleado</title>"
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
                + "         <h1>Actualizar Empleado</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de un empleado. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/EmpleadosController\" method=\"post\">\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Primer Nombre\" name=\"prNombre\" value=\"" + prNombre + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Segundo Nombre\" name=\"sdNombre\" autocomplete=\"off\" value=\"" + sdNombre + "\">\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Primer Apellido\" name=\"prApell\" value=\"" + prApell + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Segundo Apellido\" name=\"sdApell\" value=\"" + sdApell + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Dirección\" name=\"direccion\" value=\"" + direccion + "\" autocomplete=\"off\" required>\n\"\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Teléfono\" name=\"telefono\" value=\"" + telefono + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"email\" maxlength=\"40\" placeholder=\"Correo\" name=\"correo\" value=\"" + correo + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"date\" min=\"1922-01-01\" max=\"2010-01-01\" name=\"fechaNaci\" value=\"" + fechaString + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"Ciudad\" name=\"ciudad\" value=\"" + ciudad + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"text\" maxlength=\"15\" placeholder=\"País\" name=\"pais\" value=\"" + pais + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"number\" max=\"100\" min=\"1\" placeholder=\"ID Cargo\" name=\"idCargo\" value=\"" + idCargo + "\" autocomplete=\"off\" required>\n"
                + "             <input type=\"number\" max=\"100\" min=\"1\" placeholder=\"ID Departamento\" name=\"idDept\" value=\"" + idDept + "\" autocomplete=\"off\" required>\n"
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
            if (!dao.cargoExiste(idCargo)) {
                ou.print("<script>alert(\"El cargo que intenta referenciar no existe\");"
                        + "location.href=\"index.html\" </script>");
            }
            if (!dao.departamentoExiste(idDept)) {
                ou.print("<script>alert(\"El departamento que intenta referenciar no existe\");"
                        + "location.href=\"index.html\" </script>");
            }
            dao.create(empleado);
        }
        if (read) {
            int idEmpleado = 0;
            correo = request.getParameter("correoEmpleado");
            telefono = request.getParameter("telefonoEmpleado");
            boolean correoBlank = correo.isBlank();
            boolean telefonoBlank = telefono.isBlank();
            if (!correoBlank && !telefonoBlank) {
                idEmpleado = dao.buscarEmplCorreoTel(correo, telefono);
            } else {
                if (!correoBlank) {
                    idEmpleado = dao.buscarEmpleadoCorreo(correo);
                }
                if (!telefonoBlank) {
                    idEmpleado = dao.buscarEmpleadoTel(telefono);
                }
            }
            if (idEmpleado == 0) {
                ou.print("<script>alert(\"Empleado no encontrado\");"
                        + "location.href=\"index.html\" </script>");
            } else {
                getInfo(idEmpleado);
            }
        }
        if (update) {
            empleado.setIdEmpleado(dao.buscarEmpleadoCorreo(correo));
            initComponents(request);
            setInfo();
            dao.update(empleado);
            ou.print("<script>alert(\"Empleado actualizado con éxito\");"
                    + "location.href=\"index.html\"</script>");

        }
        if (delete) {
            dao.remove(dao.buscarEmpleadoCorreo(correo));
            ou.print("<script>"
                    + "alert('Empleado eliminado con éxito');"
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
        direccion = request.getParameter("direccion");
        telefono = request.getParameter("telefono");
        correo = request.getParameter("correo");
        fechaNaci = request.getParameter("fechaNaci");
        ciudad = request.getParameter("ciudad");
        pais = request.getParameter("pais");
        idCargo = request.getParameter("idCargo");
        idDept = request.getParameter("idDept");
    }

    private void setInfo() {
        empleado.setPrNomEmpl(prNombre);
        empleado.setSdNomEmpl(sdNombre);
        empleado.setPrApellEmpl(prApell);
        empleado.setSdApellEmpl(sdApell);
        empleado.setDirEmpl(direccion);
        empleado.setTelEmpl(telefono);
        empleado.setCorreoEmpl(correo);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        Date fecha;
        try {
            fecha = formato.parse(fechaNaci);
            formato.applyPattern("yyyy-MM-dd");
            empleado.setFechaNaciEmpl(fecha);
            fechaString = formato.format(fecha);
        } catch (ParseException ex) {
            System.out.println("Error: " + ex);
        }
        empleado.setCiudEmpl(ciudad);
        empleado.setPaisEmpl(pais);
        empleado.setIdCargo(cDao.obtenerCargo(Integer.parseInt(idCargo)));
        empleado.setIdDept(dDao.obtenerDepartamento(Integer.parseInt(idDept)));
    }

    private void getInfo(int idEmpleado) {
        prNombre = dao.obtenerEmpleadoPrNom(idEmpleado);
        sdNombre = dao.obtenerEmpleadoSdNom(idEmpleado);
        prApell = dao.obtenerEmpleadoPrApell(idEmpleado);
        sdApell = dao.obtenerEmpleadoSdApell(idEmpleado);
        direccion = dao.obtenerEmpleadoDir(idEmpleado);
        telefono = dao.obtenerEmpleadoTel(idEmpleado);
        correo = dao.obtenerEmpleadoCorreo(idEmpleado);
        fechaNaci = dao.obtenerEmpleadoFechaNaci(idEmpleado);
        SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date fecha;
        try {
            fecha = formato.parse(fechaNaci);
            formato.applyPattern("yyyy-MM-dd");
            fechaString = formato.format(fecha);
        } catch (ParseException ex) {
            fechaString = null;
            System.out.println("Error: " + ex);
        }
        ciudad = dao.obtenerEmpleadoCiudad(idEmpleado);
        pais = dao.obtenerEmpleadoPais(idEmpleado);
        idCargo = dao.obtenerEmpleadoIdCargo(idEmpleado);
        idDept = dao.obtenerEmpleadoIdDept(idEmpleado);
    }
}
