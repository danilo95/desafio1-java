package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    private DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public List<Departamento> findAll() throws SQLException {
        String sql = "SELECT idDepartamento, nombreDepartamento, descripcionDepartamento FROM Departamento ORDER BY idDepartamento";
        List<Departamento> lista = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departamento d = new Departamento();
                d.setIdDepartamento(rs.getInt("idDepartamento"));
                d.setNombreDepartamento(rs.getString("nombreDepartamento"));
                d.setDescripcionDepartamento(rs.getString("descripcionDepartamento"));
                lista.add(d);
            }
        }
        return lista;
    }

    public boolean delete(int idDepartamento) throws SQLException {
        String sql = "DELETE FROM Departamento WHERE idDepartamento = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDepartamento);
            return ps.executeUpdate() > 0;
        }
    }
}