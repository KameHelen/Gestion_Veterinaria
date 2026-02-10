package org.example.gestionveterinaria.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.gestionveterinaria.modelo.*;

public class ControladorPrincipal {

    // Campos y botones para búsqueda de animales
    @FXML private TextField txtBuscarAnimalNombre;
    @FXML private Button btnBuscarAnimalNombre;
    @FXML private TextField txtBuscarAnimalChip;
    @FXML private Button btnBuscarAnimalChip;
    @FXML private Label lblResultadoBusquedaAnimal;

    // Campos y botones para búsqueda de clientes
    @FXML private TextField txtBuscarClienteNombre;
    @FXML private Button btnBuscarCliente;
    @FXML private Label lblResultadoBusquedaCliente;

    // Campos y botones para añadir cliente
    @FXML private TextField txtNombreCliente;
    @FXML private TextField txtApellidosCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtCorreoCliente;
    @FXML private Button btnAgregarCliente;
    @FXML private Label lblResultadoCliente;

    // Campos y botones para añadir animal
    @FXML private TextField txtNombreAnimal;
    @FXML private TextField txtFechaNacimientoAnimal;
    @FXML private TextField txtNumeroChipAnimal;
    @FXML private TextField txtTipoAnimal;
    @FXML private TextField txtIdClienteAnimal; // Supondremos que se ingresa el ID del cliente
    @FXML private Button btnAgregarAnimal;
    @FXML private Label lblResultadoAnimal;

    // Instancias de los controladores de modelo
    private final ControladorCliente ctrlCliente = new ControladorCliente();
    private final ControladorAnimal ctrlAnimal = new ControladorAnimal();


    // --- Métodos para Búsqueda de Animales ---

    @FXML
    private void handleBuscarAnimalPorNombre() {
        String nombre = txtBuscarAnimalNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("Buscar Animal", "Por favor, introduce un nombre para buscar.");
            return;
        }

        // Nota: El modelo actual no tiene un método para buscar por nombre directamente en BD.
        // Simplemente obtenemos todos y buscamos localmente en Java. Esto no es eficiente para grandes volúmenes de datos.
        // En un entorno real, se haría una consulta SQL con LIKE o =
        java.util.List<Animal> todosLosAnimales = ctrlAnimal.obtenerTodosLosAnimales();
        StringBuilder resultado = new StringBuilder("Animales encontrados con nombre '" + nombre + "':\n");

        boolean encontrado = false;
        for (Animal animal : todosLosAnimales) {
            if (animal.getNombre().toLowerCase().contains(nombre.toLowerCase())) { // Búsqueda parcial
                resultado.append("- ").append(animal.toString()).append("\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            resultado.append("Ningún animal encontrado con ese nombre.");
        }

        lblResultadoBusquedaAnimal.setText(resultado.toString());
    }

    @FXML
    private void handleBuscarAnimalPorChip() {
        String chip = txtBuscarAnimalChip.getText().trim();
        if (chip.isEmpty()) {
            mostrarAlerta("Buscar Animal", "Por favor, introduce el número de chip para buscar.");
            return;
        }

        Animal animal = ctrlAnimal.buscarAnimalPorChip(chip);
        if (animal != null) {
            lblResultadoBusquedaAnimal.setText("Animal encontrado:\n" + animal.toString());
        } else {
            lblResultadoBusquedaAnimal.setText("No se encontró ningún animal con el chip: " + chip);
        }
    }


    // --- Método para Búsqueda de Clientes ---

    @FXML
    private void handleBuscarCliente() {
        String nombre = txtBuscarClienteNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("Buscar Cliente", "Por favor, introduce un nombre para buscar.");
            return;
        }

        java.util.List<Cliente> todosLosClientes = ctrlCliente.obtenerTodosLosClientes();
        StringBuilder resultado = new StringBuilder("Clientes encontrados con nombre '" + nombre + "':\n");

        boolean encontrado = false;
        for (Cliente cliente : todosLosClientes) {
            if ((cliente.getNombre() + " " + cliente.getApellidos()).toLowerCase().contains(nombre.toLowerCase())) { // Búsqueda en nombre y apellidos
                resultado.append("- ").append(cliente.toString()).append("\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            resultado.append("Ningún cliente encontrado con ese nombre.");
        }

        lblResultadoBusquedaCliente.setText(resultado.toString());
    }


    // --- Métodos para Añadir Cliente y Animal ---

    @FXML
    private void handleAgregarCliente() {
        String nombre = txtNombreCliente.getText().trim();
        String apellidos = txtApellidosCliente.getText().trim();
        String telefono = txtTelefonoCliente.getText().trim();
        String correo = txtCorreoCliente.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty()) {
            lblResultadoCliente.setText("Error: El nombre y apellidos son obligatorios.");
            return;
        }

        Cliente nuevoCliente = new Cliente(nombre, apellidos, telefono, correo);

        if (ctrlCliente.insertarCliente(nuevoCliente)) {
            lblResultadoCliente.setText("Cliente agregado exitosamente: " + nuevoCliente.getNombre() + " " + nuevoCliente.getApellidos());
            // Limpiar campos
            txtNombreCliente.clear();
            txtApellidosCliente.clear();
            txtTelefonoCliente.clear();
            txtCorreoCliente.clear();
        } else {
            lblResultadoCliente.setText("Error: No se pudo agregar el cliente.");
        }
    }

    @FXML
    private void handleAgregarAnimal() {
        String nombre = txtNombreAnimal.getText().trim();
        String fechaNacStr = txtFechaNacimientoAnimal.getText().trim(); // Debe ser YYYY-MM-DD
        String chip = txtNumeroChipAnimal.getText().trim();
        String tipo = txtTipoAnimal.getText().trim().toLowerCase(); // Perro, Gato, Exotico
        String idClienteStr = txtIdClienteAnimal.getText().trim();

        if (nombre.isEmpty() || fechaNacStr.isEmpty() || chip.isEmpty() || tipo.isEmpty() || idClienteStr.isEmpty()) {
            lblResultadoAnimal.setText("Error: Todos los campos son obligatorios.");
            return;
        }

        int idCliente;
        try {
            idCliente = Integer.parseInt(idClienteStr);
        } catch (NumberFormatException e) {
            lblResultadoAnimal.setText("Error: El ID del cliente debe ser un número.");
            return;
        }

        // Validar tipo
        Animal nuevoAnimal;
        switch (tipo) {
            case "perro":
                nuevoAnimal = new Perro(chip, nombre, fechaNacStr, null); // El dueño se asignará después
                break;
            case "gato":
                nuevoAnimal = new Gato(chip, nombre, fechaNacStr, null);
                break;
            case "exotico":
                nuevoAnimal = new Exotico(chip, nombre, fechaNacStr, null);
                break;
            default:
                lblResultadoAnimal.setText("Error: Tipo de animal inválido. Use 'Perro', 'Gato' o 'Exotico'.");
                return;
        }

        // Buscar el cliente para asignarlo como dueño
        Cliente dueño = ctrlCliente.buscarClientePorId(idCliente);
        if (dueño == null) {
            lblResultadoAnimal.setText("Error: No se encontró un cliente con ID: " + idCliente);
            return;
        }
        nuevoAnimal.setDueño(dueño); // Asignar el dueño encontrado

        if (ctrlAnimal.insertarAnimal(nuevoAnimal)) {
            lblResultadoAnimal.setText("Animal agregado exitosamente: " + nuevoAnimal.getNombre() + " (" + nuevoAnimal.getTipo() + ") con chip: " + chip);
            // Limpiar campos
            txtNombreAnimal.clear();
            txtFechaNacimientoAnimal.clear();
            txtNumeroChipAnimal.clear();
            txtTipoAnimal.clear();
            txtIdClienteAnimal.clear();
        } else {
            lblResultadoAnimal.setText("Error: No se pudo agregar el animal.");
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