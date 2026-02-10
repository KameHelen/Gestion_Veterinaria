package org.example.gestionveterinaria.modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Cambia estas constantes según tu configuración de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/gestionveterinaria_db"; // Nombre de la BD
    private static final String USUARIO = "root"; // Por ejemplo, "root"
    private static final String CONTRASENA = ""; // Tu contraseña real

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}