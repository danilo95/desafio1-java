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

    private Contratacion mapRow(ResultSet rs) throws SQLException {
        Contratacion c = new Contratacion();
        c.setIdContratacion(rs.getInt("idContratacion"));
        c.setIdDepartamento(rs.getInt("idDepartamento"));
        c.setIdEmpleado(rs.getInt("idEmpleado"));
        c.setIdCargo(rs.getInt("idCargo"));
        c.setIdTipoContratacion(rs.getInt("idTipoContratacion"));
        c.setFechaContratacion(rs.getDate("fechaContratacion"));
        c.setSalario(rs.getBigDecimal("salario"));
        c.setEstado(rs.getBoolean("estado"));

        c.setNombreDepartamento(rs.getString("nombreDepartamento"));
        c.setNombreEmpleado(rs.getString("nombreEmpleado"));
        c.setNombreCargo(rs.getString("nombreCargo"));
        c.setNombreTipoContratacion(rs.getString("nombreTipoContratacion"));
        return c;
    }

    public List<Contratacion> findAll() throws SQLException {
        String sql = "SELECT c.*, " +
                "d.nombreDepartamento, " +
                "e.nombrePersona AS nombreEmpleado, " +
                "ca.cargo AS nombreCargo, " +
                "t.tipoContratacion AS nombreTipoContratacion " +
                "FROM Contrataciones c " +
                "INNER JOIN Departamento d ON c.idDepartamento = d.idDepartamento " +
                "INNER JOIN Empleados e ON c.idEmpleado = e.idEmpleado " +
                "INNER JOIN Cargos ca ON c.idCargo = ca.idCargo " +
                "INNER JOIN TipoContratacion t ON c.idTipoContratacion = t.idTipoContratacion " +
                "ORDER BY c.idContratacion DESC";
        List<Contratacion> lista = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public Contratacion findById(int idContratacion) throws SQLException {
        String sql = "SELECT c.*, " +
                "d.nombreDepartamento, " +
                "e.nombrePersona AS nombreEmpleado, " +
                "ca.cargo AS nombreCargo, " +
                "t.tipoContratacion AS nombreTipoContratacion " +
                "FROM Contrataciones c " +
                "INNER JOIN Departamento d ON c.idDepartamento = d.idDepartamento " +
                "INNER JOIN Empleados e ON c.idEmpleado = e.idEmpleado " +
                "INNER JOIN Cargos ca ON c.idCargo = ca.idCargo " +
                "INNER JOIN TipoContratacion t ON c.idTipoContratacion = t.idTipoContratacion " +
                "WHERE c.idContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContratacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public int insert(Contratacion c) throws SQLException {
        String sql = "INSERT INTO Contrataciones " +
                "(idDepartamento, idEmpleado, idCargo, idTipoContratacion, fechaContratacion, salario, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getIdDepartamento());
            ps.setInt(2, c.getIdEmpleado());
            ps.setInt(3, c.getIdCargo());
            ps.setInt(4, c.getIdTipoContratacion());
            ps.setDate(5, c.getFechaContratacion());
            ps.setBigDecimal(6, c.getSalario());
            ps.setBoolean(7, c.isEstado());

            int affected = ps.executeUpdate();
            if (affected == 0) return 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public boolean update(Contratacion c) throws SQLException {
        String sql = "UPDATE Contrataciones SET " +
                "idDepartamento = ?, idEmpleado = ?, idCargo = ?, idTipoContratacion = ?, " +
                "fechaContratacion = ?, salario = ?, estado = ? " +
                "WHERE idContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getIdDepartamento());
            ps.setInt(2, c.getIdEmpleado());
            ps.setInt(3, c.getIdCargo());
            ps.setInt(4, c.getIdTipoContratacion());
            ps.setDate(5, c.getFechaContratacion());
            ps.setBigDecimal(6, c.getSalario());
            ps.setBoolean(7, c.isEstado());
            ps.setInt(8, c.getIdContratacion());

            return ps.executeUpdate() > 0;
        }
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
