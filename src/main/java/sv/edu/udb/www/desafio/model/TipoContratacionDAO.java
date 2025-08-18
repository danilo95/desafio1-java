package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoContratacionDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TipoContratacion> findAll() throws SQLException {
        List<TipoContratacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM TipoContratacion";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoContratacion tc = new TipoContratacion(
                        rs.getInt("idTipoContratacion"),
                        rs.getString("tipoContratacion")
                );
                lista.add(tc);
            }
        }
        return lista;
    }

    public TipoContratacion findById(int id) throws SQLException {
        String sql = "SELECT * FROM TipoContratacion WHERE idTipoContratacion = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoContratacion(
                            rs.getInt("idTipoContratacion"),
                            rs.getString("tipoContratacion")
                    );
                }
            }
        }
        return null;
    }

    public void insert(TipoContratacion tipo) throws SQLException {
        String sql = "INSERT INTO TipoContratacion (tipoContratacion) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo.getTipoContratacion());
            stmt.executeUpdate();
        }
    }

    public void update(TipoContratacion tipo) throws SQLException {
        String sql = "UPDATE TipoContratacion SET tipoContratacion=? WHERE idTipoContratacion=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo.getTipoContratacion());
            stmt.setInt(2, tipo.getIdTipoContratacion());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM TipoContratacion WHERE idTipoContratacion=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}