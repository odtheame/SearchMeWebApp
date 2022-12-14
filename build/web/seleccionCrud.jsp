<%-- 
    Document   : seleccionCrud.jsp
    Created on : 25/11/2022, 4:30:35 p. m.
    Author     : Nova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SearchMe - Inicio</title>
        <meta name="viewport"
              content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css">
        <link rel="stylesheet" href="css/style-index-lvl1.css">
        <link rel="shortcut icon" href="_img/_un-optimized/iconSearchMe.png">

    </head>

    <body class="body">

        <div class="contenedor-panel-seleccion">

            <div class="panel-seleccion">
                <div class="mensajeSeleccion">
                    <div class="icono-panel-tabla">
                        <img class="iconSearchMe" src="_img/_un-optimized/iconSearchMeFull.png" alt="Icono SearchMe" title="SearchMe.">
                    </div>
                </div>
                <div class="tabla-icono" id="tablaBodega">
                    <div class="icono">
                        <i class='bx bx-package'></i>
                    </div>
                    <button class="botonTabla" id="bodegasBtn">Bodegas</button>
                </div>
                <div class="tabla-icono" id="tablaCargo">
                    <div class="icono">
                        <i class='bx bx-briefcase-alt'></i>
                    </div>
                    <button class="botonTabla" id="cargosBtn">Cargos</button>
                </div>
                <div class="tabla-icono" id="tablaCliente">
                    <div class="icono">
                        <i class='bx bx-face'></i>
                    </div>
                    <button class="botonTabla" id="clientesBtn">Clientes</button>
                </div>
                <div class="tabla-icono" id="tablaDepartamento">
                    <div class="icono">
                        <i class='bx bx-buildings'></i>
                    </div>
                    <button class="botonTabla" id="departamentosBtn">Departamentos</button>
                </div>
                <div class="tabla-icono" id="tablaEmpleado">
                    <div class="icono">
                        <i class='bx bx-user'></i>
                    </div>
                    <button class="botonTabla" id="empleadosBtn">Empleados</button>
                </div>
                <div class="tabla-icono" id="tablaRecibo">
                    <div class="icono">
                        <i class='bx bx-money-withdraw'></i>
                    </div>
                    <button class="botonTabla" id="recibosBtn">Recibos</button>
                </div>
                <div class="tabla-icono" id="tablaTienda">
                    <div class="icono">
                        <i class='bx bx-store'></i>
                    </div>
                    <button class="botonTabla" id="tiendasBtn">Tiendas</button>
                </div>
            </div>
        </div>

        <div class="contenedor-formulario bodegasDiv">
            <form class="formulario" id="bodegasForm" method="post">
                <div class="intro-formulario">
                    <h2>Bodegas</h2>
                </div>
                <input type="text" placeholder="Nombre" autocomplete="off" name="nombreBodega">
                <input type="text" placeholder="Teléfono" autocomplete="off" name="telefonoBodega">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createBodega.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/BodegasController">
            </form>
        </div>

        <div class="contenedor-formulario cargosDiv ocultoDiv">
            <form class="formulario" id="cargosForm" method="post">
                <div class="intro-formulario">
                    <h2>Cargos</h2>
                </div>
                <input type="text" placeholder="Nombre" autocomplete="off" name="nombreCargo">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createCargo.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/CargosController">
            </form>
        </div>

        <div class="contenedor-formulario ocultoDiv clientesDiv">
            <form class="formulario" id="clientesForm" method="post">
                <div class="intro-formulario">
                    <h2>Clientes</h2>
                </div>
                <input type="email" placeholder="Correo" autocomplete="off" name="correoCliente">
                <input type="text" placeholder="Teléfono" autocomplete="off" name="telefonoCliente">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createCliente.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/ClientesController">
            </form>
        </div>

        <div class="contenedor-formulario ocultoDiv departamentosDiv">
            <form class="formulario" id="departamentosForm" method="post">
                <div class="intro-formulario">

                    <h2>Departamentos</h2>
                </div>
                <input type="text" placeholder="Nombre" autocomplete="off" name="nombreDepartamento">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createDepartamento.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/DepartamentosController">
            </form>
        </div>

        <div class="contenedor-formulario empleadosDiv ocultoDiv">
            <form class="formulario" id="empleadosForm" method="post">
                <div class="intro-formulario">
                    <h2>Empleados</h2>
                </div>
                <input type="email" placeholder="Correo" autocomplete="off" name="correoEmpleado">
                <input type="text" placeholder="Teléfono" autocomplete="off" name="telefonoEmpleado">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createEmpleado.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/EmpleadosController">
            </form>
        </div>

        <div class="contenedor-formulario recibosDiv ocultoDiv">
            <form class="formulario" id="recibosForm" method="post">
                <div class="intro-formulario">
                    <h2>Recibos</h2>
                </div>
                <input type="number" placeholder="Número" autocomplete="off" name="numeroRecibo">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createRecibo.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/RecibosController">
            </form>
        </div>

        <div class="contenedor-formulario tiendasDiv ocultoDiv">
            <form class="formulario" id="tiendasForm" method="post">
                <div class="intro-formulario">
                    <h2>Tiendas</h2>
                </div>
                <input type="text" placeholder="Nombre" autocomplete="off" name="nombreTienda">
                <input type="text" placeholder="Teléfono" autocomplete="off" name="telefonoTienda">
                <input type="submit" value="Crear" name="btnOperacion" formaction="create/createTienda.jsp">
                <input type="submit" value="Buscar" name="btnOperacion" formaction="/SearchMeWebApp/TiendasController">
            </form>
        </div>

        <script src="scripts/index-tables.js"></script>

        <%
            if (request.getParameter("log") == null) {
                response.sendRedirect("index.jsp");
            }
            if (request.getParameter("table") == null) {
            } else {
        %>
        <input type="hidden" id="log" value="<%=request.getParameter("log")%>">
        <input type="hidden" id="msg" value="<%=request.getParameter("msg")%>">
        <input type="hidden" id="table" value="<%=request.getParameter("table")%>">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="scripts/alertaError.js"></script>
        <%
            }
        %>

    </body>
</html>
