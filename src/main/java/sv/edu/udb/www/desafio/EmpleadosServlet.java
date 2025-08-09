package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.EmpleadoDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EmpleadosServlet", urlPatterns = {"/empleados"})
public class EmpleadosServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    private EmpleadoDAO empleadoDAO;

    @Override
    public void init() {
        empleadoDAO = new EmpleadoDAO();

        empleadoDAO.setDataSource(ds);
    }

    // get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("empleados", empleadoDAO.findAll());
            req.getRequestDispatcher("/empleados.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error consultando empleados", e);
        }
    }

    // ELIMINAR (desde el formulario POST del empleados.jsp)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if ("eliminar".equals(accion)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    empleadoDAO.delete(id);
                } catch (NumberFormatException ignored) {
                    // id inválido
                } catch (SQLException e) {
                    // error al intentar eliminar
                    throw new ServletException("Error eliminando empleado", e);
                }
            }
        }
        // Vuelvo a llamar empleados para actualizar
        resp.sendRedirect(req.getContextPath() + "/empleados");
    }
}