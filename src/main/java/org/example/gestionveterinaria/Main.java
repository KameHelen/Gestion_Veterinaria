package org.example.gestionveterinaria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el archivo FXML de la vista principal
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/gestionveterinaria/VistaPrincipal.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Sistema de Gestión Veterinaria - Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); // Permitir redimensionar
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}