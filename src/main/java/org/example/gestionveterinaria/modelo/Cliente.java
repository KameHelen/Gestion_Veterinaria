package org.example.gestionveterinaria.modelo;



import java.util.ArrayList;
import java.util.List;


public class Cliente {
    private int id_cliente;
        public int getId_cliente() {
            return id_cliente;
        }

        public void setId_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
        }
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private List<Animal> animales;



// Constructor vacío
public Cliente () {
    this.animales = new ArrayList<>();
}

// Constructor con parámetros
public Cliente(String nombre, String apellido, String telefono, String correo) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.correo = correo;
    this.animales = new ArrayList<>();
}


public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getApellido() {
    return apellido;
}

public void setApellido(String apellido) {
    this.apellido = apellido;
}

public String getTelefono() {
    return telefono;
}

public void setTelefono(String telefono) {
    this.telefono = telefono;
}

public String getCorreo() {
    return correo;
}

public void setCorreo(String correo) {
    this.correo = correo;
}

public List<Animal> getAnimales() {
    return animales;
}


// Añadir animal a la lista del cliente
public void agregarAnimal(Animal animal) {
    animales.add(animal);
}

// Eliminar animal del cliente
public void eliminarAnimal(Animal animal) {
    animales.remove(animal);
}



@Override
public String toString() {
    return nombre + " " + apellido + " (" + telefono + ")";
}
}





