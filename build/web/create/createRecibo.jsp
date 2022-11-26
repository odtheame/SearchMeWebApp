<%-- 
    Document   : createRecibo
    Created on : 25/11/2022, 3:42:57 p. m.
    Author     : Nova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Crear Recibo</title>
        <meta name="viewport"
              content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css">
        <link rel="stylesheet" href="../css/style-index-lvl2.css">
        <link rel="shortcut icon" href="../_img/_un-optimized/iconSearchMe.png">
    </head>
    <body>

        <header class="panel-navegacion">
            <img class="iconSearchMe" src="../_img/_un-optimized/iconSearchMe.png" alt="Icono SearchMe" title="SearchMe.">
            <form action="/SearchMeWebApp/LoginController" method="post">
                <button class="botonNav" id="inicioBtn">Inicio</button>
                <button class="botonNav" id="salirBtn">Salir</button>
            </form>
        </header>

        <header class="panel-crear-elemento">
            <h1>Crea un recibo</h1>
        </header>

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de registro de recibos. Aquí podrá establecer la información respectivamente, por favor ingrese los datos y haga clic en crear.</p>
            <form class="formulario" action="/SearchMeWebApp/RecibosController" method="post">
                <input type="number" maxlength="100000" placeholder="No. Recibo" name="numRecibo" autocomplete="off" required>
                <input type="date" min="1922-01-01" max="2010-01-01" name="fechaRecibo" autocomplete="off" required>
                <input type="number" step="0.01" min="0" placeholder="Valor Total" name="valorTotal" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Tienda" name="idTienda" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Cliente" name="idCliente" autocomplete="off">
                <input type="submit" value="Crear" name="btnOperacion">
            </form>
        </div>

    </body>
</html>