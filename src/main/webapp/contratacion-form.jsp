<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>
        <c:choose>
            <c:when test="${not empty contratacion}">Editar</c:when>
            <c:otherwise>Nuevo</c:otherwise>
        </c:choose> Contratación
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet"/>
    <style> body { padding-top: 4.5rem; } </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">Empresa</a>
        <div id="mainNav" class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
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
                <c:when test="${not empty contratacion}">Editar Contratación</c:when>
                <c:otherwise>Nueva Contratación</c:otherwise>
            </c:choose>
        </h1>
        <a href="${pageContext.request.contextPath}/contrataciones" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Volver
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:set var="formAction" value="${pageContext.request.contextPath}/contrataciones/nuevo"/>
    <c:if test="${not empty contratacion}">
        <c:set var="formAction" value="${pageContext.request.contextPath}/contrataciones/editar"/>
    </c:if>

    <form method="post" action="${formAction}">
        <c:if test="${not empty contratacion}">
            <input type="hidden" name="id" value="${contratacion.idContratacion}" />
        </c:if>

        <div class="mb-3">
            <label class="form-label">Departamento *</label>
            <select name="idDepartamento" class="form-select" required>
                <option value="">-- Seleccione --</option>
                <c:forEach var="d" items="${departamentos}">
                    <option value="${d.idDepartamento}" <c:if test="${contratacion != null && contratacion.idDepartamento == d.idDepartamento}">selected</c:if>>
                            ${d.nombreDepartamento}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Empleado *</label>
            <select name="idEmpleado" class="form-select" required>
                <option value="">-- Seleccione --</option>
                <c:forEach var="e" items="${empleados}">
                    <option value="${e.idEmpleado}" <c:if test="${contratacion != null && contratacion.idEmpleado == e.idEmpleado}">selected</c:if>>
                            ${e.nombrePersona}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Cargo *</label>
            <select name="idCargo" class="form-select" required>
                <option value="">-- Seleccione --</option>
                <c:forEach var="c" items="${cargos}">
                    <option value="${c.idCargo}" <c:if test="${contratacion != null && contratacion.idCargo == c.idCargo}">selected</c:if>>
                            ${c.cargo}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Tipo de Contratación *</label>
            <select name="idTipoContratacion" class="form-select" required>
                <option value="">-- Seleccione --</option>
                <c:forEach var="t" items="${tiposContratacion}">
                    <option value="${t.idTipoContratacion}" <c:if test="${contratacion != null && contratacion.idTipoContratacion == t.idTipoContratacion}">selected</c:if>>
                            ${t.tipoContratacion}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Fecha de Contratación *</label>
            <input type="date" name="fechaContratacion" class="form-control"
                   value="<c:out value='${contratacion.fechaContratacion}'/>" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Salario *</label>
            <input type="number" name="salario" class="form-control" step="0.01"
                   value="<c:out value='${contratacion.salario}'/>" required />
        </div>

        <div class="form-check mb-3">
            <input type="checkbox" name="estado" class="form-check-input" id="estadoCheck"
                   <c:if test="${contratacion != null && contratacion.estado}">checked</c:if> />
            <label class="form-check-label" for="estadoCheck">Activo</label>
        </div>

        <button type="submit" class="btn btn-primary">
            <i class="bi bi-save"></i>
            <c:choose>
                <c:when test="${not empty contratacion}">Guardar cambios</c:when>
                <c:otherwise>Crear</c:otherwise>
            </c:choose>
        </button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
