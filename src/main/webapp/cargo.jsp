<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Cargos</title>

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

<!-- Menú -->
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
    <!-- Incluir formulario solo si se está creando o editando -->
    <c:if test="${accion == 'nuevo' || accion == 'editar'}">
        <jsp:include page="nuevoCargo.jsp" />
    </c:if>

    <div class="d-flex align-items-center justify-content-between mb-3">
        <h1 class="h3 mb-3">Cargos</h1>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/cargo?action=nuevo">
            <i class="bi bi-plus-circle"></i> Nuevo Cargo
        </a>
    </div>

    <c:choose>
        <c:when test="${empty cargos}">
            <div class="alert alert-info">No hay cargos registrados.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Jefatura</th>
                        <th class="text-center" style="width: 160px;">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="c" items="${cargos}">
                        <tr>
                            <td>${c.idCargo}</td>
                            <td>${c.cargo}</td>
                            <td>${c.descripcionCargo}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${c.jefatura}">Sí</c:when>
                                    <c:otherwise>No</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center table-actions">
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/cargo?action=editar&id=${c.idCargo}"
                                   title="Editar">
                                    <i class="bi bi-pencil-square"></i>
                                </a>
                                <a class="btn btn-sm btn-outline-danger"
                                   href="${pageContext.request.contextPath}/cargo?action=eliminar&id=${c.idCargo}"
                                   onclick="return confirm('¿Seguro que quieres eliminar este cargo?');"
                                   title="Eliminar">
                                    <i class="bi bi-trash3"></i>
                                </a>
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
