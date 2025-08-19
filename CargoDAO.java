package sv.edu.udb.www.desafio.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {
    private DataSource dataSource;

    public CargoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Listar todos los cargos
    public List<Cargo> listar() throws SQLException {
        List<Cargo> lista = new ArrayList<>();
        String sql = "SELECT idCargo, cargo, descripcionCargo, jefatura FROM cargos ORDER BY idCargo";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cargo c = new Cargo();
                c.setIdCargo(rs.getInt("idCargo"));
                c.setCargo(rs.getString("cargo"));
                c.setDescripcionCargo(rs.getString("descripcionCargo"));
                c.setJefatura(rs.getBoolean("jefatura"));
                lista.add(c);
            }
        }
        return lista;
    }

    // Insertar un nuevo cargo
    public void insertar(Cargo c) throws SQLException {
        String sql = "INSERT INTO cargos (cargo, descripcionCargo, jefatura) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCargo());
            ps.setString(2, c.getDescripcionCargo());
            ps.setBoolean(3, c.isJefatura());
            ps.executeUpdate();
        }
    }

    // Obtener cargo por ID
    public Cargo obtenerCargo(int idCargo) throws SQLException {
        String sql = "SELECT idCargo, cargo, descripcionCargo, jefatura FROM cargos WHERE idCargo = ?";
        Cargo c = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCargo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Cargo();
                    c.setIdCargo(rs.getInt("idCargo"));
                    c.setCargo(rs.getString("cargo"));
                    c.setDescripcionCargo(rs.getString("descripcionCargo"));
                    c.setJefatura(rs.getBoolean("jefatura"));
                }
            }
        }
        return c;
    }

    // Actualizar cargo
    public void actualizar(Cargo c) throws SQLException {
        String sql = "UPDATE cargos SET cargo = ?, descripcionCargo = ?, jefatura = ? WHERE idCargo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCargo());
            ps.setString(2, c.getDescripcionCargo());
            ps.setBoolean(3, c.isJefatura());
            ps.setInt(4, c.getIdCargo());
            ps.executeUpdate();
        }
    }

    // Eliminar cargo
    public void eliminar(int idCargo) throws SQLException {
        String sql = "DELETE FROM cargos WHERE idCargo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCargo);
            ps.executeUpdate();
        }

        // Reenumerar IDs después de eliminar
        reenumerarIds();
    }

    // Reenumerar IDs consecutivos y ajustar AUTO_INCREMENT
    private void reenumerarIds() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {

            // Inicializar contador
            st.execute("SET @contador = 0");

            // Actualizar IDs de forma consecutiva
            st.execute("UPDATE cargos SET idCargo = (@contador := @contador + 1) ORDER BY idCargo");

            // Ajustar AUTO_INCREMENT al siguiente valor
            st.execute("ALTER TABLE cargos AUTO_INCREMENT = 1");
        }
    }
}
