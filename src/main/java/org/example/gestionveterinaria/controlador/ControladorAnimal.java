package org.example.gestionveterinaria.controlador;


import org.example.gestionveterinaria.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorAnimal {

    private final ControladorCliente controladorCliente = new ControladorCliente();

    // Inserta un nuevo animal en la base de datos.
    // Asume que el cliente dueño ya existe y tiene un ID válido.
    public boolean insertarAnimal(Animal animal) {
        String sql = "INSERT INTO Animal (numero_chip, nombre, fecha_nacimiento, id_cliente, tipo_animal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNumero_chip());
            pstmt.setString(2, animal.getNombre());
            pstmt.setDate(3, Date.valueOf(animal.getFecha_nacimiento())); // Asumiendo que fecha_nacimiento es String, conviértela a LocalDate y luego a Date
            pstmt.setInt(4, animal.getDueño().getId_cliente()); // El dueño debe tener ID
            pstmt.setString(5, animal.getTipo());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Obtiene todos los animales de la base de datos y los devuelve como objetos Animal
    public List<Animal> obtenerTodosLosAnimales() {
        List<Animal> animales = new ArrayList<>();
        String sql = "SELECT a.numero_chip, a.nombre, a.fecha_nacimiento, a.tipo_animal, " +
                "c.id_cliente, c.nombre AS cliente_nombre, c.apellidos AS cliente_apellidos " +
                "FROM Animal a LEFT JOIN Cliente c ON a.id_cliente = c.id_cliente";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Obtener datos del animal
                String numeroChip = rs.getString("numero_chip");
                String nombre = rs.getString("nombre");
                String fechaNacimientoStr = rs.getDate("fecha_nacimiento").toString(); // Convertir a String o LocalDate
                String tipo = rs.getString("tipo_animal");

                // Obtener datos del cliente (dueño)
                int idCliente = rs.getInt("id_cliente"); // Puede ser NULL si no tiene dueño
                Cliente dueño = null;
                if (!rs.wasNull()) { // Verificar si el id_cliente era NULL
                    // Opción 1: Usar el controladorCliente para obtener el objeto Cliente completo
                    dueño = controladorCliente.buscarClientePorId(idCliente);
                    // Opción 2: Crear un Cliente temporal solo con el ID si no necesitas los demás datos aquí
                    // dueño = new Cliente();
                    // dueño.setId_cliente(idCliente);
                }


                // Crear instancia específica según el tipo
                Animal animal;
                switch (tipo.toLowerCase()) {
                    case "perro":
                        animal = new Perro(numeroChip, nombre, fechaNacimientoStr, dueño);
                        break;
                    case "gato":
                        animal = new Gato(numeroChip, nombre, fechaNacimientoStr, dueño);
                        break;
                    case "exotico":
                        animal = new Exotico(numeroChip, nombre, fechaNacimientoStr, dueño);
                        break;
                    default:
                        System.err.println("Tipo de animal desconocido: " + tipo);
                        continue; // Saltar este registro
                }
                animales.add(animal);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener animales: " + e.getMessage());
            e.printStackTrace();
        }
        return animales;
    }

    // Busca un animal por su número de chip.
    public Animal buscarAnimalPorChip(String chip) {
        String sql = "SELECT a.numero_chip, a.nombre, a.fecha_nacimiento, a.tipo_animal, " +
                "c.id_cliente, c.nombre AS cliente_nombre, c.apellidos AS cliente_apellidos " +
                "FROM Animal a LEFT JOIN Cliente c ON a.id_cliente = c.id_cliente " +
                "WHERE a.numero_chip = ?";
        Animal animal = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chip);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String fechaNacimientoStr = rs.getDate("fecha_nacimiento").toString();
                String tipo = rs.getString("tipo_animal");

                int idCliente = rs.getInt("id_cliente");
                Cliente dueño = null;
                if (!rs.wasNull()) {
                    dueño = controladorCliente.buscarClientePorId(idCliente);
                }

                switch (tipo.toLowerCase()) {
                    case "perro":
                        animal = new Perro(chip, nombre, fechaNacimientoStr, dueño);
                        break;
                    case "gato":
                        animal = new Gato(chip, nombre, fechaNacimientoStr, dueño);
                        break;
                    case "exotico":
                        animal = new Exotico(chip, nombre, fechaNacimientoStr, dueño);
                        break;
                    default:
                        System.err.println("Tipo de animal desconocido: " + tipo);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar animal por chip: " + e.getMessage());
            e.printStackTrace();
        }
        return animal;
    }

    // Actualiza la información de un animal existente.
    // Asume que el cliente dueño ya existe y tiene un ID válido.
    public boolean actualizarAnimal(Animal animal) {
        String sql = "UPDATE Animal SET nombre = ?, fecha_nacimiento = ?, id_cliente = ? WHERE numero_chip = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setDate(2, Date.valueOf(animal.getFecha_nacimiento()));
            if (animal.getDueño() != null) {
                pstmt.setInt(3, animal.getDueño().getId_cliente());
            } else {
                pstmt.setNull(3, Types.INTEGER); // Si no tiene dueño, establecer id_cliente a NULL
            }
            pstmt.setString(4, animal.getNumero_chip());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Elimina un animal por su número de chip.
    public boolean eliminarAnimal(String chip) {
        String sql = "DELETE FROM Animal WHERE numero_chip = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chip);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
