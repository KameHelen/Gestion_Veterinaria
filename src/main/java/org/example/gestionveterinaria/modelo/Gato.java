package org.example.gestionveterinaria.modelo;

public class Gato extends Animal {

    public Gato (String numero_chip, String nombre, String fecha_nacimiento, Cliente dueno) {
        super (numero_chip, nombre, fecha_nacimiento, dueno);
    }


    @Override
    public String getTipo() {
        return "Gato";
    }

}
