package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.Tipocontratacion;
import sv.edu.udb.www.desafio.model.TipocontratacionDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "TipoContratacionFormServlet", urlPatterns = {"/tipos-contratacion/nuevo", "/tipos-contratacion/editar"})
public class TipoContratacionFormServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql2")
    private DataSource ds;

    private TipocontratacionDAO tipoDAO;

    @Override
    public void init() throws ServletException {
        tipoDAO = new TipocontratacionDAO();
        tipoDAO.setDataSource(ds);
    }

    // Mostrar formulario (nuevo o editar)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath();

        if ("/tipos-contratacion/editar".equals(servletPath)) {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/tipos-contratacion");
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                Tipocontratacion t = tipoDAO.findById(id);
                if (t == null) {
                    resp.sendRedirect(req.getContextPath() + "/tipos-contratacion");
                    return;
                }
                req.setAttribute("tipo", t); // para poblar el form
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/tipos-contratacion");
                return;
            } catch (SQLException e) {
                throw new ServletException("Error cargando tipo de contratación", e);
            }
        }

        req.getRequestDispatcher("/tipoContratacion-form.jsp").forward(req, resp);
    }

    // Guardar para crear o editar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id"); // presente en editar
        String nombre = req.getParameter("nombre");

        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre del tipo de contratación es obligatorio.");
            Tipocontratacion t = new Tipocontratacion();
            if (idStr != null && !idStr.trim().isEmpty()) {
                try { t.setIdTipoContratacion(Integer.parseInt(idStr)); } catch (NumberFormatException ignored) {}
            }
            t.setTipoContratacion(nombre);
            req.setAttribute("tipo", t);
            req.getRequestDispatcher("/tipoContratacion-form.jsp").forward(req, resp);
            return;
        }

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                // Editar
                int id = Integer.parseInt(idStr);
                Tipocontratacion t = new Tipocontratacion();
                t.setIdTipoContratacion(id);
                t.setTipoContratacion(nombre.trim());
                tipoDAO.update(t);
            } else {
                // Crear
                Tipocontratacion t = new Tipocontratacion();
                t.setTipoContratacion(nombre.trim());
                tipoDAO.insert(t);
            }
        } catch (SQLException e) {
            throw new ServletException("Error guardando tipo de contratación", e);
        }

        resp.sendRedirect(req.getContextPath() + "/tipos-contratacion");
    }
}
