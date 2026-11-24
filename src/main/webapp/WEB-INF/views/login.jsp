<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>EduStream LMS</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head><body><main><section class="login"><p class="eyebrow">APRENDER. CONECTAR. AVANZAR.</p><h1>Bienvenido a EduStream</h1><p>Un espacio para tus cursos y tu aprendizaje.</p>
<jsp:include page="fragments/flash.jsp"/>
<form action="${pageContext.request.contextPath}/login" method="post">
<input type="hidden" name="csrf" value="${sessionScope.csrf}">
<label for="email">Correo electrónico</label><input id="email" name="email" type="email" autocomplete="username" maxlength="100" required>
<label for="password">Contraseña</label><input id="password" name="password" type="password" autocomplete="current-password" maxlength="128" required>
<button>Iniciar sesión</button></form><p class="muted">Accede con la cuenta de estudiante o profesor asignada.</p></section></main><footer>EduStream · Proyecto final de Programación 2</footer></body></html>
