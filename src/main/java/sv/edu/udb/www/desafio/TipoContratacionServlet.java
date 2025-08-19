package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.TipocontratacionDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "TipoContratacionServlet", urlPatterns = {"/tipos-contratacion"})
public class TipoContratacionServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql2")
    private DataSource ds;

    private TipocontratacionDAO tipocontratacionDAO;

    @Override
    public void init() {
        tipocontratacionDAO = new TipocontratacionDAO();
        tipocontratacionDAO.setDataSource(ds);
    }

    // GET: listar todos
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("tiposContratacion", tipocontratacionDAO.findAll());
            req.getRequestDispatcher("/tiposContratacion.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listando tipos de contratación", e);
        }
    }

    // POST: eliminar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if ("eliminar".equals(accion)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    tipocontratacionDAO.delete(id);
                } catch (NumberFormatException ignored) {
                } catch (SQLException e) {
                    throw new ServletException("Error eliminando tipo de contratación", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/tipos-contratacion");
    }
}
