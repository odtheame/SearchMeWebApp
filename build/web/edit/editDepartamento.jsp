<%-- 
    Document   : editDepartamento
    Created on : 25/11/2022, 8:11:29 p. m.
    Author     : Nova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Editar Departamento</title>
        <meta name="viewport"
              content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css">
        <link rel = "stylesheet" type="text/css" href = "/SearchMeWebApp/css/style-index-lvl2.css">
        <link rel = "shortcut icon" href = "/SearchMeWebApp/_img/_un-optimized/iconSearchMe.png">
    </head>

    <body>
        <header class="panel-navegacion">
            <img class="iconSearchMe" src="/SearchMeWebApp/_img/_un-optimized/iconSearchMe.png" alt="Icono SearchMe" title="SearchMe.">
            <form method="post">
                <input type="submit" class="botonNav" value="Inicio" id="inicioBtn" formaction="/SearchMeWebApp/LoginController">
                <input type="submit" class="botonNav" value="Salir" id="salirBtn" formaction="/SearchMeWebApp/index.jsp">
            </form>
        </header>

        <header class="panel-crear-elemento">
            <h1>Editar departamento</h1>
        </header>

        <%
            String nombre, direccion, idBodega;
            if (request.getParameter("log") == null) {
                response.sendRedirect("../index.jsp");
            } else {
                nombre = (String) request.getAttribute("nombre");
                direccion = (String) request.getAttribute("direccion");
                idBodega = (String) request.getAttribute("idBodega");
        %>

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de creación de un departamento. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>
            <form class="formulario" action="/SearchMeWebApp/DepartamentosController" method="post">
                <input type="text" maxlength="20" placeholder="Nombre" name="nombre" value="<%=nombre%>" autocomplete="off" required>
                <input type="text" maxlength="40" placeholder="Dirección" name="direccion" value="<%=direccion%>" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Bodega" name="idBodega" value="<%=idBodega%>" autocomplete="off" required>
                <input type="submit" value="Actualizar" name="btnOperacion">
                <input type="submit" value="Eliminar" name="btnOperacion">
            </form>
        </div>
        <%}
            if (request.getParameter("table") != null) {
        %>
        <input type="hidden" id="log" value="<%=request.getParameter("log")%>">
        <input type="hidden" id="msg" value="<%=request.getParameter("msg")%>">
        <input type="hidden" id="table" value="<%=request.getParameter("table")%>">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="/SearchMeWebApp/scripts/alertaError.js"></script>
        <%            }
        %>
    </body>

</html>