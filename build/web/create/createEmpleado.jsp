<%-- 
    Document   : createEmpleado
    Created on : 25/11/2022, 3:42:51 p. m.
    Author     : Nova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Crear Empleado</title>
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
            <h1>Registra un empleado</h1>
        </header>

        <div class="contenedor-contenido">
            <p>Bienvenido al panel de registro de empleados. Aquí podrá establecer la información respectivamente, por favor ingrese los datos y haga clic en crear.</p>
            <form class="formulario" action="/SearchMeWebApp/EmpleadosController" method="post">
                <input type="text" maxlength="15" placeholder="Primer Nombre" name="prNombre" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Segundo Nombre" name="sdNombre" autocomplete="off">
                <input type="text" maxlength="15" placeholder="Primer Apellido" name="prApell" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Segundo Apellido" name="sdApell" autocomplete="off" required>
                <input type="email" maxlength="40" placeholder="Correo" name="correo" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="País" name="pais" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Teléfono" name="telefono" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Ciudad" name="ciudad" autocomplete="off" required>
                <input type="text" maxlength="15" placeholder="Dirección" name="direccion" autocomplete="off" required>
                <input type="date" min="1922-01-01" max="2010-01-01" name="fechaNaci" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Cargo" name="idCargo" autocomplete="off" required>
                <input type="number" max="100" min="1" placeholder="ID Departamento" name="idDept" autocomplete="off"  required>
                <input type="submit" value="Crear" name="btnOperacion">
            </form>
        </div>

    </body>
</html>