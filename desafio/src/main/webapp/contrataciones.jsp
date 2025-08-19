<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 9/8/2025
  Time: 03:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Contrataciones</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />

    <style>
        body { padding-top: 4.5rem; }
        .table-actions > * { margin-right: .25rem; }
        .table-actions form { display: inline-block; margin: 0; }
    </style>
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
    <h1 class="h3 mb-3">Contrataciones</h1>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/contrataciones/nueva">
            <i class="bi bi-plus-circle"></i> Nueva Contratacion
        </a>
    </div>

    <c:choose>
        <c:when test="${empty contrataciones}">
            <div class="alert alert-info">No hay contrataciones.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Departamento</th>
                        <th>Empleado</th>
                        <th>Cargo</th>
                        <th>Tipo</th>
                        <th>Fecha</th>
                        <th>Salario</th>
                        <th>Estado</th>
                        <th class="text-center" style="width: 160px;">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${contrataciones}" var="c">
                        <tr>
                            <td>${c.idContratacion}</td>
                            <td>${c.nombreDepartamento}</td>
                            <td>${c.nombreEmpleado}</td>
                            <td>${c.nombreCargo}</td>
                            <td>${c.nombreTipoContratacion}</td>
                            <td>${c.fechaContratacion}</td>
                            <td>${c.salario}</td>
                            <td>
                <span class="badge ${c.estado ? 'bg-success' : 'bg-secondary'}">
                        ${c.estado ? 'Activo' : 'Inactivo'}
                </span>
                            </td>
                            <td class="text-center table-actions">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/contrataciones/editar?id=${c.idContratacion}"
                                   title="Editar">
                                    <i class="bi bi-pencil-square"></i>
                                </a>
                                <form method="post" action="${pageContext.request.contextPath}/contrataciones"
                                      onsubmit="return confirm('¿Deseas eliminar esta contratación?');">
                                    <input type="hidden" name="accion" value="eliminar" />
                                    <input type="hidden" name="id" value="${c.idContratacion}" />
                                    <button type="submit" class="btn btn-sm btn-outline-danger" title="Eliminar">
                                        <i class="bi bi-trash3"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
