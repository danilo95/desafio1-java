<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title>
    <c:choose>
      <c:when test="${contratacion != null}">Editar</c:when>
      <c:otherwise>Nueva</c:otherwise>
    </c:choose> Contratación
  </title>

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
      </ul>
    </div>
  </div>
</nav>

<div class="container">
  <div class="d-flex align-items-center justify-content-between mb-3">
    <h1 class="h3 mb-0">
      <c:choose>
        <c:when test="${contratacion != null}">Editar Contratación</c:when>
        <c:otherwise>Nueva Contratación</c:otherwise>
      </c:choose>
    </h1>
    <a href="${pageContext.request.contextPath}/contrataciones" class="btn btn-outline-secondary">
      <i class="bi bi-arrow-left"></i> Volver
    </a>
  </div>

  <!-- Formulario -->
  <form action="contrataciones" method="post" class="row g-3">
    <c:if test="${contratacion != null}">
      <input type="hidden" name="idContratacion" value="${contratacion.idContratacion}"/>
    </c:if>

    <div class="col-md-6">
      <label for="idDepartamento" class="form-label">Departamento</label>
      <select name="idDepartamento" id="idDepartamento" class="form-select" required>
        <c:forEach var="d" items="${listaDepartamentos}">
          <option value="${d.idDepartamento}"
                  <c:if test="${contratacion != null && contratacion.idDepartamento == d.idDepartamento}">selected</c:if>>
              ${d.nombreDepartamento}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-6">
      <label for="idEmpleado" class="form-label">Empleado</label>
      <select name="idEmpleado" id="idEmpleado" class="form-select" required>
        <c:forEach var="e" items="${listaEmpleados}">
          <option value="${e.idEmpleado}"
                  <c:if test="${contratacion != null && contratacion.idEmpleado == e.idEmpleado}">selected</c:if>>
              ${e.nombrePersona}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-6">
      <label for="idCargo" class="form-label">Cargo</label>
      <select name="idCargo" id="idCargo" class="form-select" required>
        <c:forEach var="c" items="${listaCargos}">
          <option value="${c.idCargo}"
                  <c:if test="${contratacion != null && contratacion.idCargo == c.idCargo}">selected</c:if>>
              ${c.cargo} - ${c.descripcionCargo}
            <c:if test="${c.jefatura}">(Jefatura)</c:if>
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-6">
      <label for="idTipoContratacion" class="form-label">Tipo de Contratación</label>
      <select name="idTipoContratacion" id="idTipoContratacion" class="form-select" required>
        <c:forEach var="tc" items="${listaTipos}">
          <option value="${tc.idTipoContratacion}"
                  <c:if test="${contratacion != null && contratacion.idTipoContratacion == tc.idTipoContratacion}">selected</c:if>>
              ${tc.tipoContratacion}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-6">
      <label for="fechaContratacion" class="form-label">Fecha de Contratación</label>
      <input type="date" id="fechaContratacion" name="fechaContratacion"
             class="form-control"
             value="<c:out value='${contratacion != null ? contratacion.fechaContratacion : ""}'/>" required/>
    </div>

    <div class="col-md-6">
      <label for="salario" class="form-label">Salario</label>
      <input type="number" id="salario" name="salario" class="form-control"
             step="0.01"
             value="<c:out value='${contratacion != null ? contratacion.salario : ""}'/>" required/>
    </div>

    <div class="col-md-6">
      <label for="estado" class="form-label">Estado</label>
      <select name="estado" id="estado" class="form-select">
        <option value="1" <c:if test="${contratacion != null && contratacion.estado}">selected</c:if>>Activo</option>
        <option value="0" <c:if test="${contratacion != null && !contratacion.estado}">selected</c:if>>Inactivo</option>
      </select>
    </div>

    <div class="col-12">
      <button type="submit" class="btn btn-primary">
        <i class="bi bi-save"></i>
        <c:choose>
          <c:when test="${contratacion != null}">Guardar cambios</c:when>
          <c:otherwise>Crear</c:otherwise>
        </c:choose>
      </button>
    </div>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
