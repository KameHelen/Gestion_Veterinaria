package org.example.gestionveterinaria.controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; // <-- Importar Scene
import org.example.gestionveterinaria.modelo.ConexionBD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lblMensaje;

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText(); // En un entorno real, esto se compararía con un hash

        if (validarCredenciales(username, password)) {
            lblMensaje.setText("");
            // Aquí iría la lógica para abrir la ventana principal según el rol
            abrirVentanaPrincipal(obtenerRolUsuario(username));
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
            mostrarAlerta("Error de Autenticación", "Las credenciales ingresadas no son válidas.");
        }
    }

    private boolean validarCredenciales(String username, String password) {
        String sql = "SELECT password_hash, rol FROM Usuario WHERE username = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // En este ejemplo simple, se compara directamente. En la vida real, usa BCrypt u otra librería de hashing.
                // Suponiendo que las contraseñas guardadas en la DB son las planas ("admin123", "carlos123", etc.)
                // Para usar hashes, se usaría BCrypt.checkpw(password_plana, storedHash)
                return storedHash.equals(password);
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private String obtenerRolUsuario(String username) {
        String sql = "SELECT rol FROM Usuario WHERE username = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("rol");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // O lanzar una excepción
    }

    private void abrirVentanaPrincipal(String rol) {
        try {
            // Cerrar la ventana de login
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();

            // Abrir la ventana principal según el rol
            FXMLLoader loader;
            Parent root;
            if ("Administrador".equals(rol)) {
                loader = new FXMLLoader(getClass().getResource("VistaAdmin.fxml"));// Asegúrate que la ruta sea correcta
                root = loader.load();
                Stage adminStage = new Stage();
                adminStage.setTitle("Panel de Administración");
                adminStage.setScene(new Scene(root)); // <-- Ahora Scene está importado
                adminStage.setResizable(false);
                adminStage.show();
            } else if ("Veterinario".equals(rol)) {
                System.out.println("Bienvenido Veterinario!");
                // Aquí irá la lógica para abrir la interfaz de Veterinario
                mostrarAlerta("Inicio de Sesión Exitoso", "Bienvenido, Veterinario!");
            }

        } catch (IOException e) { // Capturar IOException por FXMLLoader.load()
            System.err.println("Error al abrir la ventana principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}