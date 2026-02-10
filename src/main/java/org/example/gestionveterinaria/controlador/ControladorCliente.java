package org.example.gestionveterinaria.controlador;


import org.example.gestionveterinaria.modelo.Cliente;
import org.example.gestionveterinaria.modelo.ConexionBD;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class ControladorCliente {

    // Inserta un nuevo cliente en la base de datos.
    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, apellidos, telefono, correo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreo());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Obtiene todos los clientes de la base de datos.
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellidos, telefono, correo FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
                cliente.setId_cliente(rs.getInt("id_cliente"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    // Busca un cliente por su ID.
    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT id_cliente, nombre, apellidos, telefono, correo FROM Cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
                cliente.setId_cliente(rs.getInt("id_cliente"));
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }

    // Actualiza la información de un cliente existente.
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, apellidos = ?, telefono = ?, correo = ? WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreo());
            pstmt.setInt(5, cliente.getId_cliente());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Elimina un cliente por su ID.
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
