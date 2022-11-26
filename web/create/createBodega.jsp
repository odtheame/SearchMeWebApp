<%-- 
    Document   : createBodega
    Created on : 25/11/2022, 3:40:11 p. m.
    Author     : Nova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Crear Bodega</title>
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
            <form method="post">
                <input type="submit" class="botonNav" value="Inicio" id="inicioBtn" formaction="/SearchMeWebApp/LoginController">
                <input type="submit" class="botonNav" value="Salir" id="salirBtn" formaction="../index.jsp">
            </form>
        </header>

        <header class="panel-crear-elemento">
            <h1>Crea una bodega</h1>
        </header>

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de creación de una bodega. Aquí podrá establecer la información respectivamente, por favor ingrese los datos y haga clic en crear.</p>
            <form class="formulario" action="/SearchMeWebApp/BodegasController" method="post">
                <input type="text" maxlength="20" placeholder="Nombre" name="nombre" autocomplete="off" required>
                <input type="text" maxlength="30" placeholder="Direccion" name="direccion" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Telefono" name="telefono" autocomplete="off" required>
                <input type="submit" value="Crear" name="btnOperacion">
            </form>
        </div>

    </body>
</html>