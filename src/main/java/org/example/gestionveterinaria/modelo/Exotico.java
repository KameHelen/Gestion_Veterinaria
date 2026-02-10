package org.example.gestionveterinaria.modelo;

public class Exotico extends Animal {
	public Exotico(String numeroChip, String nombre, String fechaNacimiento, Cliente dueno) {
		super(numeroChip, nombre, fechaNacimiento, dueno);
	}
	public String getTipo() {
		return "Exotico";
	}
}
