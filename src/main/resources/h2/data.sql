
-- Inserts para usuario
INSERT INTO usuario (id, nombre, email, password, created_date, last_modified_date)
VALUES
--user1 y pass1 y user2 y pass2
(1, 'user1', 'user1@madrid.com', '73a9ffb13cf97007ac82e9a6da9de8c16dbe0285ae35e6c308e1c66c7e0d1b8d', NOW(), NOW()),
(2, 'user2', 'user2@madrid.com', '2cdff3b35ee0b7e73068c87d04d37dc3ac7505646eb47324385dc34d308bb857', NOW(), NOW());

-- Inserts para punto_de_interes
INSERT INTO punto_de_interes (id, nombre, descripcion, latitud, longitud, categoria, created_date, last_modified_date)
VALUES
(1, 'Parque del Retiro', 'Un parque icónico en el centro de Madrid', 40.415363, -3.684449, 'Parque', NOW(), NOW()),
(2, 'Museo del Prado', 'Uno de los museos de arte más importantes del mundo', 40.413781, -3.692127, 'Museo', NOW(), NOW()),
(3, 'Puerta del Sol', 'Una de las plazas más famosas de Madrid, centro de España', 40.416775, -3.703790, 'Plaza', NOW(), NOW());

-- Inserts para ruta
INSERT INTO ruta (id, nombre, descripcion, duracion, distancia, created_date, last_modified_date)
VALUES
(1, 'Tour Histórico Madrid', 'Una ruta que recorre los principales puntos históricos de Madrid', 2.5, 5.0, NOW(), NOW()),
(2, 'Ruta Cultural Madrid', 'Un paseo por los principales museos y centros culturales de la ciudad', 3.0, 4.2, NOW(), NOW());

-- Inserts para archivo
INSERT INTO archivo (id, nombre, ruta, punto_interes_id, created_date, last_modified_date)
VALUES
(1, 'Mapa del Retiro', '/archivos/mapa_retiro.pdf', 1, NOW(), NOW()),
(2, 'Guía del Prado', '/archivos/guia_prado.pdf', 2, NOW(), NOW());

-- Inserts para ruta_punto_de_interes
INSERT INTO ruta_punto_de_interes (ruta_id, punto_de_interes_id, created_date, last_modified_date)
VALUES
(1, 1, NOW(), NOW()), -- Ruta 1 incluye Parque del Retiro
(1, 3, NOW(), NOW()), -- Ruta 1 incluye Puerta del Sol
(2, 2, NOW(), NOW()), -- Ruta 2 incluye Museo del Prado
(2, 1, NOW(), NOW()); -- Ruta 2 incluye Parque del Retiro

-- Inserts para usuario_ruta_favorita
INSERT INTO usuario_ruta_favorita (usuario_id, ruta_id, created_date, last_modified_date)
VALUES
(1, 1, NOW(), NOW()), -- user1 tiene como favorita la Ruta 1
(2, 2, NOW(), NOW()); -- user2 tiene como favorita la Ruta 2
