package org.example.gestionveterinaria.modelo;

public class Perro extends Animal {

    public Perro (String numero_chip, String nombre, String fecha_nacimiento, Cliente dueno) {
        super (numero_chip, nombre, fecha_nacimiento, dueno);
    }


    @Override
    public String getTipo() {
        return "Perro";
    }

}
