package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.Empleado;
import sv.edu.udb.www.desafio.model.EmpleadoDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "EmpleadosFormServlet", urlPatterns = {"/empleados/nuevo", "/empleados/editar"})
public class EmpleadosFormServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    private EmpleadoDAO empleadoDAO;

    @Override
    public void init() throws ServletException {
        empleadoDAO = new EmpleadoDAO();
        empleadoDAO.setDataSource(ds);
    }

    // Mostrar formulario (nuevo o editar)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath(); // valido para /empleado/nuevo o /empleado/editar

        if ("/empleados/editar".equals(servletPath)) {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/empleados");
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                Empleado e = empleadoDAO.findById(id);
                if (e == null) {
                    resp.sendRedirect(req.getContextPath() + "/empleados");
                    return;
                }
                req.setAttribute("empleados", e); // para poblar el form
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/empleados");
                return;
            } catch (SQLException e) {
                throw new ServletException("Error cargando empleados", e);
            }
        }

        req.getRequestDispatcher("/empleados-form.jsp").forward(req, resp);
    }

    // Guardar para crear o editar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id"); // presente en editar
        String dui = req.getParameter("dui");
        String nombre = req.getParameter("nombre");
        String usuario = req.getParameter("usuario");
        String telefono = req.getParameter("telefono");
        String correo = req.getParameter("correo");
        String fecha = req.getParameter("fecha");
        Date fechaSQL = Date.valueOf(fecha);

        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre es obligatorio.");
            // volver a llenarlo
            Empleado e = new Empleado();
            if (idStr != null && !idStr.trim().isEmpty()) {
                try { e.setIdEmpleado(Integer.parseInt(idStr)); } catch (NumberFormatException ignored) {}
                req.setAttribute("editMode", true);
            }

            e.setNumeroDui(dui);
            e.setNombrePersona(nombre);
            e.setUsuario(usuario);
            e.setNumeroTelefono(telefono);
            e.setCorreoInstitucional(correo);
            e.setFechaNacimiento(fechaSQL);
            req.setAttribute("empleados", e);
            req.getRequestDispatcher("/empleados-form.jsp").forward(req, resp);
            return;
        }

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                // Editar
                int id = Integer.parseInt(idStr);
                Empleado e = new Empleado();
                e.setIdEmpleado(id);
                e.setNumeroDui(dui);
                e.setNombrePersona(nombre.trim());
                e.setUsuario(usuario);
                e.setNumeroTelefono(telefono);
                e.setCorreoInstitucional(correo);
                e.setFechaNacimiento(fechaSQL);
                empleadoDAO.update(e);
            } else {
                // Crear
                Empleado e = new Empleado();
                e.setNumeroDui(dui);
                e.setNombrePersona(nombre.trim());
                e.setUsuario(usuario);
                e.setNumeroTelefono(telefono);
                e.setCorreoInstitucional(correo);
                e.setFechaNacimiento(fechaSQL);
                empleadoDAO.insert(e);
            }
        } catch (SQLException e) {
            throw new ServletException("Error guardando Empleados", e);
        }

        resp.sendRedirect(req.getContextPath() + "/empleados");
    }
}