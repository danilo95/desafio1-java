package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.DepartamentoDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DepartamentosServlet", urlPatterns = {"/departamentos"})
public class DepartamentosServlet  extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    private DepartamentoDAO departamentoDAO;

    @Override
    public void init() {
        departamentoDAO = new DepartamentoDAO();
        departamentoDAO.setDataSource(ds);
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("departamentos", departamentoDAO.findAll());
            req.getRequestDispatcher("/departamentos.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listando departamentos", e);
        }
    }

    // POST: eliminar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if ("eliminar".equals(accion)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) { // Java 8 compatible
                try {
                    int id = Integer.parseInt(idStr);
                    departamentoDAO.delete(id);
                } catch (NumberFormatException ignored) {
                } catch (SQLException e) {
                    throw new ServletException("Error eliminando departamento", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/departamentos");
    }



}
