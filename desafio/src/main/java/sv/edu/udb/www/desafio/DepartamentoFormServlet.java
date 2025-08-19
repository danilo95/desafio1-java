package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.Departamento;
import sv.edu.udb.www.desafio.model.DepartamentoDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DepartamentoFormServlet", urlPatterns = {"/departamentos/nuevo", "/departamentos/editar"})
public class DepartamentoFormServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    private DepartamentoDAO departamentoDAO;

    @Override
    public void init() throws ServletException {
        departamentoDAO = new DepartamentoDAO();
        departamentoDAO.setDataSource(ds);
    }

    // Mostrar formulario (nuevo o editar)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath(); // valido para /departamentos/nuevo o /departamentos/editar

        if ("/departamentos/editar".equals(servletPath)) {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/departamentos");
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                Departamento d = departamentoDAO.findById(id);
                if (d == null) {
                    resp.sendRedirect(req.getContextPath() + "/departamentos");
                    return;
                }
                req.setAttribute("departamento", d); // para poblar el form
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/departamentos");
                return;
            } catch (SQLException e) {
                throw new ServletException("Error cargando departamento", e);
            }
        }

        req.getRequestDispatcher("/departamentos-form.jsp").forward(req, resp);
    }

    // Guardar para crear o editar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id"); // presente en editar
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");

        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre es obligatorio.");
            // volver a llenarlo
            Departamento d = new Departamento();
            if (idStr != null && !idStr.trim().isEmpty()) {
                try { d.setIdDepartamento(Integer.parseInt(idStr)); } catch (NumberFormatException ignored) {}
                req.setAttribute("editMode", true);
            }
            d.setNombreDepartamento(nombre);
            d.setDescripcionDepartamento(descripcion);
            req.setAttribute("departamento", d);
            req.getRequestDispatcher("/departamentos-form.jsp").forward(req, resp);
            return;
        }

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                // Editar
                int id = Integer.parseInt(idStr);
                Departamento d = new Departamento();
                d.setIdDepartamento(id);
                d.setNombreDepartamento(nombre.trim());
                d.setDescripcionDepartamento(descripcion != null ? descripcion.trim() : null);
                departamentoDAO.update(d);
            } else {
                // Crear
                Departamento d = new Departamento();
                d.setNombreDepartamento(nombre.trim());
                d.setDescripcionDepartamento(descripcion != null ? descripcion.trim() : null);
                departamentoDAO.insert(d);
            }
        } catch (SQLException e) {
            throw new ServletException("Error guardando departamento", e);
        }

        resp.sendRedirect(req.getContextPath() + "/departamentos");
    }
}