package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Cargo> findAll() throws SQLException {
        List<Cargo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cargos";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cargo c = new Cargo(
                        rs.getInt("idCargo"),
                        rs.getString("cargo"),
                        rs.getString("descripcionCargo"),
                        rs.getBoolean("jefatura")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public Cargo findById(int id) throws SQLException {
        String sql = "SELECT * FROM Cargos WHERE idCargo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cargo(
                            rs.getInt("idCargo"),
                            rs.getString("cargo"),
                            rs.getString("descripcionCargo"),
                            rs.getBoolean("jefatura")
                    );
                }
            }
        }
        return null;
    }

    public void insert(Cargo cargo) throws SQLException {
        String sql = "INSERT INTO Cargos (cargo, descripcionCargo, jefatura) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo.getCargo());
            stmt.setString(2, cargo.getDescripcionCargo());
            stmt.setBoolean(3, cargo.isJefatura());
            stmt.executeUpdate();
        }
    }

    public void update(Cargo cargo) throws SQLException {
        String sql = "UPDATE Cargos SET cargo=?, descripcionCargo=?, jefatura=? WHERE idCargo=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo.getCargo());
            stmt.setString(2, cargo.getDescripcionCargo());
            stmt.setBoolean(3, cargo.isJefatura());
            stmt.setInt(4, cargo.getIdCargo());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cargos WHERE idCargo=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}