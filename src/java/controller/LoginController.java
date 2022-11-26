package controller;

import dao.LoginDAO;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Login;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    Login login = new Login();
    LoginDAO dao = new LoginDAO();
    String usuario;
    String clave;
    boolean errorCredenciales;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean noCredentials = request.getParameter("user") == null || request.getParameter("pwd") == null;
        if (noCredentials) {
        } else {
            usuario = request.getParameter("user");
            clave = request.getParameter("pwd");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        boolean existeUsuario;
        try {
            existeUsuario = dao.buscarLogin(usuario, clave);
            if (existeUsuario) {
                HttpSession session = request.getSession();
                session.setAttribute("isLogged", usuario);
                request.getRequestDispatcher("seleccionCrud.jsp?log=true").forward(request, response);
            } else {
                response.sendRedirect("index.jsp?log=false");
            }
        } catch (NoSuchAlgorithmException ex) {
        }
    }
}
