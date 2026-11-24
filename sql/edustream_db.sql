-- EduStream LMS. Ejecutar en MySQL 8.0 o superior.
-- No elimina tablas ni datos existentes. Si las tablas ya existen, se detiene:
-- no importar encima de la versión original sin migración/respaldo.
CREATE DATABASE IF NOT EXISTS edustream_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE edustream_db;
CREATE TABLE usuarios (
 id_usuario INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 rol ENUM('ESTUDIANTE','PROFESOR') NOT NULL,
 fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;
CREATE TABLE cursos (
 id_curso INT AUTO_INCREMENT PRIMARY KEY,
 nombre_curso VARCHAR(150) NOT NULL,
 descripcion TEXT,
 id_profesor INT NOT NULL,
 FOREIGN KEY (id_profesor) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
) ENGINE=InnoDB;
CREATE TABLE inscripciones (
 id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
 id_estudiante INT NOT NULL,
 id_curso INT NOT NULL,
 fecha_inscripcion DATE NOT NULL,
 CONSTRAINT uq_estudiante_curso UNIQUE(id_estudiante,id_curso),
 FOREIGN KEY (id_estudiante) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
 FOREIGN KEY (id_curso) REFERENCES cursos(id_curso) ON DELETE CASCADE
) ENGINE=InnoDB;
-- Mantiene nombres, columnas, relaciones y cuentas del script del profesor.
-- Diferencias: contraseñas derivadas, claves obligatorias y restricción de duplicados.
INSERT INTO usuarios(nombre,email,password,rol) VALUES
 ('Admin Profesor','profe@edustream.com','pbkdf2$210000$RWR1U3RyZWFtRGVtbzIwMjY=$EmzvWQI5CGb+AKL+p/Z0ltUUCT0BsPIIV1+5B/AeBg0=','PROFESOR'),
 ('Juan Estudiante','juan@correo.com','pbkdf2$210000$RWR1U3RyZWFtRGVtbzIwMjY=$EmzvWQI5CGb+AKL+p/Z0ltUUCT0BsPIIV1+5B/AeBg0=','ESTUDIANTE');
INSERT INTO cursos(nombre_curso,descripcion,id_profesor) VALUES
 ('Java SE Avanzado','Dominio de POO y colecciones.',1),
 ('Desarrollo Web con Java EE','Servlets, JSP y JDBC.',1);
-- Inscripción inicial para demostrar la relación y la vista del profesor.
INSERT INTO inscripciones(id_estudiante,id_curso,fecha_inscripcion) VALUES(2,1,CURRENT_DATE);
