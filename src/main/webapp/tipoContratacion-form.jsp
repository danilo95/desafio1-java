<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15/8/2025
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title><c:choose><c:when test="${not empty tipo}">Editar</c:when><c:otherwise>Nuevo</c:otherwise></c:choose> Tipo de Contratación</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
  <style> body { padding-top: 4.5rem; } </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container">
    <a class="navbar-brand" href="#">Empresa</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div id="mainNav" class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/empleados">Empleados</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/departamentos">Departamentos</a></li>
        <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/contrataciones">Contrataciones</a></li>
        <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/tipos-contratacion">Tipo contratacion</a></li>
        <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/cargo">Cargos</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container">
  <div class="d-flex align-items-center justify-content-between mb-3">
    <h1 class="h3 mb-0">
      <c:choose>
        <c:when test="${not empty tipo}">Editar Tipo de Contratación</c:when>
        <c:otherwise>Nuevo Tipo de Contratación</c:otherwise>
      </c:choose>
    </h1>
    <a href="${pageContext.request.contextPath}/tipos-contratacion" class="btn btn-outline-secondary">
      <i class="bi bi-arrow-left"></i> Volver
    </a>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}<c:choose><c:when test='${not empty tipo}'>/tipos-contratacion/editar</c:when><c:otherwise>/tipos-contratacion/nuevo</c:otherwise></c:choose>">
    <c:if test="${not empty tipo}">
      <input type="hidden" name="id" value="${tipo.idTipoContratacion}" />
    </c:if>

    <div class="mb-3">
      <label class="form-label">Tipo de Contratación <span class="text-danger">*</span></label>
      <input type="text" name="nombre" class="form-control"
             value="<c:out value='${tipo.tipoContratacion}'/>"
             required maxlength="50" />
      <div class="form-text">Máximo 50 caracteres.</div>
    </div>

    <button type="submit" class="btn btn-primary">
      <i class="bi bi-save"></i>
      <c:choose>
        <c:when test="${not empty tipo}">Guardar cambios</c:when>
        <c:otherwise>Crear</c:otherwise>
      </c:choose>
    </button>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
