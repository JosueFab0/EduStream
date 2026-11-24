# Verificación ejecutada

Fecha: 9 de noviembre de 2026, zona America/Costa_Rica.

## Entorno

- Windows, JDK 21.0.12; código compilado para Java 11.
- Maven 3.9.9.
- MySQL Community Server 8.4.13, instancia temporal en 127.0.0.1:33316.
- Base independiente edustream_test, importada desde el SQL entregado cambiando
  únicamente el nombre de base.
- Apache Tomcat 9.0.123, instancia temporal en el puerto 18080.

No se modificó una base de trabajo existente ni se instaló un servicio permanente.
Las instancias de prueba se detuvieron al terminar. Las credenciales y puertos de
prueba no están incorporados en el código ni reemplazan la configuración del README.

## Resultados

| Verificación | Resultado |
|---|---|
| Compilación y empaquetado WAR | Correctos |
| Pruebas de reglas | 4 aprobadas |
| Prueba de integración MySQL | 1 aprobada, sin omisiones en la ejecución integrada |
| Recorrido automatizado HTTP con sesiones independientes | 24 comprobaciones aprobadas |
| Error con MySQL detenido | 500 personalizado, sin detalles internos en la respuesta |
| Revisión visual | Panel de profesor en escritorio y catálogo a 375 px |
| Limpieza de registros de pruebas | Permanecen los 2 usuarios, 2 cursos y 1 inscripción iniciales |

La prueba integrada cubrió autenticación, intento de inyección en el correo,
dos profesores y un estudiante, rechazo de edición/eliminación de curso ajeno,
creación/lectura/actualización/eliminación, inscripción duplicada, separación de
listados por propietario, rechazo de cancelación ajena, cancelación propia y
eliminación en cascada de inscripciones.

Las comprobaciones HTTP ejecutaron los Servlets y compilaron las vistas JSP reales
en Tomcat, con consultas a MySQL. Se comprobaron acceso, validaciones, escape de
HTML, filtros de rol, formularios protegidos, curso inexistente o identificador
inválido, inscripciones y cierre de sesión. El detalle de las 24 comprobaciones
está en comprobaciones-web.json. El caso de identificador inválido se ejecutó;
el caso manual de curso eliminado simultáneamente queda como prueba adicional.

Evidencias: verificacion-maven.log, verificacion-error.txt y capturas/.

## Pendiente en la instalación del usuario

- Abrir y ejecutar el proyecto desde su versión concreta de NetBeans. Se verificó
  la compilación Maven y el despliegue en Tomcat; no se automatizó el IDE.
- Configurar MySQL y Tomcat permanentes siguiendo README.txt.
- Completar el recorrido manual de CASOS_PRUEBA.md, incluida accesibilidad por
  teclado, expiración de sesión, reinicio y los navegadores que usará el equipo.
- Elaborar diagramas, manual de usuario, evidencias propias y presentación.

La cobertura de la rúbrica está documentada en RUBRICA.md. La nota final depende
de la evaluación del profesor y de la defensa del equipo.
