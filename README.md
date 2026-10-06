# Gestión de productos

API REST con Spring Boot, Spring Data JPA, Hibernate y Flyway sobre PostgreSQL.

## Preguntas

### ¿Cuál es la función de Spring Data JPA?

Reduce el código de persistencia: defines un repositorio (por ejemplo `JpaRepository`) y Spring genera las operaciones CRUD y consultas derivadas de los nombres de método, usando JPA por debajo.

### ¿Qué función cumple Hibernate?

Es el motor ORM que traduce entidades Java a SQL: mapea clases a tablas, gestiona el ciclo de vida de los objetos y ejecuta las consultas contra la base de datos.

### ¿Qué diferencia existe entre JPA y Hibernate?

**JPA** es la especificación (anotaciones e interfaces estándar: `@Entity`, `EntityManager`, etc.). **Hibernate** es una implementación de esa especificación. El código se escribe contra JPA; Hibernate es quien realmente persiste los datos.

### ¿Qué función cumple `@Entity`?

Marca una clase como entidad JPA: Hibernate la trata como una tabla (o se mapea a una con `@Table`) y cada instancia corresponde a una fila.

### ¿Qué función cumple `@ManyToOne`?

Define una relación muchos-a-uno. En este proyecto, varios productos pertenecen a una categoría y a un proveedor.

### ¿Qué función cumple `@JoinColumn`?

Indica la columna de clave foránea en la tabla dueña de la relación (`categoria_id`, `proveedor_id` en `producto`).

### ¿Qué función cumple `JpaRepository`?

Interfaz de Spring Data JPA que aporta `save`, `findAll`, `findById`, `deleteById`, etc. Los repositorios del proyecto (`ProductoRepository`, `ProveedorRepository`, `CategoriaRepository`) la extienden.

### ¿Por qué se utilizan migraciones?

Para versionar el esquema: cada cambio de tablas o datos se aplica de forma ordenada y repetible. Flyway evita que Hibernate cree o altere tablas a ciegas (`ddl-auto=validate`).

### ¿Qué diferencia existe entre V1, V2 y V3?

| Migración | Qué hace |
|-----------|----------|
| **V1** | Crea las tablas iniciales `categoria` y `producto`. |
| **V2** | Agrega la columna `descripcion` a `producto`. |
| **V3** | Crea `proveedor`, la FK `producto.proveedor_id`, inserta **dos proveedores** y **productos asociados**. |

Flyway aplica V1 → V2 → V3 en ese orden y no vuelve a ejecutar un script ya aplicado.

### ¿Qué problema puede producir una relación bidireccional al generar JSON?

Si `Producto` apunta a `Proveedor` y `Proveedor` lista sus `productos`, Jackson entra en un ciclo infinito al serializar. Se evita con `@JsonIgnore` en un lado (aquí, en `Proveedor.productos`) o con `@JsonManagedReference` / `@JsonBackReference`.

---

## Requisitos de la práctica

| Requisito | Estado |
|-----------|--------|
| Migración V3 | `src/main/resources/db/migration/V3__crear_proveedor.sql` |
| Entidad Proveedor | `Entity/Proveedor.java` |
| Relación con Producto | `@ManyToOne` + `@JoinColumn(name = "proveedor_id")` en `Producto` |
| ProveedorRepository | `Repositories/ProveedorRepository.java` |
| ProveedorController | `GET/POST/PUT/DELETE` en `/api/proveedores` |
| Dos proveedores | Insertados en V3 |
| Productos asociados | `P-001` → proveedor 1, `P-002` → proveedor 2 |
| Pruebas en Postman | Colección de peticiones abajo |

---

## Pruebas en Postman

Base URL: `http://localhost:8080`

### 1. Listar proveedores (deben verse los dos de V3)

`GET http://localhost:8080/api/proveedores`

### 2. Crear un proveedor

`POST http://localhost:8080/api/proveedores`  
Header: `Content-Type: application/json`

```json
{
  "nombre": "Suministros del Norte",
  "telefono": "7777-3333",
  "correo": "norte@proveedores.com",
  "activo": true
}
```

### 3. Obtener un proveedor por id

`GET http://localhost:8080/api/proveedores/1`

### 4. Actualizar un proveedor

`PUT http://localhost:8080/api/proveedores/1`

```json
{
  "nombre": "Distribuidora Central Actualizada",
  "telefono": "2222-1111",
  "correo": "central@proveedores.com",
  "activo": true
}
```

### 5. Listar productos (deben traer categoría y proveedor)

`GET http://localhost:8080/api/productos`

### 6. Crear un producto asociado a un proveedor

`POST http://localhost:8080/api/productos`

```json
{
  "codigo": "P-003",
  "nombre": "Aceite 1L",
  "descripcion": "Aceite vegetal",
  "precioVenta": 45.50,
  "existencia": 50,
  "categoria": { "id": 1 },
  "proveedor": { "id": 1 }
}
```

### 7. Crear categoría (si hace falta)

`POST http://localhost:8080/api/categorias`

```json
{
  "nombre": "Limpieza",
  "activa": true
}
```

### 8. Eliminar un proveedor (solo si no tiene productos, o fallará la FK)

`DELETE http://localhost:8080/api/proveedores/3`
