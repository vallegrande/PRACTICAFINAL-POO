-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS sistema_fertilizantes;
USE sistema_fertilizantes;

-- Tabla de categorías
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tipo_fertilizante ENUM('Organico', 'Quimico', 'Mineral', 'Liquido') NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria_id INT,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol ENUM('Administrador', 'Vendedor', 'Almacen') DEFAULT 'Vendedor',
    telefono VARCHAR(20),
    activo BOOLEAN DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insertar datos de ejemplo en categorías
INSERT INTO categorias (nombre, descripcion, tipo_fertilizante) VALUES
('Urea', 'Fertilizante nitrogenado de rápida absorción', 'Quimico'),
('Superfosfato', 'Fertilizante fosforado para raíces', 'Mineral'),
('Sulfato de Amonio', 'Fuente de nitrógeno y azufre', 'Quimico'),
('Compost Orgánico', 'Fertilizante natural de lenta liberación', 'Organico'),
('Fertilizante Foliar', 'Aplicación directa en hojas', 'Liquido'),
('Humus de Lombriz', 'Abono orgánico de alta calidad', 'Organico'),
('NPK 15-15-15', 'Fertilizante balanceado', 'Quimico'),
('Cal Agrícola', 'Corrector de pH del suelo', 'Mineral');

-- Insertar datos de ejemplo en productos
INSERT INTO productos (nombre, precio, stock, categoria_id, descripcion) VALUES
('Urea Granular', 25.50, 100, 1, 'Urea de alta pureza en presentación granular'),
('Superfosfato Triple', 18.75, 80, 2, 'Fertilizante fosforado concentrado'),
('Sulfato de Amonio Premium', 22.30, 60, 3, 'Fuente dual de nitrógeno y azufre'),
('Compost de Estiércol', 15.00, 120, 4, 'Compost orgánico de estiércol bovino'),
('Fertilizante Foliar Max', 35.20, 40, 5, 'Fertilizante líquido para aplicación foliar'),
('Humus de Lombriz Roja', 28.90, 90, 6, 'Humus de alta calidad de lombriz roja californiana'),
('NPK Balanceado 20-10-10', 30.45, 70, 7, 'Fórmula balanceada para crecimiento vegetativo'),
('Cal Dolomítica', 12.80, 110, 8, 'Correcto de suelo con magnesio'),
('Urea Soluble', 27.60, 50, 1, 'Urea de rápida disolución para riego'),
('Superfosfato Simple', 16.90, 85, 2, 'Fertilizante fosforado de liberación media');

-- Insertar usuarios por defecto
INSERT INTO usuarios (nombre, email, password, rol, telefono, activo) VALUES
('Administrador Principal', 'admin@fertilizantes.com', 'admin123', 'Administrador', '987654321', true),
('Juan Pérez Vendedor', 'vendedor@fertilizantes.com', 'vendedor123', 'Vendedor', '987654322', true),
('María García Almacén', 'almacen@fertilizantes.com', 'almacen123', 'Almacen', '987654323', true),
('Carlos López', 'carlos@fertilizantes.com', 'carlos123', 'Vendedor', '987654324', true),
('Ana Martínez', 'ana@fertilizantes.com', 'ana123', 'Almacen', '987654325', true);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_categorias_nombre ON categorias(nombre);
CREATE INDEX idx_productos_nombre ON productos(nombre);
CREATE INDEX idx_productos_precio ON productos(precio);
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_activo ON usuarios(activo);

-- Crear vistas útiles
CREATE VIEW vista_productos_completa AS
SELECT
    p.id,
    p.nombre as producto_nombre,
    p.precio,
    p.stock,
    p.descripcion as producto_descripcion,
    c.id as categoria_id,
    c.nombre as categoria_nombre,
    c.tipo_fertilizante,
    CASE
        WHEN p.stock > 0 THEN 'DISPONIBLE'
        ELSE 'AGOTADO'
    END as estado
FROM productos p
LEFT JOIN categorias c ON p.categoria_id = c.id;

CREATE VIEW vista_usuarios_activos AS
SELECT
    id,
    nombre,
    email,
    rol,
    telefono,
    fecha_creacion
FROM usuarios
WHERE activo = true;

-- Procedimientos almacenados útiles

-- Procedimiento para actualizar stock
DELIMITER //
CREATE PROCEDURE actualizar_stock_producto(
    IN producto_id INT,
    IN cantidad INT,
    IN operacion ENUM('INCREMENTAR', 'DECREMENTAR')
)
BEGIN
    IF operacion = 'INCREMENTAR' THEN
        UPDATE productos SET stock = stock + cantidad WHERE id = producto_id;
    ELSEIF operacion = 'DECREMENTAR' THEN
        UPDATE productos SET stock = stock - cantidad WHERE id = producto_id;
    END IF;
END //
DELIMITER ;

-- Procedimiento para buscar productos por criterio
DELIMITER //
CREATE PROCEDURE buscar_productos_avanzado(IN criterio VARCHAR(100))
BEGIN
    SELECT * FROM vista_productos_completa
    WHERE producto_nombre LIKE CONCAT('%', criterio, '%')
       OR categoria_nombre LIKE CONCAT('%', criterio, '%')
       OR tipo_fertilizante LIKE CONCAT('%', criterio, '%')
       OR CAST(precio AS CHAR) LIKE CONCAT('%', criterio, '%');
END //
DELIMITER ;

-- Triggers para auditoría

-- Trigger para registrar cambios en productos
CREATE TABLE auditoria_productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    accion ENUM('INSERT', 'UPDATE', 'DELETE'),
    precio_anterior DECIMAL(10,2),
    precio_nuevo DECIMAL(10,2),
    stock_anterior INT,
    stock_nuevo INT,
    usuario VARCHAR(100),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER after_producto_update
    AFTER UPDATE ON productos
    FOR EACH ROW
BEGIN
    INSERT INTO auditoria_productos (producto_id, accion, precio_anterior, precio_nuevo, stock_anterior, stock_nuevo, usuario)
    VALUES (NEW.id, 'UPDATE', OLD.precio, NEW.precio, OLD.stock, NEW.stock, USER());
END //
DELIMITER ;

-- Consultas de ejemplo para probar la base de datos

-- Consulta 1: Productos disponibles por categoría
SELECT
    c.nombre as categoria,
    COUNT(p.id) as total_productos,
    SUM(p.stock) as stock_total,
    AVG(p.precio) as precio_promedio
FROM categorias c
LEFT JOIN productos p ON c.id = p.categoria_id
GROUP BY c.id, c.nombre
ORDER BY total_productos DESC;

-- Consulta 2: Productos con stock bajo (menos de 20 unidades)
SELECT
    nombre,
    precio,
    stock,
    CASE
        WHEN stock < 10 THEN 'CRÍTICO'
        WHEN stock < 20 THEN 'BAJO'
        ELSE 'NORMAL'
    END as nivel_stock
FROM productos
WHERE stock < 20
ORDER BY stock ASC;

-- Consulta 3: Usuarios activos por rol
SELECT
    rol,
    COUNT(*) as total_usuarios
FROM usuarios
WHERE activo = true
GROUP BY rol
ORDER BY total_usuarios DESC;