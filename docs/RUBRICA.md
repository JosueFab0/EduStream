# Correspondencia con los dos PDF

El PDF de Programación 2 contiene la rúbrica y los entregables; el PDF de BD
contiene el esquema inicial y la guía JDBC. No se agregan módulos de un LMS completo.

| Criterio | Peso | Evidencia en el proyecto | Cómo comprobar |
|---|---:|---|---|
| Arquitectura MVC | 10% | model, dao y service separados de web; JSP bajo WEB-INF/views | Revisar que JSP no consulta MySQL y los DAO no dependen de HTTP |
| Dominio de POO | 10% | Usuario abstracto; Profesor y Estudiante heredan; ICursoOperaciones implementada por CursoService | ReglasTest y revisión de clases; el rol cambia el comportamiento |
| Persistencia y CRUD | 10% | Conexion, UsuarioDAO, CursoDAO e InscripcionDAO; cursos tienen crear, consultar, actualizar y eliminar | PersistenciaTest y recorrido del profesor |
| Manejo de errores | 5% | NegocioException, InscripcionDuplicadaException; error.jsp; errores SQL propagados al contenedor | Duplicado, validaciones, permisos, rutas inexistentes y MySQL detenido |
| Interfaz y navegación | 5% | Inicio, login, catálogo, formulario, inscripciones, estilos adaptables y etiquetas | Recorrer con ambos roles y en pantalla estrecha |

## Especificaciones particulares

| Requisito | Implementación |
|---|---|
| Usuarios abstractos y roles | model/Usuario.java, Profesor.java, Estudiante.java |
| Interfaces de comportamiento | service/ICursoOperaciones.java |
| Colecciones dinámicas | ArrayList en CursoDAO e InscripcionDAO; se construyen por consulta |
| Excepción de duplicados | Restricción UNIQUE más InscripcionDuplicadaException, incluso ante concurrencia |
| JDBC, driver com.mysql.cj.jdbc.Driver | config/Conexion.java y dependencia Maven mysql-connector-j |
| PreparedStatement y cierre de conexiones | Todos los DAO usan try-with-resources |
| Servlets controladores | web/LoginServlet, InicioServlet, CursoServlet, InscripcionServlet, LogoutServlet |
| Acciones estándar JSP | jsp:include en vistas y jsp:forward en index.jsp; JSTL para listas y escape |
| HttpSession | usuarioLogueado contiene Profesor o Estudiante; rotación del identificador al entrar |
| Filters para administración | SeguridadFilter protege /app/* y exige profesor en /app/profesor/* |
| Autorización por recurso | UPDATE/DELETE condicionados por id_profesor; cancelación por id_estudiante |
| Sin persistencia en variables estáticas | MySQL es la fuente de datos; listas y sesión no sustituyen las tablas |
| Estructura y datos de prueba | sql/edustream_db.sql |
| Proyecto comprimido y manual | EduStream.zip y README.txt |

## Cambios mínimos respecto al SQL del profesor

Se conservan las tres tablas, sus columnas, los dos roles, las cuentas y los dos
cursos. Se exige que las relaciones y la fecha no sean nulas y se agrega UNIQUE
en estudiante/curso. Las contraseñas de prueba se almacenan con PBKDF2, sin cambiar
la contraseña de acceso. Se agrega una inscripción de ejemplo. La integridad de
roles la controla el servicio; la clave foránea sola no comprueba el rol.

## Límites de la verificación

La matriz demuestra cobertura del código, no una calificación concedida por el
profesor. Consulta RESULTADOS.md para distinguir pruebas ejecutadas de revisiones
manuales pendientes. El equipo debe elaborar sus diagramas, evidencias y presentación.
