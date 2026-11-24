# Recorrido para el responsable de pruebas

Usar una base de prueba y anotar fecha, resultado real, captura y observaciones.

| Caso | Acción | Resultado esperado |
|---|---|---|
| Inicio correcto | Acceder con cada cuenta del README | Panel con nombre y rol correspondiente |
| Inicio incorrecto | Usar contraseña errónea | Mensaje genérico, sin sesión autenticada |
| Sin sesión | Abrir /app/cursos directamente | Redirección al login |
| Protección de rol | Como estudiante abrir /app/profesor/curso | Error 403 personalizado |
| Crear curso | Como profesor guardar nombre y descripción | Aparece en listado y en MySQL |
| Actualizar | Editar el curso recién creado | Cambios visibles después de volver a entrar |
| Validar | Enviar nombre vacío o de más de 150 caracteres | Rechazo, sin registro inválido |
| Curso ajeno | Con un segundo profesor de prueba intentar editar/eliminar curso ajeno | Rechazo; registro original permanece |
| Inscribir | Como estudiante elegir un curso sin inscripción | Aparece en sus inscripciones y en las del profesor dueño |
| Duplicado | Inscribirse de nuevo en el mismo curso | Mensaje de duplicado; solo una fila en MySQL |
| Cancelar | Cancelar una inscripción propia | Desaparece de ambas vistas |
| Inscripción ajena | Intentar cancelar identificador de otro estudiante | Rechazo; no se elimina |
| Eliminar curso | Confirmar eliminación en formulario del curso | Curso y sus inscripciones desaparecen |
| Formulario vencido | Enviar formulario sin el token de sesión | 403; ningún cambio en MySQL |
| Inyección | Correo con comillas y expresiones SQL | No permite acceso |
| Texto HTML | Guardar etiquetas como descripción | Se muestran como texto, sin ejecutarse |
| Persistencia | Reiniciar aplicación y consultar | Datos guardados permanecen |
| Salir | Cerrar sesión y volver a una ruta privada | Solicita login; caché privada deshabilitada |
| Página inexistente | Abrir una ruta inventada | 404 personalizado |
| Error de conexión | Detener la base en entorno de prueba y consultar cursos | Error genérico; detalle solo en logs |
| Pantalla pequeña | Revisar en 375 px de ancho | Formularios y navegación utilizables; tabla desplazable |

La segunda cuenta de profesor y estudiante se crea solo en la base de prueba,
con contraseñas derivadas. PersistenciaTest genera y elimina esos datos automáticamente.
No editar el rol de una cuenta mientras tenga una sesión activa; cerrar sesión
antes de comprobar el nuevo rol. La administración de cuentas no forma parte del alcance.
