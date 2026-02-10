package org.example.gestionveterinaria.controlador;

import org.example.gestionveterinaria.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorAnimal {

    public List<Animal> obtenerTodosLosAnimales() {
        List<Animal> animales = new ArrayList<>();
        String sql = "SELECT * FROM Animal";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Animal animal = crearAnimalDesdeResultSet(rs);
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public Animal buscarAnimalPorChip(String chip) {
        String sql = "SELECT * FROM Animal WHERE numero_chip = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chip);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearAnimalDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertarAnimal(Animal animal) {
        String sql = "INSERT INTO Animal (numero_chip, nombre, fecha_nacimiento, id_cliente, tipo_animal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, animal.getNumeroChip());
            ps.setString(2, animal.getNombre());
            ps.setString(3, animal.getFechaNacimiento());
            ps.setInt(4, animal.getDueno() != null ? animal.getDueno().getId_cliente() : null);
            ps.setString(5, animal.getTipo());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Animal crearAnimalDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo_animal");
        String chip = rs.getString("numero_chip");
        String nombre = rs.getString("nombre");
        String fechaNac = rs.getString("fecha_nacimiento");
        int idCliente = rs.getInt("id_cliente");
        Cliente dueno = null;
        if (idCliente > 0) {
            dueno = new ControladorCliente().buscarClientePorId(idCliente);
        }
        switch (tipo.toLowerCase()) {
            case "perro":
                return new Perro(chip, nombre, fechaNac, dueno);
            case "gato":
                return new Gato(chip, nombre, fechaNac, dueno);
            case "exotico":
                return new Exotico(chip, nombre, fechaNac, dueno);
            default:
                return null;
        }
    }
}
