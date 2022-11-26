<%-- 
    Document   : editRecibo
    Created on : 25/11/2022, 8:11:44 p. m.
    Author     : Nova
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Editar Recibo</title>
        <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
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
            <h1>Editar recibo</h1>
        </header>

        <%
            String fechaRecibo;
            Double valorTotal;
            int numRecibo, idTienda, idCliente;
            if (request.getParameter("log") == null) {
                response.sendRedirect("../index.jsp");
            } else {
                numRecibo = Integer.parseInt(request.getAttribute("numRecibo").toString());
                fechaRecibo = (String) request.getAttribute("fechaRecibo");
                valorTotal = Double.parseDouble(request.getAttribute("valorTotal").toString());
                idTienda = Integer.parseInt(request.getAttribute("idTienda").toString());
                idCliente = Integer.parseInt(request.getAttribute("idCliente").toString());
        %> 

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de registro de recibos. Aquí podrá establecer la información respectivamente, por favor ingrese los datos si es el caso y haga clic en actualizar o eliminar.</p>
            <form class="formulario" action="/SearchMeWebApp/RecibosController" method="post">
                <input type="number" maxlength="100000" placeholder="No. Recibo" name="numRecibo" value="<%=numRecibo%>" autocomplete="off" required>
                <input type="date" min="1922-01-01" max="2010-01-01" name="fechaRecibo" value="<%=fechaRecibo%>" autocomplete="off" required>
                <input type="number" step="0.01" min="0" placeholder="Valor Total" name="valorTotal" value="<%=valorTotal%>" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Tienda" name="idTienda" value="<%=idTienda%>" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Cliente" name="idCliente" value="<%=idCliente%>" autocomplete="off" >
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