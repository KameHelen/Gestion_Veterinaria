package org.example.gestionveterinaria.modelo;

public class Gato extends Animal {
	public Gato(String numeroChip, String nombre, String fechaNacimiento, Cliente dueno) {
		super(numeroChip, nombre, fechaNacimiento, dueno);
	}
	public String getTipo() {
		return "Gato";
	}
}
