<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card mb-4">
    <div class="card-header">
        <c:choose>
            <c:when test="${accion == 'editar'}">Editar Cargo</c:when>
            <c:otherwise>Nuevo Cargo</c:otherwise>
        </c:choose>
    </div>
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/cargo">
            <c:if test="${accion == 'editar'}">
                <input type="hidden" name="id" value="${cargo.idCargo}" />
            </c:if>
            <div class="mb-3">
                <label class="form-label">Nombre Cargo</label>
                <input type="text" name="cargo" class="form-control"
                       value="${cargo.cargo}" required />
            </div>
            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcionCargo" class="form-control">${cargo.descripcionCargo}</textarea>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" name="jefatura" class="form-check-input" id="jefaturaCheck"
                       <c:if test="${cargo.jefatura}">checked</c:if> />
                <label for="jefaturaCheck" class="form-check-label">¿Es jefatura?</label>
            </div>
            <button type="submit" class="btn btn-success">Guardar</button>
            <a href="${pageContext.request.contextPath}/cargo" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</div>
