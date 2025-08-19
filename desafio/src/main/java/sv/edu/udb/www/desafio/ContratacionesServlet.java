package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.ContratacionDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ContratacionesServlet", urlPatterns = {"/contrataciones"})
public class ContratacionesServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    private ContratacionDAO contratacionDAO;

    @Override
    public void init() {
        contratacionDAO = new ContratacionDAO();
        contratacionDAO.setDataSource(ds);
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("contrataciones", contratacionDAO.findAll());
            req.getRequestDispatcher("/contrataciones.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listando contrataciones", e);
        }
    }

    // POST: eliminar usando id
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if ("eliminar".equals(accion)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    contratacionDAO.delete(id);
                } catch (NumberFormatException ignored) {
                } catch (SQLException e) {
                    throw new ServletException("Error eliminando contratación", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/contrataciones");
    }
}