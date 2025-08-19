package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratacionDAO {
    private DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public List<Contratacion> findAll() throws SQLException {
        String sql =
                "SELECT c.idContratacion, " +
                        "       c.idDepartamento, d.nombreDepartamento, " +
                        "       c.idEmpleado, e.nombrePersona AS nombreEmpleado, " +
                        "       c.idCargo, ca.cargo AS nombreCargo, " +
                        "       c.idTipoContratacion, tc.tipoContratacion AS nombreTipoContratacion, " +
                        "       c.fechaContratacion, c.salario, c.estado " +
                        "FROM Contrataciones c " +
                        "JOIN Departamento d ON d.idDepartamento = c.idDepartamento " +
                        "JOIN Empleados e ON e.idEmpleado = c.idEmpleado " +
                        "JOIN Cargos ca ON ca.idCargo = c.idCargo " +
                        "JOIN TipoContratacion tc ON tc.idTipoContratacion = c.idTipoContratacion " +
                        "ORDER BY c.idContratacion";

        List<Contratacion> lista = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contratacion c = new Contratacion();
                c.setIdContratacion(rs.getInt("idContratacion"));
                c.setIdDepartamento(rs.getInt("idDepartamento"));
                c.setNombreDepartamento(rs.getString("nombreDepartamento"));
                c.setIdEmpleado(rs.getInt("idEmpleado"));
                c.setNombreEmpleado(rs.getString("nombreEmpleado"));
                c.setIdCargo(rs.getInt("idCargo"));
                c.setNombreCargo(rs.getString("nombreCargo"));
                c.setIdTipoContratacion(rs.getInt("idTipoContratacion"));
                c.setNombreTipoContratacion(rs.getString("nombreTipoContratacion"));
                c.setFechaContratacion(rs.getDate("fechaContratacion"));
                c.setSalario(rs.getBigDecimal("salario"));
                c.setEstado(rs.getBoolean("estado"));
                lista.add(c);
            }
        }
        return lista;
    }

    public boolean delete(int idContratacion) throws SQLException {
        String sql = "DELETE FROM Contrataciones WHERE idContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContratacion);
            return ps.executeUpdate() > 0;
        }
    }
}