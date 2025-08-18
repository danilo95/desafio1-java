package sv.edu.udb.www.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.www.desafio.model.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ContratacionesServlet", urlPatterns = {"/contrataciones"})
public class ContratacionesServlet extends HttpServlet {

    @Resource(lookup = "jdbc/mysql")          // ⬅️ MISMO recurso JNDI que el resto de la app
    private DataSource ds;

    private ContratacionDAO contratacionDAO;
    private DepartamentoDAO departamentoDAO;
    private EmpleadoDAO empleadoDAO;
    private CargoDAO cargoDAO;
    private TipoContratacionDAO tipoContratacionDAO;

    @Override
    public void init() {
        contratacionDAO = new ContratacionDAO();
        departamentoDAO = new DepartamentoDAO();
        empleadoDAO = new EmpleadoDAO();
        cargoDAO = new CargoDAO();
        tipoContratacionDAO = new TipoContratacionDAO();

        // Inyectar el mismo DataSource a todos los DAO
        contratacionDAO.setDataSource(ds);
        departamentoDAO.setDataSource(ds);
        empleadoDAO.setDataSource(ds);
        cargoDAO.setDataSource(ds);
        tipoContratacionDAO.setDataSource(ds);
    }

    // GET: lista, new, edit, delete (navegación)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteContratacion(request, response);
                    break;
                default:
                    listContrataciones(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error en ContratacionesServlet", e);
        }
    }

    // POST: insert/update (form submit)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Decidimos insert/update por presencia de idContratacion
        String idStr = request.getParameter("idContratacion");
        try {
            if (idStr == null || idStr.trim().isEmpty()) {
                insertContratacion(request, response);
            } else {
                updateContratacion(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error al guardar contratación", e);
        }
    }

    // ====== Implementaciones ======

    private void listContrataciones(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Contratacion> lista = contratacionDAO.findAll();
        request.setAttribute("listaContrataciones", lista);
        request.getRequestDispatcher("/contrataciones.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Cargar catálogos
        request.setAttribute("listaDepartamentos", departamentoDAO.findAll());
        request.setAttribute("listaEmpleados", empleadoDAO.findAll());
        request.setAttribute("listaCargos", cargoDAO.findAll());
        request.setAttribute("listaTipos", tipoContratacionDAO.findAll());

        request.getRequestDispatcher("/contrataciones-form.jsp").forward(request, response);
    }

    private void insertContratacion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Contratacion c = new Contratacion();
        c.setIdDepartamento(Integer.parseInt(request.getParameter("idDepartamento")));
        c.setIdEmpleado(Integer.parseInt(request.getParameter("idEmpleado")));
        c.setIdCargo(Integer.parseInt(request.getParameter("idCargo")));
        c.setIdTipoContratacion(Integer.parseInt(request.getParameter("idTipoContratacion")));
        c.setFechaContratacion(Date.valueOf(request.getParameter("fechaContratacion")));
        c.setSalario(new BigDecimal(request.getParameter("salario")));
        // En el form el estado viene como "1" o "0"
        c.setEstado("1".equals(request.getParameter("estado")));

        contratacionDAO.insert(c);
        response.sendRedirect("contrataciones");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Contratacion c = contratacionDAO.findById(id);

        // Cargar catálogos
        request.setAttribute("listaDepartamentos", departamentoDAO.findAll());
        request.setAttribute("listaEmpleados", empleadoDAO.findAll());
        request.setAttribute("listaCargos", cargoDAO.findAll());
        request.setAttribute("listaTipos", tipoContratacionDAO.findAll());

        request.setAttribute("contratacion", c);
        request.getRequestDispatcher("/contrataciones-form.jsp").forward(request, response);
    }

    private void updateContratacion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Contratacion c = new Contratacion();
        c.setIdContratacion(Integer.parseInt(request.getParameter("idContratacion")));
        c.setIdDepartamento(Integer.parseInt(request.getParameter("idDepartamento")));
        c.setIdEmpleado(Integer.parseInt(request.getParameter("idEmpleado")));
        c.setIdCargo(Integer.parseInt(request.getParameter("idCargo")));
        c.setIdTipoContratacion(Integer.parseInt(request.getParameter("idTipoContratacion")));
        c.setFechaContratacion(Date.valueOf(request.getParameter("fechaContratacion")));
        c.setSalario(new BigDecimal(request.getParameter("salario")));
        c.setEstado("1".equals(request.getParameter("estado")));

        contratacionDAO.update(c);
        response.sendRedirect("contrataciones");
    }

    private void deleteContratacion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        contratacionDAO.delete(id);
        response.sendRedirect("contrataciones");
    }
}