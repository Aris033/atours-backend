-- 1. Crear las tablas primero
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ruta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    duracion DOUBLE PRECISION NOT NULL,
    distancia DOUBLE PRECISION,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS punto_de_interes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    latitud NUMERIC(9, 6) NOT NULL,
    longitud NUMERIC(9, 6) NOT NULL,
    categoria VARCHAR(255),
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS archivo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    ruta VARCHAR(255) NOT NULL,
    punto_interes_id BIGINT,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    CONSTRAINT fk_archivo_punto_interes FOREIGN KEY (punto_interes_id)
        REFERENCES punto_de_interes (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ruta_punto_de_interes (
    ruta_id BIGINT NOT NULL,
    punto_de_interes_id BIGINT NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (ruta_id, punto_de_interes_id),
    CONSTRAINT fk_ruta_punto_interes_ruta FOREIGN KEY (ruta_id)
        REFERENCES ruta (id) ON DELETE CASCADE,
    CONSTRAINT fk_ruta_punto_interes_punto FOREIGN KEY (punto_de_interes_id)
        REFERENCES punto_de_interes (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario_ruta_favorita (
    usuario_id BIGINT NOT NULL,
    ruta_id BIGINT NOT NULL,
    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    PRIMARY KEY (usuario_id, ruta_id),
    CONSTRAINT fk_usuario_ruta_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_ruta_ruta FOREIGN KEY (ruta_id)
        REFERENCES ruta (id) ON DELETE CASCADE
);

-- 2. Limpiar las tablas (si existen registros)
DELETE FROM usuario_ruta_favorita;
DELETE FROM ruta_punto_de_interes;
DELETE FROM archivo;
DELETE FROM punto_de_interes;
DELETE FROM ruta;
DELETE FROM usuario;

-- 3. Reiniciar secuencias en H2
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 1;
ALTER TABLE ruta ALTER COLUMN id RESTART WITH 1;
ALTER TABLE punto_de_interes ALTER COLUMN id RESTART WITH 1;
ALTER TABLE archivo ALTER COLUMN id RESTART WITH 1;
