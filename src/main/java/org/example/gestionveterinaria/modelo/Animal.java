package org.example.gestionveterinaria.modelo;

public abstract class Animal {

    //      ATRIBUTOS DEL MODELO
    private String numeroChip;        // Identificador único del animal
    private String nombre;            // Nombre del animal
    private String fechaNacimiento;   // Fecha de nacimiento (String o LocalDate)
    private Cliente dueno;            // Relación: un animal pertenece a un cliente


    //   CONSTRUCTOR COMPLETO
    //   Se usa cuando se crea un animal con todos sus datos
    public Animal(String numeroChip, String nombre, String fechaNacimiento, Cliente dueno) {
        this.numeroChip = numeroChip;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.dueno = dueno;
    }

    //         GETTERS / SETTERS
    public String getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(String numeroChip) {
        this.numeroChip = numeroChip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Cliente getDueno() {
        return dueno;
    }

    public void setDueno(Cliente dueno) {
        this.dueno = dueno;
    }

    //     REPRESENTACIÓN TEXTO

    @Override
    public String toString() {
        return nombre + " (Chip: " + numeroChip + ")";
    }
}