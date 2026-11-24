# EduStream LMS — guía para redactar el trabajo escrito

## 1. Introducción y alcance

EduStream es una aplicación web para administrar cursos e inscripciones. El profesor crea, edita y elimina sus propios cursos y consulta quién se inscribió en ellos. El estudiante consulta el catálogo, se inscribe y puede cancelar sus propias inscripciones. Ambos roles deben iniciar sesión. El alcance del proyecto es la gestión de cursos e inscripciones.

Para redactar esta sección, describan qué tareas resuelve la aplicación, quiénes la usan y qué información conserva después de apagarla. Usen un ejemplo del recorrido de la sección 5.

## 2. Cómo está organizado el sistema

| Parte | Función | Archivos que se pueden citar |
|---|---|---|
| Pantallas | Muestran formularios, cursos, inscripciones y mensajes | `WEB-INF/views/login.jsp`, `cursos.jsp`, `curso-form.jsp`, `inscripciones.jsp` |
| Controladores | Reciben las acciones del navegador, llaman a la lógica y eligen qué pantalla mostrar | `LoginServlet.java`, `CursoServlet.java`, `InscripcionServlet.java` |
| Reglas del sistema | Validan los datos y verifican el rol o propietario antes de guardar, modificar o eliminar | `CursoService.java`, `InscripcionService.java` |
| Acceso a datos | Ejecuta consultas con parámetros y convierte los resultados en objetos Java | `Conexion.java`, `UsuarioDAO.java`, `CursoDAO.java`, `InscripcionDAO.java` |
| Base de datos | Guarda usuarios, cursos e inscripciones | `sql/edustream_db.sql` |

Esta separación sigue el patrón modelo, vista y controlador. Para explicarlo en el trabajo, sigan una sola acción desde el botón hasta la base de datos y de vuelta a la pantalla. Los archivos Java están en `src/main/java/com/edustream/`, organizados por responsabilidad. Las pantallas están en `src/main/webapp/WEB-INF/views/`.

## 3. Clases, inicio de sesión y permisos

- **Herencia.** `Usuario` es una clase abstracta que reúne los datos comunes. `Profesor` y `Estudiante` heredan de ella y definen su rol y sus permisos para gestionar cursos.
- **Interfaz.** `ICursoOperaciones` define las operaciones de cursos. `CursoService` implementa esas operaciones y aplica las reglas de negocio.
- **Sesión.** `LoginServlet` pide a `UsuarioDAO` que verifique el correo y la contraseña. Cuando el acceso es correcto, guarda el usuario en la sesión. `SeguridadFilter` exige una sesión para entrar a las pantallas privadas y un rol de profesor para abrir las rutas de gestión. `LogoutServlet` cierra la sesión.
- **Permisos sobre los datos.** El profesor solo modifica o elimina cursos que le pertenecen. El estudiante solo cancela sus propias inscripciones. El sistema muestra un mensaje si algo falla, por ejemplo, al intentar repetir una inscripción.
- **Diagrama de clases.** Empiecen por `Usuario`, `Profesor`, `Estudiante`, `Curso` e `Inscripcion`. Agreguen la interfaz y el servicio de cursos para mostrar dónde se aplica el contrato de operaciones.

## 4. Cómo se relacionan las tablas

| Tabla | Qué guarda | Relación |
|---|---|---|
| `usuarios` | Identificador, nombre, correo, contraseña protegida y rol | Contiene las cuentas de profesores y estudiantes |
| `cursos` | Identificador, nombre, descripción y profesor | Cada curso tiene un profesor. Un profesor puede tener varios cursos |
| `inscripciones` | Identificador, estudiante, curso y fecha | Vincula a un estudiante con un curso. Cada lado puede aparecer en varias inscripciones |

Para el diagrama de la base, dibujen una relación de uno a muchos entre profesor y curso, entre estudiante e inscripción y entre curso e inscripción. Profesor y estudiante son roles de la misma tabla `usuarios`. Marquen la restricción que impide repetir la pareja estudiante y curso. Confirmen los nombres exactos de columnas y claves en `edustream_db.sql`.

## 5. Ejemplo que pueden explicar en el informe

1. El profesor entra, pulsa **Crear curso** y envía el nombre y la descripción. `CursoServlet` recibe el formulario. `CursoService` valida los datos y `CursoDAO` guarda el curso en MySQL con el identificador del profesor.
2. El estudiante entra y busca el curso en el catálogo. Al pulsar **Inscribirme**, `InscripcionServlet` recibe la solicitud. `InscripcionService` verifica el rol y la existencia del curso. `InscripcionDAO` guarda la inscripción. Si la pareja ya existe, el sistema informa al estudiante.
3. Después de reiniciar MySQL y Tomcat, las pantallas vuelven a consultar los datos guardados. En la prueba del 16 de noviembre de 2026 sobrevivieron el curso **Fundamentos de Java — Demostración** y la inscripción de **Juan Estudiante**. El curso de demostración se creó durante la prueba. Una instalación nueva puede repetir el recorrido con otro nombre.

## 6. Orden sugerido para el trabajo escrito

1. **Introducción.** Objetivo, usuarios y alcance (sección 1).
2. **Diseño.** Organización del sistema, clases, permisos y diagrama de base de datos (secciones 2 a 4).
3. **Funcionamiento.** Recorrido de profesor y estudiante con referencia a los archivos (sección 5).
4. **Pruebas y resultados.** Indiquen qué se hizo, qué se esperaba y qué se observó. En `Evidencias/`, la captura 01 muestra el curso creado, la 02 muestra la inscripción y las capturas 03 y 04 muestran los datos tras el reinicio. Para otros casos, consulten `EduStream/docs/CASOS_PRUEBA.md` y distingan las pruebas que ejecuten de las que queden pendientes.
5. **Conclusiones.** Expliquen qué objetivos se comprobaron y qué aprendió el equipo. Revisen los criterios con `EduStream/docs/RUBRICA.md`. El `README.txt` es la referencia para instalación.
