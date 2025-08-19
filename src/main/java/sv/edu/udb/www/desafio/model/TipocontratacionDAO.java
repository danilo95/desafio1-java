package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipocontratacionDAO {

    private DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public List<Tipocontratacion> findAll() throws SQLException {
        String sql = "SELECT idTipoContratacion, tipoContratacion FROM tipocontratacion ORDER BY idTipoContratacion";
        List<Tipocontratacion> lista = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipocontratacion t = new Tipocontratacion();
                t.setIdTipoContratacion(rs.getInt("idTipoContratacion"));
                t.setTipoContratacion(rs.getString("tipoContratacion"));
                lista.add(t);
            }
        }
        return lista;
    }

    public Tipocontratacion findById(int id) throws SQLException {
        String sql = "SELECT idTipoContratacion, tipoContratacion FROM tipocontratacion WHERE idTipoContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tipocontratacion t = new Tipocontratacion();
                    t.setIdTipoContratacion(rs.getInt("idTipoContratacion"));
                    t.setTipoContratacion(rs.getString("tipoContratacion"));
                    return t;
                }
            }
        }
        return null;
    }

    public int insert(Tipocontratacion t) throws SQLException {
        String sql = "INSERT INTO tipocontratacion (tipoContratacion) VALUES (?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTipoContratacion());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return 0;
        }
    }

    public boolean update(Tipocontratacion t) throws SQLException {
        String sql = "UPDATE tipocontratacion SET tipoContratacion = ? WHERE idTipoContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getTipoContratacion());
            ps.setInt(2, t.getIdTipoContratacion());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idTipoContratacion) throws SQLException {
        String sql = "DELETE FROM tipocontratacion WHERE idTipoContratacion = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTipoContratacion);
            return ps.executeUpdate() > 0;
        }
    }
}

