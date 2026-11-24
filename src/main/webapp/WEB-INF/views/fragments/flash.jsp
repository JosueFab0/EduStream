<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.mensaje}"><p class="notice" role="status"><c:out value="${sessionScope.mensaje}"/></p><c:remove var="mensaje" scope="session"/></c:if>
<c:if test="${not empty error}"><p class="error" role="alert"><c:out value="${error}"/></p></c:if>
