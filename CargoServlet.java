package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.Cargo;
import sv.edu.udb.www.desafio.model.CargoDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cargo")
public class CargoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/mysql") // tu JNDI correcto
    private DataSource dataSource;

    private CargoDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new CargoDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action == null) action = "list";

        try {
            List<Cargo> lista = dao.listar(); // Declarada una sola vez

            switch (action) {
                case "nuevo":
                    // Configura la acción para el formulario
                    request.setAttribute("accion", "nuevo");
                    request.setAttribute("cargos", lista);
                    request.getRequestDispatcher("/cargo.jsp").forward(request, response);
                    return;

                case "editar":
                    if (idParam != null) {
                        int id = Integer.parseInt(idParam);
                        Cargo cargo = dao.obtenerCargo(id);
                        request.setAttribute("cargo", cargo);
                        request.setAttribute("accion", "editar");
                        request.setAttribute("cargos", lista);
                        request.getRequestDispatcher("/cargo.jsp").forward(request, response);
                        return;
                    }
                    break;

                case "eliminar":
                    if (idParam != null) {
                        int id = Integer.parseInt(idParam);
                        dao.eliminar(id);
                    }
                    response.sendRedirect(request.getContextPath() + "/cargo");
                    return;

                case "list":
                default:
                    request.setAttribute("cargos", lista);
                    request.getRequestDispatcher("/cargo.jsp").forward(request, response);
                    return;
            }
        } catch (SQLException e) {
            throw new ServletException("Error con la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nombreCargo = request.getParameter("cargo");
        String descripcionCargo = request.getParameter("descripcionCargo");
        boolean jefatura = request.getParameter("jefatura") != null;

        Cargo c = new Cargo();
        c.setCargo(nombreCargo);
        c.setDescripcionCargo(descripcionCargo);
        c.setJefatura(jefatura);

        try {
            if (idParam == null || idParam.isEmpty()) {
                dao.insertar(c); // Nuevo cargo
            } else {
                c.setIdCargo(Integer.parseInt(idParam));
                dao.actualizar(c); // Editar cargo
            }
        } catch (SQLException e) {
            throw new ServletException("Error al guardar el cargo", e);
        }

        // Redirige a la lista de cargos después de guardar
        response.sendRedirect(request.getContextPath() + "/cargo");
    }
}
