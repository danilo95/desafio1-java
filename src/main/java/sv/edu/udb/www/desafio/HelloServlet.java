package sv.edu.udb.www.desafio;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import sv.edu.udb.www.desafio.model.EmpleadoDAO;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Inject
    private EmpleadoDAO empleadoDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setAttribute("empleados", empleadoDAO.findAll());
            req.getRequestDispatcher("/empleados.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error consultando empleados", e);
        }
    }
}