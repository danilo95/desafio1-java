<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Listado de Contrataciones</title>

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
        <h1 class="h3 mb-0">Listado de Contrataciones</h1>
        <a href="contrataciones?action=new" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> Nueva Contratación
        </a>
    </div>

    <table class="table table-striped table-bordered align-middle">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Departamento</th>
            <th>Empleado</th>
            <th>Cargo</th>
            <th>Tipo Contratación</th>
            <th>Fecha</th>
            <th>Salario</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${listaContrataciones}">
            <tr>
                <td>${c.idContratacion}</td>

                <td>
                    <c:choose>
                        <c:when test="${not empty c.nombreDepartamento}">
                            ${c.nombreDepartamento}
                        </c:when>
                        <c:when test="${not empty listaDepartamentos}">
                            <c:set var="depNombre" value="" />
                            <c:forEach var="d" items="${listaDepartamentos}">
                                <c:if test="${d.idDepartamento == c.idDepartamento}">
                                    <c:set var="depNombre" value="${d.nombreDepartamento}" />
                                </c:if>
                            </c:forEach>
                            <c:out value="${empty depNombre ? c.idDepartamento : depNombre}" />
                        </c:when>
                        <c:otherwise>
                            ${c.idDepartamento}
                        </c:otherwise>
                    </c:choose>
                </td>

                <!-- Empleado -->
                <td>
                    <c:choose>
                        <c:when test="${not empty c.nombreEmpleado}">
                            ${c.nombreEmpleado}
                        </c:when>
                        <c:when test="${not empty listaEmpleados}">
                            <c:set var="empNombre" value="" />
                            <c:forEach var="e" items="${listaEmpleados}">
                                <c:if test="${e.idEmpleado == c.idEmpleado}">
                                    <c:set var="empNombre" value="${e.nombrePersona}" />
                                </c:if>
                            </c:forEach>
                            <c:out value="${empty empNombre ? c.idEmpleado : empNombre}" />
                        </c:when>
                        <c:otherwise>
                            ${c.idEmpleado}
                        </c:otherwise>
                    </c:choose>
                </td>

                <!-- Cargo -->
                <td>
                    <c:choose>
                        <c:when test="${not empty c.nombreCargo}">
                            ${c.nombreCargo}
                        </c:when>
                        <c:when test="${not empty listaCargos}">
                            <c:set var="cargoNombre" value="" />
                            <c:forEach var="ca" items="${listaCargos}">
                                <c:if test="${ca.idCargo == c.idCargo}">
                                    <c:set var="cargoNombre" value="${ca.cargo}" />
                                </c:if>
                            </c:forEach>
                            <c:out value="${empty cargoNombre ? c.idCargo : cargoNombre}" />
                        </c:when>
                        <c:otherwise>
                            ${c.idCargo}
                        </c:otherwise>
                    </c:choose>
                </td>

                <!-- Tipo Contratación -->
                <td>
                    <c:choose>
                        <c:when test="${not empty c.nombreTipoContratacion}">
                            ${c.nombreTipoContratacion}
                        </c:when>
                        <c:when test="${not empty listaTipos}">
                            <c:set var="tipoNombre" value="" />
                            <c:forEach var="tc" items="${listaTipos}">
                                <c:if test="${tc.idTipoContratacion == c.idTipoContratacion}">
                                    <c:set var="tipoNombre" value="${tc.tipoContratacion}" />
                                </c:if>
                            </c:forEach>
                            <c:out value="${empty tipoNombre ? c.idTipoContratacion : tipoNombre}" />
                        </c:when>
                        <c:otherwise>
                            ${c.idTipoContratacion}
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>${c.fechaContratacion}</td>
                <td>$${c.salario}</td>
                <td>
                    <span class="badge
                        <c:choose>
                            <c:when test='${c.estado}'>bg-success</c:when>
                            <c:otherwise>bg-secondary</c:otherwise>
                        </c:choose>">
                        <c:choose>
                            <c:when test="${c.estado}">Activo</c:when>
                            <c:otherwise>Inactivo</c:otherwise>
                        </c:choose>
                    </span>
                </td>
                <td>
                    <a href="contrataciones?action=edit&id=${c.idContratacion}" class="btn btn-sm btn-warning">
                        <i class="bi bi-pencil"></i> Editar
                    </a>
                    <a href="contrataciones?action=delete&id=${c.idContratacion}" class="btn btn-sm btn-danger"
                       onclick="return confirm('¿Seguro que deseas eliminar esta contratación?')">
                        <i class="bi bi-trash"></i> Eliminar
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
