package org.example.gestionveterinaria.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ControladorAdmin {

    @FXML
    private void handleGestionarClientes(ActionEvent event) {
        System.out.println("Abriendo ventana de gestión de clientes...");
        // Aquí llamarías a la lógica para abrir la ventana de clientes
        // Por ahora, simulamos con un mensaje
        mostrarAlerta("Funcionalidad", "Ir a la ventana de gestión de clientes.");
    }

    @FXML
    private void handleGestionarAnimales(ActionEvent event) {
        System.out.println("Abriendo ventana de gestión de animales...");
        // Aquí llamarías a la lógica para abrir la ventana de animales
        mostrarAlerta("Funcionalidad", "Ir a la ventana de gestión de animales.");
    }

    @FXML
    private void handleGestionarCitas(ActionEvent event) {
        System.out.println("Abriendo ventana de gestión de citas...");
        // Aquí llamarías a la lógica para abrir la ventana de citas
        mostrarAlerta("Funcionalidad", "Ir a la ventana de gestión de citas.");
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        System.out.println("Cerrando sesión...");
        try {
            // Cerrar la ventana actual de Admin
            Stage adminStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            adminStage.close();

            // Volver a abrir la ventana de login
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/vistas/VistaLogin.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            Stage loginStage = new Stage();
            loginStage.setTitle("Sistema de Gestión Veterinaria - Login");
            loginStage.setScene(scene);
            loginStage.setResizable(false);
            loginStage.show();

        } catch (Exception e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
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