# Gestión de productos — NicaMarket

API REST con Spring Boot, Spring Data JPA, Hibernate, Flyway y **autenticación JWT** (Spring Security 6 / OAuth2 Resource Server).

Rama de esta entrega: `seguridad-jwt`.

---

## Seguridad JWT (práctica)

### Caso

Comercial NicaMarket, S. A. Protege el CRUD: solo usuarios autenticados consultan; solo ADMIN modifica.

| Rol | Permisos |
|-----|----------|
| Invitado | `/api/publico/saludo`, `/api/auth/login` |
| USER | Consultar productos y categorías |
| ADMIN | Consultar y modificar productos y categorías |

### Usuarios de laboratorio

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| `admin` | `Admin123!` | ADMIN |
| `operador` | `Operador123!` | USER |

Solo desarrollo. No usar en producción.

### Configurar el secreto (obligatorio)

1. Ejecutar `ni.edu.uam.gestionproductos.util.GenerarClave` (Main) y copiar el Base64.
2. En IntelliJ → Run/Debug Configuration → Environment variables:
   `JWT_SECRET_BASE64=<valor generado>`
3. Reiniciar la aplicación.

**No subir el secreto ni tokens al repositorio.**

### Cómo arrancar

1. PostgreSQL con base `gestion_productos`.
2. Variable `JWT_SECRET_BASE64` configurada.
3. Ejecutar la aplicación Spring Boot.

### Endpoints de autenticación

| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/publico/saludo` | Público |
| POST | `/api/auth/login` | Público |
| GET | `/api/auth/perfil` | Autenticado (JWT) |

**Login**

`POST http://localhost:8080/api/auth/login`

```json
{
  "username": "admin",
  "password": "Admin123!"
}
```

Respuesta: `{ "token": "...", "type": "Bearer" }`.  
En Postman: Authorization → Bearer Token → pegar `token`.

### Reglas de autorización

| Recurso | GET | POST / PUT / DELETE |
|---------|-----|---------------------|
| `/api/productos/**` | USER, ADMIN | solo ADMIN |
| `/api/categorias/**` | USER, ADMIN | solo ADMIN (reto final) |
| Otros `/api/**` | autenticado | autenticado |

### Pruebas Postman (productos)

| # | Solicitud | Token | Esperado |
|---|-----------|-------|----------|
| 1 | GET `/api/publico/saludo` | Sin token | 200 |
| 2 | POST `/api/auth/login` (válido) | — | 200 + JWT |
| 3 | POST `/api/auth/login` (mala clave) | — | 401 |
| 4 | GET `/api/productos` | Sin token | 401 |
| 5 | GET `/api/productos` | USER (`operador`) | 200 |
| 6 | POST `/api/productos` | USER | 403 |
| 7 | GET `/api/productos` | Token modificado | 401 |
| 8 | POST `/api/productos` | ADMIN | 200 (si el JSON es válido) |

Login USER:

```json
{ "username": "operador", "password": "Operador123!" }
```

### Reto final: categorías

Mismas reglas que productos. Evidencias sugeridas:

1. GET `/api/categorias` sin token → **401** (falta autenticación).
2. GET `/api/categorias` con USER → **200**.
3. POST `/api/categorias` con USER → **403** (autenticado, sin permiso).
4. POST `/api/categorias` con ADMIN → **200** (datos válidos).
5. PUT o DELETE con ADMIN → **200 / 204**; con token inválido → **401**.

### Reto opcional

`GET /api/auth/perfil` con Bearer Token → `username` y `roles` del JWT. Sin token → 401.

### Archivos nuevos

```
config/SecurityConfig.java
Controllers/AuthController.java
Controllers/PublicoController.java
DTO/LoginRequest.java
DTO/LoginResponse.java
Services/TokenService.java
util/GenerarClave.java
```

### Autenticación vs autorización

- **Autenticación**: ¿quién eres? (login + JWT válido).
- **Autorización**: ¿qué puedes hacer? (roles USER / ADMIN → 403 si no alcanza).

### Preguntas de cierre

**¿En qué se diferencian HTTP 401 y 403?**  
**401 Unauthorized**: no hay identidad válida (sin token, token inválido o vencido).  
**403 Forbidden**: la identidad es válida, pero el rol no permite la operación.

**¿Por qué no basta con leer el contenido de un JWT para confiar en él?**  
El payload es legible; cualquiera puede fabricar un JSON. Hay que **verificar la firma** con la clave secreta y comprobar el vencimiento.

**¿Qué función cumple JwtDecoder?**  
Valida el JWT (firma HS256, formato y expiración) y entrega los claims para la seguridad de Spring.

**¿Qué riesgos ocasionaría publicar la clave de firma?**  
Un atacante podría emitir tokens falsos como ADMIN y acceder o modificar datos sin credenciales reales.

### Lista de cotejo

| Criterio | Estado |
|----------|--------|
| El proyecto inicia y conserva el CRUD | Logrado |
| Login genera JWT firmado con vencimiento (30 min) | Logrado |
| Consulta de productos requiere autenticación | Logrado |
| USER no puede modificar productos | Logrado |
| Reto: categorías protegidas por rol | Logrado |
| Postman: 200, 401, 403 | Registrar capturas |
| Explica autenticación vs autorización | Ver arriba |
| Repositorio sin secretos ni tokens | No incluir `JWT_SECRET_BASE64` |

---

## Estructura del proyecto (CRUD)

```
Controllers/   Producto, Categoria, Proveedor, Etiqueta, Auth, Publico
Services/      Producto, Categoria, Proveedor, Etiqueta, Token
Repositories/  Producto, Categoria, Proveedor, Etiqueta
Entity/        Producto, Categoria, Proveedor, Etiqueta
DTO/           Request/Response + LoginRequest/LoginResponse
config/        SecurityConfig
db/migration/  V1 … V4
```

Capas: **Controller → Service → Repository → Entity**, con **DTO** de entrada/salida.

---

## Preguntas (práctica anterior)

### ¿Cuál es la función de Spring Data JPA?

Reduce el código de persistencia: defines un repositorio (`JpaRepository`) y Spring genera CRUD y consultas por nombre de método.

### ¿Qué función cumple Hibernate?

Es el ORM que traduce entidades Java a SQL y ejecuta las operaciones contra PostgreSQL.

### ¿Qué diferencia existe entre JPA y Hibernate?

**JPA** es la especificación. **Hibernate** es una implementación de esa especificación.

### ¿Qué función cumple `@Entity`?

Marca una clase como tabla persistible; cada instancia es una fila.

### ¿Qué función cumple `@ManyToOne`?

Varios registros de un lado apuntan a uno del otro (varios productos → una categoría o un proveedor).

### ¿Qué función cumple `@JoinColumn`?

Nombra la columna FK en la tabla dueña (`categoria_id`, `proveedor_id`).

### ¿Qué función cumple `JpaRepository`?

Aporta `save`, `findAll`, `findById`, `deleteById` y consultas derivadas.

### ¿Por qué se utilizan migraciones?

Versionan el esquema (V1, V2, V3, V4) de forma ordenada y repetible. `ddl-auto=validate` solo comprueba que coincida.

### ¿Qué diferencia existe entre V1, V2 y V3?

| Migración | Qué hace |
|-----------|----------|
| **V1** | Tablas `categoria` y `producto`. |
| **V2** | Columna `descripcion` en `producto`. |
| **V3** | Tabla `proveedor`, FK `proveedor_id`, dos proveedores y productos. |
| **V4** | Tablas `etiqueta` y `producto_etiqueta` (N a N). |

### ¿Qué problema puede producir una relación bidireccional al generar JSON?

Ciclo infinito (producto → categoría → productos → …). Se evita con `@JsonIgnore` en `Categoria.productos` y `Proveedor.productos`.

---

## Preguntas de comprobación (esta entrega)

### ¿Cuál es la función de una clase Service?

Agrupa la lógica de negocio: validar IDs, armar entidades, asociar relaciones. El controlador solo recibe HTTP y delega.

### ¿Por qué un controlador no debería contener toda la lógica de negocio?

Porque mezcla HTTP con reglas de dominio, dificulta pruebas y reutilización. El servicio se puede usar desde otro controlador o prueba sin depender de la web.

### ¿Qué es un DTO?

Un objeto de transferencia: el JSON que entra o sale de la API, sin ser necesariamente la entidad de base de datos.

### ¿Qué diferencia existe entre un DTO y una entidad JPA?

La **entidad** está mapeada a una tabla (`@Entity`). El **DTO** (`ProductoRequestDTO`) solo transporta datos de la petición (`categoriaId` en lugar de un objeto `Categoria`).

### ¿Qué ventaja tiene recibir `categoriaId` en lugar de una entidad `Categoria` completa?

El cliente envía un identificador simple. El servicio carga la categoría real; se evitan JSON anidados incompletos y se valida que la categoría exista.

### ¿Qué relación representan `@OneToMany` y `@ManyToOne`?

Es la misma relación vista de los dos lados: muchos productos (`@ManyToOne`) pertenecen a una categoría (`@OneToMany` en `Categoria`).

### ¿Dónde se almacena la clave foránea en la relación Categoria–Producto?

En la tabla **`producto`**, columna **`categoria_id`**. `Categoria` no guarda IDs de productos; solo mapea la lista con `mappedBy = "categoria"`.

### ¿Qué representa `@ManyToMany`?

Una relación N a N: un producto puede tener varias etiquetas y una etiqueta puede aplicarse a varios productos.

### ¿Cuál es la función de `@JoinTable`?

Indica la tabla intermedia y las columnas FK (`producto_etiqueta.producto_id` y `etiqueta_id`).

### ¿Por qué se necesita la tabla `producto_etiqueta`?

Una columna en `producto` no puede guardar varios IDs de etiqueta de forma normalizada. La tabla puente guarda cada par producto–etiqueta.

### ¿Qué responsabilidad corresponde al Repository?

Acceso a datos: CRUD y consultas (`findByCategoriaId`, `findByEtiquetasId`). No decide reglas de negocio.

### Explique el flujo: Cliente → Controller → Service → Repository → PostgreSQL

1. **Cliente** (Postman) envía HTTP (JSON o path).
2. **Controller** recibe la petición y llama al servicio.
3. **Service** aplica la lógica (buscar categoría, armar `Producto`, agregar etiqueta).
4. **Repository** ejecuta JPA/Hibernate.
5. **PostgreSQL** persiste o consulta; la respuesta vuelve por el mismo camino.

---

## Entrega: evidencias

| Evidencia | Dónde está |
|-----------|------------|
| Capas controller, service, repository, entity, dto | Paquetes listados arriba |
| `ProductoService` | `Services/ProductoService.java` |
| `ProductoRequestDTO` | `DTO/ProductoRequestDTO.java` |
| CRUD de productos | GET/POST/PUT/DELETE `/api/productos` |
| Productos por categoría | `GET /api/productos/categoria/{id}` |
| Migración V4 | `V4__crear_etiquetas.sql` |
| Entidad y repo `Etiqueta` | `Entity/Etiqueta.java`, `EtiquetaRepository` |
| Relación N–N | `@ManyToMany` + `@JoinTable` en `Producto` |
| Tabla `producto_etiqueta` | Creada en V4 |
| Asociar etiquetas | `POST /api/productos/{id}/etiquetas/{id}` |
| Reto 1 | `DELETE /api/productos/{id}/etiquetas/{id}` (solo quita el vínculo) |
| Reto 2 | `GET /api/productos/etiqueta/{etiquetaId}` |

---

## Cómo ejecutar

1. PostgreSQL con base `gestion_productos` (`application.properties`).
2. Arrancar Spring Boot. Flyway aplica V1 → V4 (V4 inserta las 5 etiquetas pedidas).

---

## Pruebas en Postman

Base URL: `http://localhost:8080`  
Header en POST/PUT: `Content-Type: application/json`

### CRUD productos

| Método | Endpoint | Operación |
|--------|----------|-----------|
| GET | `/api/productos` | Listar |
| GET | `/api/productos/{id}` | Buscar |
| POST | `/api/productos` | Crear |
| PUT | `/api/productos/{id}` | Actualizar |
| DELETE | `/api/productos/{id}` | Eliminar → **204 No Content** |

**POST** `http://localhost:8080/api/productos`

```json
{
    "codigo": "TEC-001",
    "nombre": "Teclado mecánico",
    "precioVenta": 75.50,
    "existencia": 20,
    "categoriaId": 2
}
```

Si no existe categoría 2, use `categoriaId: 1` o cree una categoría antes.

**PUT** `http://localhost:8080/api/productos/1`

```json
{
    "codigo": "TEC-001",
    "nombre": "Teclado mecánico RGB",
    "precioVenta": 89.99,
    "existencia": 15,
    "categoriaId": 1
}
```

**DELETE** `http://localhost:8080/api/productos/1` → 204

### Por categoría

`GET http://localhost:8080/api/productos/categoria/1`

### Etiquetas

**POST** `http://localhost:8080/api/etiquetas`

```json
{
    "nombre": "Oferta"
}
```

V4 ya inserta: Oferta, Importado, Empresarial, Portátil, Gaming.  
`GET /api/etiquetas` para ver los ids.

**Asociar:** `POST http://localhost:8080/api/productos/2/etiquetas/1`

**Reto 1 — quitar asociación:** `DELETE http://localhost:8080/api/productos/2/etiquetas/1` → 204. El producto y la etiqueta siguen existiendo.

**Reto 2 — productos por etiqueta:** `GET http://localhost:8080/api/productos/etiqueta/1`

### Proveedores y categorías (práctica anterior)

- `GET/POST/PUT/DELETE /api/proveedores`
- `GET/POST /api/categorias`
