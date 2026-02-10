module org.example.gestionveterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.gestionveterinaria to javafx.fxml;
    opens org.example.gestionveterinaria.modelo to javafx.base;
    exports org.example.gestionveterinaria;
    exports org.example.gestionveterinaria.controlador to javafx.fxml;
    opens org.example.gestionveterinaria.controlador to javafx.fxml;
}