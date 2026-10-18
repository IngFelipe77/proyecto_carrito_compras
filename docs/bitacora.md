# Bitácora de desarrollo

| Fecha | Avance realizado | Problema o decisión | Solución / aprendizaje | Commit |
|---|---|---|---|---|
| 16/10/2026 | Revisé la VM Lubuntu y lo ya instalado (Apache, MySQL 8.0, Java 21, Maven, Git) | La VM traía PHP 8.3 y el repo base pide PHP 7 (RNF-04); el manejador de errores del repo convierte avisos en errores fatales | Instalé PHP 7.4 junto al 8.3 con el PPA ondrej/php, a2dismod php8.3 y a2enmod php7.4; php -v = 7.4.33 | — |
| 16/10/2026 | Intento de instalar PHP 7.4 | «El paquete php7.4 no está disponible»: faltaba el repositorio extra | Agregué ppa:ondrej/php y actualicé la lista | — |
| 16/10/2026 | Instalación de PHP 7.4 | php-common rompía a php-mcrypt | Comprobé que el proyecto no usa mcrypt y lo desinstalé | — |
| 17/10/2026 | Diagramas de casos de uso y de clases (plan) en Dia | Dibujé primero el diagrama, como pide el ingeniero, antes de programar | Clases por capas: entity, repository, service y controller, con toString y multiplicidad | — |
| 17/10/2026 | Ubiqué mi repositorio y traje el código del ingeniero | La carpeta php-mvc de la VM era de otro autor (daveh), no el carrito del ingeniero | Cloné mvelasquezo/carrito-compra-basico y lo copié a php/ de mi repo; script de la base en script/cc.sql | d1668c5 |
| 17/10/2026 | Primer push de mi repo desde la VM | GitHub rechazó mi contraseña (autenticación por contraseña no soportada) | Creé un token personal (classic, scope repo) y lo guardé en la caché de Git por 8 horas | d1668c5 |
| 17/10/2026 | MySQL: preparar la base cc y el usuario carrito | No recordaba la contraseña de root («Access denied»); al reiniciar MySQL sin permisos quedó un proceso viejo bloqueando ibdata1 (error 11) | Restablecí la contraseña de root, maté el proceso viejo y arranqué el servicio normal; la base cc ya traía las 2 tablas y los 4 productos; creé el usuario carrito con permisos solo sobre cc | — |
| 17/10/2026 | PHP funcionando en su Virtual Host | Config.php usaba root/USAC123 y HOST carrito-compra.com; los Virtual Hosts viejos apuntaban a otra carpeta | Cambié el usuario a carrito, HOST a carrito-compra.localhost, reescribí el Virtual Host hacia php/public, chmod o+x en mi carpeta y escribible php/logs; probé ver, agregar y eliminar | — |
| 17/10/2026 | Creé el proyecto Spring Boot 4.1.1 (Java 21, Maven, Web, JPA, MySQL) | Initializr puede generar la versión 4.1.1.RELEASE, que no existe en Maven; la correcta es 4.1.1 | Conecté con application.properties: ddl-auto=none y naming StandardImpl; la app arrancó y se conectó a cc | 6cb47a6 |
| 17/10/2026 | Capas Entity, Repository y Service | — | Producto e ItemCarrito con @ManyToOne; repositorios con consultas derivadas; CarritoService.agregar suma si ya existe (400 y 404 con ResponseStatusException) | f07ee32, 2668d79 |
| 17/10/2026 | Controladores REST y CORS | Las carpetas controller y config quedaron anidadas por error en IntelliJ | Las reubiqué con la terminal; probé con curl 201, suma, 200, 400, 404 y 204 | 82629ba |
| 17/10/2026 | Página con fetch en carritogt.localhost | El app.js no se había creado y la página quedaba vacía (404 del archivo) | Creé frontend/js/app.js; la página lista, agrega, suma, elimina y vacía consumiendo la API | 4f831a8 |
| 17/10/2026 | Documentación: README, bitácora, diagramas, capturas y este documento | — | Subí la documentación al repositorio | — |

**Fuentes consultadas y uso de IA:** documentación de Ubuntu, MySQL y Spring; el enunciado y las convenciones del curso; y asistencia de IA (Claude) como tutor para entender conceptos y guiar los pasos. El código lo escribí y lo ejecuté en mi VM, y lo puedo explicar.
