package controller;

import dao.ClientesDAO;
import dao.RecibosDAO;
import dao.TiendasDAO;
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
import model.Recibos;

@WebServlet("/RecibosController")
public class RecibosController extends HttpServlet {

    Recibos recibo = new Recibos();
    RecibosDAO dao = new RecibosDAO();
    TiendasDAO tDao = new TiendasDAO();
    ClientesDAO cDao = new ClientesDAO();
    String numRecibo;
    String fechaRecibo;
    String valorTotal;
    String idTienda;
    String idCliente;
    String fechaString;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter ou = response.getWriter();
        ou.print("<!DOCTYPE html>\n"
                + "<html lang = \"es\">\n"
                + "<head>\n"
                + "     <meta charset = \"UTF-8\">\n"
                + "     <title>SearchMe - Actualizar Recibo</title>"
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
                + "         <h1>Actualizar recibo</h1>\n"
                + "     </header>\n"
                + "     <div class=\"contenedor-contenido\">\n"
                + "         <p>Bienvenido al panel de edición de una recibo. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>\n"
                + "         <form class=\"formulario\" action=\"/SearchMeWebApp/RecibosController\" method=\"post\">\n"
                + "            <input type=\"text\" maxlength=\"20\" placeholder=\"No. Recibo\" name=\"numRecibo\" value=\"" + numRecibo + "\" autocomplete=\"off\" disabled>\n"
                + "            <input type=\"date\" min=\"1922-01-01\" max=\"2010-01-01\" name=\"fechaRecibo\" value=\"" + fechaString + "\" autocomplete=\"off\" required>\n"
                + "            <input type=\"number\" step=\"0.01\" min=\"0\" placeholder=\"Valor Total\" name=\"valorTotal\" value=\"" + valorTotal + "\" autocomplete=\"off\" required>\n"
                + "            <input type=\"number\" max=\"100\" min=\"1\" placeholder=\"ID Tienda\" name=\"idTienda\" value=\"" + idTienda + "\" autocomplete=\"off\" required>\n"
                + "            <input type=\"number\" max=\"100\" min=\"1\" placeholder=\"ID Cliente\" name=\"idCliente\" value=\"" + idCliente + "\" autocomplete=\"off\" >\n"
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
            if (!dao.clienteExiste(idCliente)) {
                ou.print("<script>alert(\"El cliente que intenta referenciar no existe\");"
                        + "location.href=\"index.html\" </script>");
            }
            if (!dao.tiendaExiste(idTienda)) {
                ou.print("<script>alert(\"La tienda que intenta referenciar no existe\");"
                        + "location.href=\"index.html\" </script>");
            }
            dao.create(recibo);
        }
        if (read) {
            int idRecibo = 0;
            numRecibo = request.getParameter("numeroRecibo");
            if (!"".equals(numRecibo)) {
                idRecibo = dao.buscarReciboNum(numRecibo, dao.findAll());
            }
            if (idRecibo == 0) {
                ou.print("<script>alert(\"Recibo no encontrado\");"
                        + "location.href=\"index.html\" </script>");
            } else {
                getInfo(idRecibo);
            }
        }
        if (update) {
            recibo.setIdRecibo(dao.buscarReciboNum(numRecibo, dao.findAll()));
            initComponents(request);
            setInfo();
            dao.update(recibo);
            ou.print("<script>alert(\"Recibo actualizado con éxito\");"
                    + "location.href=\"index.html\"</script>");
        }
        if (delete) {
            dao.remove(dao.buscarReciboNum(numRecibo, dao.findAll()));
            ou.print("<script>"
                    + "alert('Recibo eliminado con exito');"
                    + "location.href=\"index.html\"</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        processRequest(request, response);
    }

    private void initComponents(HttpServletRequest request) {
        numRecibo = request.getParameter("numRecibo");
        fechaRecibo = request.getParameter("fechaRecibo");
        valorTotal = request.getParameter("valorTotal");
        idTienda = request.getParameter("idTienda");
        idCliente = request.getParameter("idCliente");
    }

    private void setInfo() {
        recibo.setNumRecibo(Integer.parseInt(numRecibo));
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        Date fecha;
        try {
            fecha = formato.parse(fechaRecibo);
            formato.applyPattern("yyyy-MM-dd");
            recibo.setFechaCompra(fecha);
            fechaString = formato.format(fecha);
        } catch (ParseException ex) {
            System.out.println("Error: " + ex);
        }
        recibo.setValorTotal(Double.parseDouble(valorTotal));
        recibo.setIdTienda(tDao.obtenerTienda(Integer.parseInt(idTienda)));
        recibo.setIdCliente(cDao.obtenerCliente(Integer.parseInt(idCliente)));
    }

    private void getInfo(int idRecibo) {
        numRecibo = dao.obtenerReciboNum(idRecibo);
        fechaRecibo = dao.obtenerReciboFecha(idRecibo);
        SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date fecha;
        try {
            fecha = formato.parse(fechaRecibo);
            formato.applyPattern("yyyy-MM-dd");
            fechaString = formato.format(fecha);
        } catch (ParseException ex) {
            fechaString = null;
            System.out.println("Error: " + ex);
        }
        valorTotal = dao.obtenerReciboValor(idRecibo);
        idTienda = dao.obtenerReciboIdTienda(idRecibo);
        idCliente = dao.obtenerReciboIdCliente(idRecibo);
    }
}
