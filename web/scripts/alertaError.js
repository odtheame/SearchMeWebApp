var logged = document.getElementById("log").value; 
var proc = document.getElementById("msg").value; 
var tabla = document.getElementById("table").value; 

console.log(logged);
console.log(proc);
console.log(tabla);

let icono;
let tipo;
let msg;

if (logged === 'false') {
    icono = 'error';
    tipo = 'Error';
    msg = 'Credenciales no válidas';
}
if (proc === 'notFound') {
    icono = 'error';
    tipo = 'Error';
    msg = tabla + ' no encontrad'+ tabla.slice(-1);
}
if (proc === 'created') {
    icono = 'success';
    tipo = 'Mensaje';
    msg = tabla + ' cread'+ tabla.slice(-1) +' con éxito';
}
if (proc === 'updated') {
    icono = 'success';
    tipo = 'Mensaje';
    msg = tabla + ' actualizad'+ tabla.slice(-1) +' con éxito';
}
if (proc === 'deleted') {
    icono = 'success';
    tipo = 'Mensaje';
    msg = tabla + ' eliminad'+ tabla.slice(-1) +' con éxito';
}
Swal.fire({
    icon: icono,
    title: tipo,
    width: 300,
    color: 'rgba(53, 48, 48, 0.602)',
    text: msg,
    background: 'rgba(247, 247, 247, 1)',
    confirmButtonText: 'Aceptar',
    allowEnterKey: false,
    confirmButtonColor: 'rgba(235, 103, 103, 0.8)',
    heightAuto: false
});




