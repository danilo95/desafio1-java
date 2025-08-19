<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 9/8/2025
  Time: 03:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title><c:choose><c:when test="${not empty departamento}">Editar</c:when><c:otherwise>Nuevo</c:otherwise></c:choose> Departamento</title>

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
        <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/departamentos">Departamentos</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contrataciones">Contrataciones</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container">
  <div class="d-flex align-items-center justify-content-between mb-3">
    <h1 class="h3 mb-0">
      <c:choose>
        <c:when test="${not empty departamento}">Editar Departamento</c:when>
        <c:otherwise>Nuevo Departamento</c:otherwise>
      </c:choose>
    </h1>
    <a href="${pageContext.request.contextPath}/departamentos" class="btn btn-outline-secondary">
      <i class="bi bi-arrow-left"></i> Volver
    </a>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}<c:choose><c:when test='${not empty departamento}'>/departamentos/editar</c:when><c:otherwise>/departamentos/nuevo</c:otherwise></c:choose>">
    <c:if test="${not empty departamento}">
      <input type="hidden" name="id" value="${departamento.idDepartamento}" />
    </c:if>

    <div class="mb-3">
      <label class="form-label">Nombre <span class="text-danger">*</span></label>
      <input type="text" name="nombre" class="form-control"
             value="<c:out value='${departamento.nombreDepartamento}'/>"
             required maxlength="50" />
      <div class="form-text">Máximo 50 caracteres.</div>
    </div>

    <div class="mb-3">
      <label class="form-label">Descripción</label>
      <textarea name="descripcion" class="form-control" rows="3" maxlength="65535"><c:out value='${departamento.descripcionDepartamento}'/></textarea>
    </div>

    <button type="submit" class="btn btn-primary">
      <i class="bi bi-save"></i>
      <c:choose>
        <c:when test="${not empty departamento}">Guardar cambios</c:when>
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