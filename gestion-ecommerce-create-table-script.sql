SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS informacion_fiscal;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS compras;
DROP TABLE IF EXISTS articulos;
DROP TABLE IF EXISTS articulo_compra;

SET FOREIGN_KEY_CHECKS=1;

-- Información fiscal
CREATE TABLE IF NOT EXISTS informacion_fiscal(
	nif_cif VARCHAR(9) PRIMARY KEY NOT NULL UNIQUE,
	telefono VARCHAR(9) NOT NULL UNIQUE,
	direccion VARCHAR(150) NOT NULL
)
ENGINE=InnoDB;

-- Relacion 1:1 con información fiscal
CREATE TABLE IF NOT EXISTS clientes (
	nif_cif VARCHAR(9) PRIMARY KEY NOT NULL UNIQUE,
	nombre_completo VARCHAR(100) NOT NULL UNIQUE,
	email VARCHAR(150) NOT NULL UNIQUE,
	fecha_creacion DATETIME,
	FOREIGN KEY (nif_cif) REFERENCES informacion_fiscal(nif_cif)
	ON UPDATE CASCADE
	ON DELETE CASCADE
)
ENGINE=InnoDB;

-- Relacion N:M Clientes <-> Compras
CREATE TABLE IF NOT EXISTS compras (
	id INT PRIMARY KEY AUTO_INCREMENT,
	id_cliente VARCHAR(9) NOT NULL DEFAULT "",
	direccion VARCHAR(150),
	fecha_compra DATETIME NOT NULL,
	estado ENUM('Pendiente', 'Enviado', 'Entregado') NOT NULL,
	precio_total DECIMAL NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES clientes(nif_cif)
	ON DELETE SET DEFAULT
)
ENGINE=InnoDB;

-- Articulos
CREATE TABLE IF NOT EXISTS articulos (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(100) NOT NULL,
	descripcion VARCHAR(150),
	precio_actual DECIMAL NOT NULL,
	stock INT NOT NULL
)
ENGINE=InnoDB;

-- Articulo_Compra
-- Relacion N:M
CREATE TABLE IF NOT EXISTS articulo_compra (
	id_compra INT PRIMARY KEY NOT NULL,
	id_articulo INT PRIMARY KEY NOT NULL,
	unidades INT NOT NULL,
	precio_compra DECIMAL NOT NULL,
	FOREIGN KEY (id_compra) REFERENCES compras(id)
	ON DELETE RESTRICT,
	FOREIGN KEY (id_articulo) REFERENCES articulos(id)
	ON UPDATE CASCADE
	ON DELETE RESTRICT
)
ENGINE=InnoDB;