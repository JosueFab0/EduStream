<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header><a class="brand" href="${pageContext.request.contextPath}/app/inicio">EduStream<span>LMS</span></a>
<nav aria-label="Principal"><a href="${pageContext.request.contextPath}/app/inicio">Inicio</a><a href="${pageContext.request.contextPath}/app/cursos">Cursos</a><a href="${pageContext.request.contextPath}/app/inscripciones">Inscripciones</a></nav>
<div class="user"><span><c:out value="${sessionScope.usuarioLogueado.nombre}"/> · <c:out value="${sessionScope.usuarioLogueado.rol}"/></span>
<form action="${pageContext.request.contextPath}/app/salir" method="post"><input type="hidden" name="csrf" value="${sessionScope.csrf}"><button class="secondary">Salir</button></form></div></header>
