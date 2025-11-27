-- ----------------------------------------------------
-- SCRIPT MARIADB PARA CREAR BASE DE DATOS
-- ----------------------------------------------------

-- Crear la base de datos "usuarios_db" con codificación UTF-8
CREATE DATABASE IF NOT EXISTS ecommerce_bd
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_es_0900_ai_ci;

-- Creamos un usuario para esta base de datos (y le damos permisos)
CREATE USER 'usuarios_db'@'172.17.0.1' IDENTIFIED BY 'Abcd1234$';
GRANT INSERT, UPDATE, DELETE, CREATE ON ecommerce_bd.* TO 'usuarios_db'@'172.17.0.1';
FLUSH PRIVILEGES;

-- Usar la base de datos creada
USE ecommerce_bd;

-- Mostrar el estado de la base de datos creada
SHOW TABLE STATUS FROM ecommerce_bd;