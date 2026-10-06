CREATE TABLE proveedor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(30),
    correo VARCHAR(150),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE producto
ADD COLUMN proveedor_id INTEGER;

ALTER TABLE producto
ADD CONSTRAINT fk_producto_proveedor
    FOREIGN KEY (proveedor_id)
    REFERENCES proveedor(id);

INSERT INTO proveedor (nombre, telefono, correo, activo) VALUES
('Distribuidora Central', '2222-1111', 'central@proveedores.com', TRUE),
('Importadora del Pacifico', '8888-2222', 'pacifico@proveedores.com', TRUE);

INSERT INTO categoria (nombre, activa)
SELECT 'Abarrotes', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categoria);

INSERT INTO producto (codigo, nombre, descripcion, precio_venta, existencia, categoria_id, proveedor_id)
SELECT 'P-001', 'Arroz 1kg', 'Arroz blanco de grano largo', 25.00, 100, c.id, 1
FROM categoria c
ORDER BY c.id
LIMIT 1;

INSERT INTO producto (codigo, nombre, descripcion, precio_venta, existencia, categoria_id, proveedor_id)
SELECT 'P-002', 'Agua 600ml', 'Agua embotellada', 15.00, 200, c.id, 2
FROM categoria c
ORDER BY c.id
LIMIT 1;
