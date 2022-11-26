<%-- 
    Document   : index
    Created on : 25/11/2022, 12:50:54 p. m.
    Author     : Nova
--%>

<%@page import="controller.LoginController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Inicio</title>
        <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <link rel="stylesheet" href="css/style-index-lvl0.css">
        <link rel="shortcut icon" href="_img/_un-optimized/iconSearchMe.png">

    </head>

    <body class="body">

        <div class="contenedor-formulario">
            <form class="formulario" id="loginForm" action="/SearchMeWebApp/LoginController" method="post">
                <div class="intro-formulario">
                    <h2>Iniciar Sesión</h2>
                </div>
                <input type="text" autocomplete="off" placeholder="Usuario" name="user">
                <input type="password" autocomplete="off" placeholder="Contraseña" name="pwd">
                <input type="submit" value="Ingresar" name="btnLogin">
            </form>
        </div>
        <%
            if (request.getParameter("log") == null) {
                request.getSession().invalidate();
            } else {
                if (request.getParameter("log").equals("false")) {
                    System.out.println("QUERQEWRQWEQWEQWE " + request.getParameter("log"));

        %>
        <input type="hidden" id="log" value="<%=request.getParameter("log")%>">
        <input type="hidden" id="msg" value="<%=request.getParameter("msg")%>">
        <input type="hidden" id="table" value="<%=request.getParameter("table")%>">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="scripts/alertaError.js"></script>
        <%                }
            }
        %>
    </body>

</html>