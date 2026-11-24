-- Ejecutar con una cuenta administradora DESPUÉS de edustream_db.sql.
-- Sustituir la contraseña antes de ejecutar. No usar root en la aplicación.
CREATE USER 'edustream_app'@'localhost' IDENTIFIED BY 'CAMBIAR_ANTES_DE_EJECUTAR';
GRANT SELECT,INSERT,UPDATE,DELETE ON edustream_db.* TO 'edustream_app'@'localhost';
