module org.example.gestionveterinaria {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.gestionveterinaria to javafx.fxml;
    exports org.example.gestionveterinaria;
}