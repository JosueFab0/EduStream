# EduStream LMS

Proyecto académico de gestión de cursos e inscripciones. Aplicación web desarrollada localmente con Java, Servlets, JSP, JDBC y MySQL, organizada con el patrón Modelo–Vista–Controlador y clases DAO.

## Funciones

- Inicio y cierre de sesión, con roles de profesor y estudiante.
- Creación, consulta, edición y eliminación de cursos por su profesor.
- Inscripción y cancelación de cursos por estudiantes.
- Persistencia de cursos e inscripciones en MySQL.
- Validaciones, filtros de acceso y protección de contraseñas.

## Instalación

Requiere JDK 11 o superior, MySQL 8, Apache Tomcat 9 y Apache NetBeans con soporte Java Web. El proyecto utiliza `javax.servlet`.

1. Descarga o clona este repositorio.
2. Sigue [las instrucciones de instalación](README.txt) para crear la base y configurar las variables de entorno.
3. Abre la carpeta que contiene `pom.xml` en NetBeans, selecciona Tomcat 9 y ejecuta el proyecto.
4. Abre `http://localhost:8080/edustream/` cuando el servidor esté iniciado.

Las contraseñas de la base se configuran en el entorno de cada equipo. El archivo SQL de usuario de aplicación es una plantilla que debe ajustarse antes de ejecutarla.

## Organización

`src/main/java` contiene modelos, acceso a datos, servicios y controladores. `src/main/webapp` contiene las vistas y el diseño. `sql` contiene la base y la plantilla de permisos. `src/test` contiene las pruebas.

El desarrollo se realizó inicialmente en una máquina local. La documentación del equipo se incorporará cuando esté terminada.
