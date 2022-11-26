package controller;

import dao.CargosDAO;
import dao.DepartamentosDAO;
import dao.EmpleadosDAO;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("edit/editEmpleado.jsp?log=true").forward(request, response);
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
            if (dao.cargoExiste(idCargo)) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Cargo");
            } else if (dao.departamentoExiste(idDept)) {
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Departamento");
            } else {
                dao.create(empleado);
                setAttributes(request);
                request.getRequestDispatcher("/edit/editEmpleado.jsp?log=true&msg=created&table=Empleado").forward(request, response);
            }
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
                response.sendRedirect("seleccionCrud.jsp?log=true&msg=notFound&table=Empleado");
            } else {
                getInfo(idEmpleado);
                setAttributes(request);
            }
        }
        if (update) {
            empleado.setIdEmpleado(dao.buscarEmpleadoCorreo(correo));
            initComponents(request);
            setInfo();
            setAttributes(request);
            if (dao.cargoExiste(idCargo)) {
                request.getRequestDispatcher("/edit/editEmpleado.jsp?log=true&msg=notFound&table=Cargo").forward(request, response);
            } else if (dao.departamentoExiste(idDept)) {
                request.getRequestDispatcher("/edit/editEmpleado.jsp?log=true&msg=notFound&table=Departamento").forward(request, response);
            } else {
                dao.update(empleado);
                request.getRequestDispatcher("/edit/editEmpleado.jsp?log=true&msg=updated&table=Empleado").forward(request, response);
            }
        }
        if (delete) {
            dao.remove(dao.buscarEmpleadoCorreo(correo));
            setAttributes(request);
            response.sendRedirect("/SearchMeWebApp/seleccionCrud.jsp?log=true&msg=deleted&table=Empleado");
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
        direccion = request.getParameter("direccion");
        telefono = request.getParameter("telefono");
        correo = request.getParameter("correo");
        fechaNaci = request.getParameter("fechaNaci");
        ciudad = request.getParameter("ciudad");
        pais = request.getParameter("pais");
        idCargo = request.getParameter("idCargo");
        idDept = request.getParameter("idDept");
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("prNombre", prNombre);
        request.setAttribute("sdNombre", sdNombre);
        request.setAttribute("prApell", prApell);
        request.setAttribute("sdApell", sdApell);
        request.setAttribute("correo", correo);
        request.setAttribute("telefono", telefono);
        request.setAttribute("direccion", direccion);
        request.setAttribute("idCargo", idCargo);
        request.setAttribute("idDept", idDept);
        request.setAttribute("pais", pais);
        request.setAttribute("ciudad", ciudad);
        request.setAttribute("fechaNaci", fechaString);
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
