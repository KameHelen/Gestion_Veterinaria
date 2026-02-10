package org.example.gestionveterinaria.modelo;

public class Perro extends Animal {
	public Perro(String numeroChip, String nombre, String fechaNacimiento, Cliente dueno) {
		super(numeroChip, nombre, fechaNacimiento, dueno);
	}
	public String getTipo() {
		return "Perro";
	}
}
