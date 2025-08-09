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

    public Departamento findById(int id) throws SQLException {
        String sql = "SELECT idDepartamento, nombreDepartamento, descripcionDepartamento FROM Departamento WHERE idDepartamento = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Departamento d = new Departamento();
                    d.setIdDepartamento(rs.getInt("idDepartamento"));
                    d.setNombreDepartamento(rs.getString("nombreDepartamento"));
                    d.setDescripcionDepartamento(rs.getString("descripcionDepartamento"));
                    return d;
                }
            }
        }
        return null;
    }

    public int insert(Departamento d) throws SQLException {
        String sql = "INSERT INTO Departamento (nombreDepartamento, descripcionDepartamento) VALUES (?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcionDepartamento());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public boolean update(Departamento d) throws SQLException {
        String sql = "UPDATE Departamento SET nombreDepartamento = ?, descripcionDepartamento = ? WHERE idDepartamento = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcionDepartamento());
            ps.setInt(3, d.getIdDepartamento());
            return ps.executeUpdate() > 0;
        }
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