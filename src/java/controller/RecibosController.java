package controller;

import dao.ClientesDAO;
import dao.RecibosDAO;
import dao.TiendasDAO;
import java.io.IOException;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editRecibo.jsp?log=true").forward(request, response);
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
            if (dao.clienteExiste(idCliente)) {
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=notFound&table=Cliente").forward(request, response);
            } else if (dao.tiendaExiste(idTienda)) {
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=notFound&table=Tienda").forward(request, response);
            } else {
                dao.create(recibo);
                setAttributes(request);
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=created&table=Recibo").forward(request, response);
            }
        }
        if (read) {
            int idRecibo = 0;
            numRecibo = request.getParameter("numeroRecibo");
            if (!"".equals(numRecibo)) {
                idRecibo = dao.buscarReciboNum(numRecibo);
            }
            if (idRecibo == 0) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Recibo");
            } else {
                getInfo(idRecibo);
                setAttributes(request);
            }
        }
        if (update) {
            recibo.setIdRecibo(dao.buscarReciboNum(numRecibo));
            initComponents(request);
            setInfo();
            setAttributes(request);
            if (dao.tiendaExiste(idTienda)) {
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=notFound&table=Tienda").forward(request, response);
            } else if (dao.clienteExiste(idCliente)) {
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=notFound&table=Cliente").forward(request, response);
            } else {
                dao.update(recibo);
                request.getRequestDispatcher("/edit/editRecibo.jsp?log=true&msg=updated&table=Recibo").forward(request, response);
            }
        }
        if (delete) {
            dao.remove(dao.buscarReciboNum(numRecibo));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Recibo");
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
        numRecibo = request.getParameter("numRecibo");
        fechaRecibo = request.getParameter("fechaRecibo");
        valorTotal = request.getParameter("valorTotal");
        idTienda = request.getParameter("idTienda");
        idCliente = request.getParameter("idCliente");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("numRecibo", numRecibo);
        request.setAttribute("fechaRecibo", fechaString);
        request.setAttribute("valorTotal", valorTotal);
        request.setAttribute("idTienda", idTienda);
        request.setAttribute("idCliente", idCliente);
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
