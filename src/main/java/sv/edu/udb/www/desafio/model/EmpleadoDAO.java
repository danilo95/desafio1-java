package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public List<Empleado> findAll() throws SQLException {
        String sql = "SELECT idEmpleado, numeroDui, nombrePersona, usuario, numeroTelefono, correoInstitucional, fechaNacimiento FROM empleados ORDER BY idEmpleado";
        List<Empleado> lista = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setNumeroDui(rs.getString("numeroDui"));
                e.setNombrePersona(rs.getString("nombrePersona"));
                e.setUsuario(rs.getString("usuario"));
                e.setNumeroTelefono(rs.getString("numeroTelefono"));
                e.setCorreoInstitucional(rs.getString("correoInstitucional"));
                e.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                lista.add(e);
            }
        }
        return lista;
    }

    public Empleado findById(int id) throws SQLException {
        String sql = "SELECT idEmpleado, numeroDui, nombrePersona, usuario, numeroTelefono, correoInstitucional, fechaNacimiento FROM Empleados WHERE idEmpleado = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado e = new Empleado();
                    e.setIdEmpleado(rs.getInt("idEmpleado"));
                    e.setNumeroDui(rs.getString("numeroDui"));
                    e.setNombrePersona(rs.getString("nombrePersona"));
                    e.setUsuario(rs.getString("usuario"));
                    e.setNumeroTelefono(rs.getString("numeroTelefono"));
                    e.setCorreoInstitucional(rs.getString("correoInstitucional"));
                    e.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    return e;
                }
            }
        }
        return null;
    }

    public int insert(Empleado e) throws SQLException {
        String sql = "INSERT INTO Empleados (numeroDui, nombrePersona, usuario, numeroTelefono, correoInstitucional, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNumeroDui());
            ps.setString(2, e.getNombrePersona());
            ps.setString(3, e.getUsuario());
            ps.setString(4, e.getNumeroTelefono());
            ps.setString(5, e.getCorreoInstitucional());
            ps.setDate(6, e.getFechaNacimiento());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public boolean update(Empleado e) throws SQLException {
        String sql = "UPDATE Empleados SET numeroDui = ?, nombrePersona = ?, usuario = ?, numeroTelefono = ?, correoInstitucional = ?, fechaNacimiento = ? WHERE idEmpleado = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNumeroDui());
            ps.setString(2, e.getNombrePersona());
            ps.setString(3, e.getUsuario());
            ps.setString(4, e.getNumeroTelefono());
            ps.setString(5, e.getCorreoInstitucional());
            ps.setDate(6, e.getFechaNacimiento());
            ps.setInt(7, e.getIdEmpleado());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM Empleados WHERE idEmpleado = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        }
    }
}