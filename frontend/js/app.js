// Dirección de la API (Spring Boot). Si cambias el puerto, cámbialo aquí.
const API = 'http://localhost:8080/api';

// El "usuario" del carrito: un código guardado en el navegador (antes lo hacía la sesión de PHP)
let codUsuario = localStorage.getItem('codUsuario');
if (!codUsuario) {
    codUsuario = 'u-' + Date.now() + '-' + Math.floor(Math.random() * 1000000);
    localStorage.setItem('codUsuario', codUsuario);
}

const listaProductos = document.getElementById('lista-productos');
const zonaCarrito = document.getElementById('zona-carrito');
const btnVaciar = document.getElementById('btn-vaciar');
const mensaje = document.getElementById('mensaje');
const contador = document.getElementById('contador');

function escapar(texto) {
    const d = document.createElement('div');
    d.textContent = texto;
    return d.innerHTML;
}

function mostrarMensaje(texto, esError) {
    mensaje.textContent = texto;
    mensaje.className = esError ? 'error' : '';
    mensaje.hidden = false;
    setTimeout(() => { mensaje.hidden = true; }, 3000);
}

// Pide algo a la API y devuelve el JSON. Si algo sale mal, lanza un error con el motivo.
async function pedir(ruta, opciones) {
    const resp = await fetch(API + ruta, opciones);
    if (!resp.ok) {
        let motivo = 'la API respondió ' + resp.status;
        try { const e = await resp.json(); if (e.message) motivo = e.message; } catch (x) { }
        throw new Error(motivo);
    }
    return resp.status === 204 ? null : resp.json();
}

async function cargarProductos() {
    try {
        const productos = await pedir('/productos');
        listaProductos.innerHTML = productos.map(p => `
            <div class="tarjeta">
                <img src="${escapar(p.imagen)}" alt="${escapar(p.nombre)}">
                <div class="nombre">${escapar(p.nombre)}</div>
                <div class="codigo">Código: ${escapar(p.codigo)}</div>
                <div class="precio">Q${p.precio.toFixed(2)}</div>
                <input type="number" id="cant-${p.idProducto}" value="1" min="1">
                <button class="boton" type="button" onclick="agregar(${p.idProducto})">Agregar</button>
            </div>`).join('');
    } catch (e) {
        mostrarMensaje('No se pudieron cargar los productos: ' + e.message, true);
    }
}

async function cargarCarrito() {
    try {
        const items = await pedir('/carrito/' + codUsuario);
        contador.textContent = items.reduce((suma, i) => suma + i.cantidad, 0);
        if (items.length === 0) {
            zonaCarrito.innerHTML = '<p class="vacio">Tu carrito está vacío</p>';
            btnVaciar.hidden = true;
            return;
        }
        let total = 0;
        const filas = items.map(i => {
            const subTotal = i.producto.precio * i.cantidad;
            total += subTotal;
            return `<tr>
                <td><img src="${escapar(i.producto.imagen)}" alt=""></td>
                <td>${escapar(i.producto.nombre)}</td>
                <td>${i.cantidad}</td>
                <td>Q${i.producto.precio.toFixed(2)}</td>
                <td>Q${subTotal.toFixed(2)}</td>
                <td><button class="boton secundario" type="button" onclick="eliminar(${i.idShoppingCart})">Eliminar</button></td>
            </tr>`;
        }).join('');
        zonaCarrito.innerHTML = `
            <table>
                <thead><tr><th></th><th>Producto</th><th>Cantidad</th><th>Precio</th><th>Sub total</th><th></th></tr></thead>
                <tbody>${filas}</tbody>
            </table>
            <div id="total">Total: Q${total.toFixed(2)}</div>`;
        btnVaciar.hidden = false;
    } catch (e) {
        mostrarMensaje('No se pudo cargar el carrito: ' + e.message, true);
    }
}

async function agregar(idProducto) {
    const cantidad = parseInt(document.getElementById('cant-' + idProducto).value, 10);
    try {
        await pedir('/carrito/' + codUsuario, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idProducto: idProducto, cantidad: cantidad })
        });
        mostrarMensaje('Producto agregado');
        cargarCarrito();
    } catch (e) {
        mostrarMensaje('No se pudo agregar: ' + e.message, true);
    }
}

async function eliminar(idShoppingCart) {
    try {
        await pedir('/carrito/item/' + idShoppingCart, { method: 'DELETE' });
        mostrarMensaje('Producto eliminado');
        cargarCarrito();
    } catch (e) {
        mostrarMensaje('No se pudo eliminar: ' + e.message, true);
    }
}

btnVaciar.addEventListener('click', async () => {
    try {
        await pedir('/carrito/' + codUsuario, { method: 'DELETE' });
        mostrarMensaje('Carrito vaciado');
        cargarCarrito();
    } catch (e) {
        mostrarMensaje('No se pudo vaciar: ' + e.message, true);
    }
});

cargarProductos();
cargarCarrito();
