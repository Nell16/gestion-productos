# Gestión de productos

API REST con Spring Boot, Spring Data JPA, Hibernate y Flyway sobre PostgreSQL.

Capas: **Controller → Service → Repository → Entity**, más **DTO** de entrada para productos.

---

## Estructura del proyecto

```
Controllers/   ProductoController, CategoriaController, ProveedorController, EtiquetaController
Services/      ProductoService
Repositories/  ProductoRepository, CategoriaRepository, ProveedorRepository, EtiquetaRepository
Entity/        Producto, Categoria, Proveedor, Etiqueta
DTO/           ProductoRequestDTO
db/migration/  V1 … V4
```

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
