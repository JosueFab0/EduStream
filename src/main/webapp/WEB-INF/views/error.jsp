<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>EduStream LMS</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head><body><main><section class="login"><p class="eyebrow">EDUSTREAM</p><h1>No pudimos completar la solicitud</h1>
<p>Código: <c:out value="${requestScope['javax.servlet.error.status_code']}"/></p>
<c:choose><c:when test="${requestScope['javax.servlet.error.status_code'] == 403}"><p>No tienes permiso para esta acción o el formulario ha vencido. Vuelve a cargar la página.</p></c:when><c:when test="${requestScope['javax.servlet.error.status_code'] == 404}"><p>La página solicitada no existe.</p></c:when><c:otherwise><p>Vuelve a intentarlo. Si el problema continúa, informa al responsable del sistema.</p></c:otherwise></c:choose>
<a class="button" href="${pageContext.request.contextPath}/login">Volver al inicio</a></section></main><footer>EduStream · Proyecto final de Programación 2</footer></body></html>
