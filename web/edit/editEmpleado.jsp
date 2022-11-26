<%-- 
    Document   : editEmpleado
    Created on : 25/11/2022, 8:11:37 p. m.
    Author     : Nova
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Editar Empleado</title>
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
            <h1>Editar empleado</h1>
        </header>

        <%
            String prNombre, sdNombre, prApell, sdApell, correo, pais, telefono, ciudad, direccion, fechaNaci;
            int idCargo, idDept;
            if (request.getParameter("log") == null) {
                response.sendRedirect("../index.jsp");
            } else {
                prNombre = (String) request.getAttribute("prNombre");
                sdNombre = (String) request.getAttribute("sdNombre");
                prApell = (String) request.getAttribute("prApell");
                sdApell = (String) request.getAttribute("sdApell");
                correo = (String) request.getAttribute("correo");
                pais = (String) request.getAttribute("pais");
                telefono = (String) request.getAttribute("telefono");
                ciudad = (String) request.getAttribute("ciudad");
                direccion = (String) request.getAttribute("direccion");
                fechaNaci = (String) request.getAttribute("fechaNaci");
                idCargo = Integer.parseInt(request.getAttribute("idCargo").toString());
                idDept = Integer.parseInt(request.getAttribute("idDept").toString());
        %> 

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de registro de empleados. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>
            <form class="formulario" action="/SearchMeWebApp/EmpleadosController" method="post">
                <input type="text" maxlength="15" placeholder="Primer Nombre" name="prNombre" value="<%=prNombre%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Segundo Nombre" name="sdNombre" value="<%=sdNombre%>" autocomplete="off" >
                <input type="text" maxlength="15" placeholder="Primer Apellido" name="prApell" value="<%=prApell%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Segundo Apellido" name="sdApell" value="<%=sdApell%>" autocomplete="off" required>
                <input type="email" maxlength="40" placeholder="Correo" name="correo" value="<%=correo%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="País" name="pais" value="<%=pais%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Teléfono" name="telefono" value="<%=telefono%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Ciudad" name="ciudad" value="<%=ciudad%>" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Dirección" name="direccion" value="<%=direccion%>" autocomplete="off" required>
                <input type="date" min="1922-01-01" max="2010-01-01" name="fechaNaci" value="<%=fechaNaci%>" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Cargo" name="idCargo" value="<%=idCargo%>" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Departamento" name="idDept" value="<%=idDept%>" autocomplete="off" required>
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