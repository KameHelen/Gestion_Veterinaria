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
    @FXML private TextArea lblResultadoBusquedaAnimal;

    // Campos y botones para búsqueda de clientes
    @FXML private TextField txtBuscarClienteNombre;
    @FXML private Button btnBuscarCliente;
    @FXML private TextArea lblResultadoBusquedaCliente;

    // Campos y botones para añadir cliente
    @FXML private TextField txtNombreCliente;
    @FXML private TextField txtApellidosCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtCorreoCliente;
    @FXML private Button btnAgregarCliente;
    @FXML private TextArea lblResultadoCliente;

    // Campos y botones para añadir animal
    @FXML private TextField txtNombreAnimal;
    @FXML private TextField txtFechaNacimientoAnimal;
    @FXML private TextField txtNumeroChipAnimal;
    @FXML private TextField txtTipoAnimal;
    @FXML private TextField txtIdClienteAnimal; // Supondremos que se ingresa el ID del cliente
    @FXML private Button btnAgregarAnimal;
    @FXML private TextArea lblResultadoAnimal;

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

        try {
            java.util.List<Animal> todosLosAnimales = ctrlAnimal.obtenerTodosLosAnimales();
            StringBuilder resultado = new StringBuilder("Animales encontrados con nombre '" + nombre + "':\n\n");

            if (todosLosAnimales.isEmpty()) {
                resultado.append("No hay animales registrados en la base de datos.");
                lblResultadoBusquedaAnimal.setText(resultado.toString());
                return;
            }

            boolean encontrado = false;
            for (Animal animal : todosLosAnimales) {
                if (animal != null && animal.getNombre() != null && 
                    animal.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    resultado.append("- ").append(animal.toString()).append("\n");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                resultado.append("Ningún animal encontrado con ese nombre.");
            }

            lblResultadoBusquedaAnimal.setText(resultado.toString());
        } catch (Exception e) {
            lblResultadoBusquedaAnimal.setText("Error al buscar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscarAnimalPorChip() {
        String chip = txtBuscarAnimalChip.getText().trim();
        if (chip.isEmpty()) {
            mostrarAlerta("Buscar Animal", "Por favor, introduce el número de chip para buscar.");
            return;
        }

        try {
            Animal animal = ctrlAnimal.buscarAnimalPorChip(chip);
            if (animal != null) {
                StringBuilder resultado = new StringBuilder("Animal encontrado:\n\n");
                resultado.append("- Nombre: ").append(animal.getNombre()).append("\n");
                resultado.append("- Chip: ").append(animal.getNumeroChip()).append("\n");
                resultado.append("- Tipo: ").append(animal.getTipo()).append("\n");
                resultado.append("- Fecha de Nacimiento: ").append(animal.getFechaNacimiento()).append("\n");
                if (animal.getDueno() != null) {
                    resultado.append("- Dueño: ").append(animal.getDueno().getNombre()).append(" ").append(animal.getDueno().getApellido());
                }
                lblResultadoBusquedaAnimal.setText(resultado.toString());
            } else {
                lblResultadoBusquedaAnimal.setText("No se encontró ningún animal con el chip: " + chip);
            }
        } catch (Exception e) {
            lblResultadoBusquedaAnimal.setText("Error al buscar: " + e.getMessage());
            e.printStackTrace();
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

        try {
            java.util.List<Cliente> todosLosClientes = ctrlCliente.obtenerTodosLosClientes();
            StringBuilder resultado = new StringBuilder("Clientes encontrados con nombre '" + nombre + "':\n\n");

            if (todosLosClientes.isEmpty()) {
                resultado.append("No hay clientes registrados en la base de datos.");
                lblResultadoBusquedaCliente.setText(resultado.toString());
                return;
            }

            boolean encontrado = false;
            for (Cliente cliente : todosLosClientes) {
                if (cliente != null && cliente.getNombre() != null && cliente.getApellido() != null &&
                    (cliente.getNombre() + " " + cliente.getApellido()).toLowerCase().contains(nombre.toLowerCase())) {
                    resultado.append("- ID: ").append(cliente.getId_cliente()).append("\n");
                    resultado.append("  Nombre: ").append(cliente.getNombre()).append(" ").append(cliente.getApellido()).append("\n");
                    resultado.append("  Teléfono: ").append(cliente.getTelefono() != null ? cliente.getTelefono() : "N/A").append("\n");
                    resultado.append("  Correo: ").append(cliente.getCorreo() != null ? cliente.getCorreo() : "N/A").append("\n\n");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                resultado.append("Ningún cliente encontrado con ese nombre.");
            }

            lblResultadoBusquedaCliente.setText(resultado.toString());
        } catch (Exception e) {
            lblResultadoBusquedaCliente.setText("Error al buscar: " + e.getMessage());
            e.printStackTrace();
        }
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
            lblResultadoCliente.setText("Cliente agregado exitosamente: " + nuevoCliente.getNombre() + " " + nuevoCliente.getApellido());
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
        nuevoAnimal.setDueno(dueño); // Asignar el dueño encontrado

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