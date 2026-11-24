EDUSTREAM LMS - INSTALACIÓN Y EJECUCIÓN
====================================
Proyecto web Maven para Apache NetBeans. Java SE en el modelo y los servicios;
Java EE 8 en Servlets, JSP, filtros y sesiones. Persistencia real en MySQL.

1. REQUISITOS
- JDK 11 o superior (verificado con JDK 21).
- Apache NetBeans con soporte Java Web / Java EE y Maven habilitado.
- Apache Tomcat 9.x. No elegir Tomcat 10/11: utilizan jakarta.servlet;
  este proyecto utiliza javax.servlet, como Java EE 8.
- MySQL Server 8.0 o superior. Workbench es opcional y NO sustituye al servidor.
- Internet en la primera compilación para descargar dependencias.

2. BASE DE DATOS
En MySQL Workbench, abre sql/edustream_db.sql y ejecuta el archivo completo
con una cuenta administradora. Actualiza el panel de esquemas.
El script crea edustream_db, tres tablas, dos usuarios, dos cursos y una inscripción.
No borra bases existentes. Si ya tienes tablas de una versión anterior, no lo
ejecutes encima: respalda los datos y usa una base nueva de prueba o una migración.
En una base nueva con otro nombre, cambia CREATE DATABASE y USE en una copia.

Abre sql/usuario_aplicacion.example.sql, sustituye la contraseña y ejecútalo.
La cuenta edustream_app tiene solo permisos de lectura y escritura de datos.
Configura estas variables de entorno ANTES de iniciar NetBeans o Tomcat:
 EDUSTREAM_DB_URL=jdbc:mysql://localhost:3306/edustream_db?connectionTimeZone=America/Costa_Rica
 EDUSTREAM_DB_USER=edustream_app
 EDUSTREAM_DB_PASSWORD=la contraseña que elegiste
En Windows: busca "Editar las variables de entorno de esta cuenta", crea las
tres variables y cierra y vuelve a abrir NetBeans. No guardes contraseñas en Java.
Para MySQL local configurado sin TLS, si aparece "Public Key Retrieval is not
allowed", añade &allowPublicKeyRetrieval=true&sslMode=DISABLED a la URL local.
Para una conexión remota configura TLS con certificados; no uses ese ajuste local.

3. ABRIR EN NETBEANS
- Archivo > Abrir proyecto: selecciona la carpeta EduStream (contiene pom.xml).
- Deja que Maven descargue las bibliotecas. El conector queda incluido en el WAR;
  no necesitas copiar archivos manualmente a lib ni añadirlos a Tomcat.
- Herramientas > Servidores > Agregar servidor > Apache Tomcat.
  Selecciona la carpeta de una instalación Tomcat 9 y las credenciales del gestor
  de esa instalación si NetBeans las solicita. No uses la cuenta de MySQL aquí.
- Propiedades del proyecto > Ejecutar: selecciona ese Tomcat y /edustream como
  ruta de contexto. Elige el JDK 11 o superior en las opciones de Java/Maven.
- Limpiar y construir; después Ejecutar proyecto.
- Abre http://localhost:8080/edustream/ (ajusta el puerto si tu servidor usa otro).
Si no aparece Tomcat/Java Web, habilita el soporte Java Web y EE en los
complementos de NetBeans. Los nombres de menú pueden variar según la versión.

ALTERNATIVA SIN EL IDE
Desde la carpeta del proyecto, con Maven instalado: mvn clean verify
Copia target/edustream.war a la carpeta webapps de Tomcat 9.
Inicia Tomcat con bin/startup.bat y las variables configuradas. Detén con shutdown.bat.
El archivo WAR precompilado incluido también se puede desplegar así.

4. CUENTAS DE DEMOSTRACIÓN
 Profesor: profe@edustream.com / 123456
 Estudiante: juan@correo.com / 123456
Se conservan las cuentas del PDF. En la tabla se guardan derivados PBKDF2,
no contraseñas en texto plano. La sesión guarda el objeto Usuario completo
(datos de identidad y rol, sin credenciales) bajo usuarioLogueado.
El script inicial es de demostración; cambia estas cuentas antes de otro uso.

5. FUNCIONES Y ESTRUCTURA
 Profesor: crear, listar, editar y eliminar sus cursos; consultar sus inscritos.
 Estudiante: consultar catálogo, inscribirse, ver y cancelar sus inscripciones.
 Ambos: iniciar sesión, panel de inicio, cerrar sesión.
 No se incluyen registro público, pagos, notas, videos ni gestión de usuarios:
 no aparecen como funciones obligatorias en los PDF. El CRUD completo es cursos.

 sql/                         estructura, datos y usuario de conexión
 src/main/java/com/edustream/
   model/                     Usuario abstracto, Profesor, Estudiante, Curso, Inscripcion
   config/                    Conexion JDBC, variables de entorno
   dao/                       consultas preparadas y cierre de recursos
   service/                   reglas, interfaz ICursoOperaciones y autorización
   exception/                 excepciones de negocio y duplicados
   security/                  protección de contraseñas
   web/                       controladores y filtro de acceso
 src/main/webapp/
   WEB-INF/views/             páginas JSP privadas y fragmentos compartidos
   WEB-INF/web.xml            rutas, filtro, errores y sesión de 30 minutos
   assets/                    estilos adaptables
 src/test/                    pruebas de reglas y persistencia
 docs/                        rúbrica, casos de prueba y resultados verificados

6. PRUEBAS
mvn clean verify ejecuta las cuatro pruebas de reglas. La prueba de persistencia
se omite de forma explícita si EDUSTREAM_INTEGRATION no es true.
Para verificar MySQL sin tocar tu base de trabajo:
- Crea edustream_test importando una COPIA del SQL con edustream_db sustituido
  por edustream_test. Otorga permisos de datos a la cuenta de prueba en esa base.
- En una terminal nueva, configura EDUSTREAM_DB_URL hacia edustream_test,
  EDUSTREAM_DB_USER y EDUSTREAM_DB_PASSWORD para la cuenta de prueba, y
  EDUSTREAM_INTEGRATION=true. Ejecuta mvn verify en esa terminal.
- La prueba exige un nombre de base terminado en _test, crea registros propios
  y los elimina al terminar. No ejecutes pruebas de escritura sobre datos reales.
Para la revisión desde el navegador usa docs/CASOS_PRUEBA.md y registra capturas.

7. ERRORES FRECUENTES
Connection refused: inicia MySQL Server y comprueba host y puerto.
Access denied: revisa usuario, contraseña, permisos y reinicia NetBeans/Tomcat
después de cambiar variables. La contraseña predeterminada de conexión está vacía.
Unknown database / Table doesn't exist: importa el SQL en la base indicada.
ClassNotFound javax.servlet: usa Tomcat 9, no 10/11.
Puerto ocupado: cambia el puerto HTTP en tu instalación Tomcat.
403 al enviar un formulario antiguo: recarga la página o vuelve a iniciar sesión.
500: consulta logs de Tomcat; el detalle técnico se registra allí, no en la vista.
Después de modificar Java, vuelve a construir y desplegar el WAR.

8. REPARTO DEL EQUIPO
Tú: todo el desarrollo, base de datos, interfaz e integración.
Integrante 2: documentación técnica y diagramas.
Integrante 3: pruebas, registro de fallos y capturas reales.
Integrante 4: manual de usuario, presentación y revisión de entrega.
Los archivos de docs son apoyo y trazabilidad del código; las evidencias y la
exposición deben completarlas los responsables con la versión instalada.
