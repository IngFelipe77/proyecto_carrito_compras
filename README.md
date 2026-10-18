# CarritoGT

Migración de un carrito de compra hecho en PHP (proyecto base del ingeniero [mvelasquezo/carrito-compra-basico](https://github.com/mvelasquezo/carrito-compra-basico)) a una **API REST con Spring Boot** organizada por capas, con una página en HTML y JavaScript que la consume con `fetch`. Todo corre dentro de una máquina virtual Lubuntu en VirtualBox.

| Parte | Tecnología |
|---|---|
| Entorno | Windows (anfitrión), VirtualBox, Lubuntu 24.04 (invitado) |
| Versión 1 (`php/`) | Apache 2.4, PHP 7.4, MySQL 8.0, Twig (del repo base) |
| Versión 2 (`springboot/`) | Java 21, Spring Boot 4.1.1, Maven, Spring Data JPA, MySQL Driver |
| Página (`frontend/`) | HTML, CSS y JavaScript con `fetch` |

## 1. Requisitos

- **Versión PHP:** Apache 2.4 con `mod_rewrite`, PHP 7.4 con `php7.4-mysql`, MySQL 8.0.
- **Versión Spring Boot:** JDK 21, Maven (o `./mvnw`), MySQL 8.0 con la base `cc`.

## 2. Instalación y configuración

1. Importar el script de la base: `mysql -u root -p < script/cc.sql` (crea la base `cc`, sus 2 tablas y 4 productos).
2. Crear el usuario de la aplicación (no se usa `root`):
   ```sql
   CREATE USER 'carrito'@'localhost' IDENTIFIED WITH mysql_native_password BY 'una_clave';
   GRANT ALL PRIVILEGES ON cc.* TO 'carrito'@'localhost';
   ```
   La contraseña `una_clave` es local, solo de esta VM, y se documenta aquí a propósito (RNF-09).
3. **PHP:** editar `php/App/Config.php` (`DB_USER`, `DB_PASSWORD` y `HOST`) y crear el Virtual Host `carrito-compra.localhost` apuntando a `php/public` (con `AllowOverride All` y `a2enmod rewrite`).
4. **Spring Boot:** revisar `springboot/carritogt-api/src/main/resources/application.properties` (usuario y contraseña de la base).
5. **Página:** crear el Virtual Host `carritogt.localhost` apuntando a `frontend/`.
6. Agregar a `/etc/hosts`: `127.0.0.1 carrito-compra.localhost carritogt.localhost`.

## 3. Cómo iniciar y acceder

- Servicios: `sudo systemctl start apache2 mysql` (comprobar con `systemctl status`).
- Versión PHP: <http://carrito-compra.localhost>
- API: desde `springboot/carritogt-api`, ejecutar `mvn spring-boot:run` (o `./mvnw spring-boot:run`). Responde en <http://localhost:8080/api/productos>.
- Página con la API: <http://carritogt.localhost> (la API debe estar corriendo).

## 4. Archivos de configuración que hay que cambiar

| Archivo | Qué cambiar |
|---|---|
| `php/App/Config.php` | `DB_USER`, `DB_PASSWORD`, `HOST` |
| `springboot/carritogt-api/src/main/resources/application.properties` | usuario y contraseña de la base |
| `frontend/js/app.js` | constante `API` si cambia el puerto |
| `springboot/.../config/CorsConfig.java` | origen permitido si cambia el nombre de la página |

## 5. Estructura de la API

```
gt.carritogt
├── entity       Producto, ItemCarrito (representan las tablas)
├── repository   ProductoRepository, ItemCarritoRepository (acceso a la base)
├── service      ProductoService, CarritoService (reglas del negocio)
├── controller   ProductoController, CarritoController, AgregarRequest (HTTP y JSON)
└── config       CorsConfig
```

## 6. Endpoints principales

| Método y dirección | Qué hace | Respuesta |
|---|---|---|
| `GET /api/productos` | lista los productos | 200 |
| `GET /api/carrito/{codUsuario}` | lista el carrito del usuario | 200 |
| `POST /api/carrito/{codUsuario}` | agrega un producto (`{"idProducto":2,"cantidad":1}`); suma si ya estaba | 201 · 400 cantidad inválida · 404 producto inexistente |
| `DELETE /api/carrito/item/{idShoppingCart}` | quita un renglón | 204 · 404 |
| `DELETE /api/carrito/{codUsuario}` | vacía el carrito | 204 |

## 7. Documentación

En `docs/`: diagramas (casos de uso y clases, hechos en Dia), capturas, bitácora y el documento técnico.

## 8. Problemas conocidos y limitaciones

- El carrito se identifica con un código que la página guarda en el navegador (`localStorage`); no hay inicio de sesión.
- Si el cliente no manda `idProducto` al agregar, la API responde 500 en vez de 400 (pendiente de validar).
- La contraseña de la base está en texto plano en `application.properties`, aceptable solo para esta VM de práctica.
