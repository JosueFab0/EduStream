# EduStream LMS

Aplicación web para la gestión de cursos e inscripciones. Los profesores administran sus cursos y los estudiantes consultan el catálogo y gestionan sus inscripciones. La información se almacena en MySQL.

## Funcionalidades

| Rol | Operaciones |
| --- | --- |
| Profesor | Crear, consultar, editar y eliminar sus cursos; consultar estudiantes inscritos. |
| Estudiante | Consultar cursos, inscribirse y cancelar sus inscripciones. |
| Ambos | Iniciar sesión, acceder al panel de inicio y cerrar sesión. |

## Tecnologías

Java, Java EE 8, Servlets, JSP, JSTL, JDBC, MySQL, HTML y CSS. El proyecto utiliza Maven y JUnit 5 y se organiza con el patrón Modelo–Vista–Controlador, clases DAO y servicios de negocio.

## Requisitos

- JDK 11 o superior.
- MySQL Server 8.0 o superior.
- Apache Tomcat 9.
- Apache NetBeans con soporte Java Web y Maven, o Maven instalado para compilar desde una terminal.

El proyecto utiliza `javax.servlet` y se despliega en Tomcat 9.

## Instalación

### Base de datos

1. En una instalación nueva, ejecutar [sql/edustream_db.sql](sql/edustream_db.sql) desde MySQL Workbench o el cliente MySQL. Crea el esquema `edustream_db`, las tablas y los datos de demostración.
2. Editar la contraseña de [sql/usuario_aplicacion.example.sql](sql/usuario_aplicacion.example.sql) y ejecutarlo para crear la cuenta de conexión.
3. Si existe una base con datos, respaldarla y revisar la compatibilidad del esquema antes de aplicar cambios.

### Conexión

Definir estas variables de entorno antes de iniciar NetBeans o Tomcat:

| Variable | Valor |
| --- | --- |
| `EDUSTREAM_DB_URL` | `jdbc:mysql://localhost:3306/edustream_db?connectionTimeZone=America/Costa_Rica` |
| `EDUSTREAM_DB_USER` | `edustream_app` |
| `EDUSTREAM_DB_PASSWORD` | Contraseña configurada para la cuenta de conexión. |

En Windows, crear las variables desde **Editar las variables de entorno de esta cuenta** y volver a abrir NetBeans o reiniciar Tomcat.

### Ejecución en NetBeans

1. Seleccionar **Archivo > Abrir proyecto** y abrir la carpeta que contiene `pom.xml`.
2. Esperar a que Maven descargue las dependencias.
3. Registrar Tomcat 9 en **Herramientas > Servidores**.
4. En **Propiedades del proyecto > Ejecutar**, seleccionar Tomcat y configurar `/edustream` como ruta de contexto.
5. Seleccionar **Limpiar y construir** y después **Ejecutar**.
6. Abrir <http://localhost:8080/edustream/>. Ajustar el puerto si la instalación de Tomcat utiliza uno diferente.

### Compilación desde una terminal

```sh
mvn clean verify
```

Copiar `target/edustream.war` a la carpeta `webapps` de Tomcat 9 e iniciar el servidor. En Windows, utilizar `bin/startup.bat` con las variables de conexión configuradas.

## Cuentas de demostración

| Rol | Correo | Contraseña |
| --- | --- | --- |
| Profesor | `profe@edustream.com` | `123456` |
| Estudiante | `juan@correo.com` | `123456` |

Estas cuentas se crean con el archivo SQL inicial y están destinadas a demostración. Las contraseñas se almacenan mediante PBKDF2. Las credenciales de conexión a MySQL se obtienen de las variables de entorno.

## Estructura

```text
sql/                         Esquema, datos iniciales y permisos de conexión
src/main/java/com/edustream/
  config/                    Conexión JDBC
  dao/                       Acceso a datos
  exception/                 Excepciones de negocio
  model/                     Entidades y roles
  security/                  Protección de contraseñas
  service/                   Reglas de negocio
  web/                       Servlets y filtro de acceso
src/main/webapp/
  WEB-INF/views/             Vistas JSP
  WEB-INF/web.xml            Configuración de la aplicación
  assets/                    Estilos
src/test/                    Pruebas de reglas y persistencia
pom.xml                      Dependencias y compilación
```

## Pruebas

`mvn clean verify` ejecuta las pruebas de reglas. La prueba de persistencia se habilita con `EDUSTREAM_INTEGRATION=true` y requiere una base independiente cuyo nombre termine en `_test`.

Para ejecutarla, crear la base de prueba con una copia del archivo SQL, configurar las variables de conexión para esa base y ejecutar `mvn verify`. La prueba crea registros propios y los elimina al terminar.

## Resolución de problemas

| Situación | Comprobación |
| --- | --- |
| No se conecta a MySQL | Comprobar que MySQL Server esté iniciado y revisar host, puerto y variables de conexión. |
| Acceso denegado a MySQL | Revisar contraseña y permisos de la cuenta de conexión. |
| Base o tablas inexistentes | Ejecutar el archivo SQL en el esquema configurado. |
| Error relacionado con `javax.servlet` | Comprobar que el servidor sea Tomcat 9. |
| La dirección local no abre | Verificar que Tomcat esté iniciado, la aplicación desplegada y el puerto correcto. |
| Formulario rechazado con código 403 | Recargar la página o iniciar sesión nuevamente. |
| Error interno del servidor | Consultar el registro de Tomcat para identificar la causa. |
