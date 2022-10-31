const bodegasBtn = document.querySelector("#bodegasBtn"),
    cargosBtn = document.querySelector('#cargosBtn'),
    clientesBtn = document.querySelector('#clientesBtn'),
    departamentosBtn = document.querySelector('#departamentosBtn'),
    empleadosBtn = document.querySelector('#empleadosBtn'),
    recibosBtn = document.querySelector('#recibosBtn'),
    tiendasBtn = document.querySelector('#tiendasBtn'),

    bodegas = document.querySelector('.bodegasDiv'),
    cargos = document.querySelector('.cargosDiv'),
    clientes = document.querySelector('.clientesDiv'),
    departamentos = document.querySelector('.departamentosDiv'),
    empleado = document.querySelector('.empleadosDiv'),
    recibos = document.querySelector('.recibosDiv'),
    tiendas = document.querySelector('.tiendasDiv'),

    bodegasIcon = document.querySelector('#tablaBodega'),
    cargosIcon = document.querySelector('#tablaCargo'),
    clientesIcon = document.querySelector('#tablaCliente'),
    departamentosIcon = document.querySelector('#tablaDepartamento'),
    empleadosIcon = document.querySelector('#tablaEmpleado'),
    recibosIcon = document.querySelector('#tablaRecibo'),
    tiendasIcon = document.querySelector('#tablaTienda');

document.addEventListener('click', e => {

    if (e.target === bodegasBtn) {
        bodegas.className = 'active';
        cargos.className = 'inactive';
        clientes.className = 'inactive';
        departamentos.className = 'inactive';
        empleado.className = 'inactive';
        recibos.className = 'inactive';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono-active';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === cargosBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'active';
        clientes.className = 'inactive';
        departamentos.className = 'inactive';
        empleado.className = 'inactive';
        recibos.className = 'inactive';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono-active';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === clientesBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'inactive';
        clientes.className = 'active';
        departamentos.className = 'inactive';
        empleado.className = 'inactive';
        recibos.className = 'inactive';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono-active';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === departamentosBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'inactive';
        clientes.className = 'inactive';
        departamentos.className = 'active';
        empleado.className = 'inactive';
        recibos.className = 'inactive';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono-active';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === empleadosBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'inactive';
        clientes.className = 'inactive';
        departamentos.className = 'inactive';
        empleado.className = 'active';
        recibos.className = 'inactive';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono-active';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === recibosBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'inactive';
        clientes.className = 'inactive';
        departamentos.className = 'inactive';
        empleado.className = 'inactive';
        recibos.className = 'active';
        tiendas.className = 'inactive';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono-active';
        tiendasIcon.className = 'tabla-icono';
    }
    if (e.target === tiendasBtn) {
        bodegas.className = 'inactive';
        cargos.className = 'inactive';
        clientes.className = 'inactive';
        departamentos.className = 'inactive';
        empleado.className = 'inactive';
        recibos.className = 'inactive';
        tiendas.className = 'active';
        bodegasIcon.className = 'tabla-icono';
        cargosIcon.className = 'tabla-icono';
        clientesIcon.className = 'tabla-icono';
        departamentosIcon.className = 'tabla-icono';
        empleadosIcon.className = 'tabla-icono';
        recibosIcon.className = 'tabla-icono';
        tiendasIcon.className = 'tabla-icono-active';
    }
}
)