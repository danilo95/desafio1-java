package sv.edu.udb.www.desafio.model;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
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
        String sql = "SELECT idEmpleado, numeroDui, nombrePersona, usuario, numeroTelefono, correoInstitucional FROM empleados ORDER BY idEmpleado";
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
                lista.add(e);
            }
        }
        return lista;
    }

    public boolean delete(int idEmpleado) throws SQLException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Empleados WHERE idEmpleado = ?")) {
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        }
    }
}