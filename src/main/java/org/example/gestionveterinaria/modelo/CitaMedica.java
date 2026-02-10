package org.example.gestionveterinaria.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaMedica {


    //      ATRIBUTOS DEL MODELO


    private int id;                     // ID único de la cita (si usáis BD)
    private LocalDate fecha;            // Día de la cita
    private LocalTime hora;             // Hora de inicio de la cita
    private Animal animal;              // Animal atendido
    private Cliente cliente;            // Dueño del animal
    private Veterinario veterinario;    // Veterinario asignado
    private String motivo;              // Motivo de la consulta (opcional)



    //   CONSTRUCTOR COMPLETO
    //   Se usa cuando la cita ya existe en la BD

    public CitaMedica(int id, LocalDate fecha, LocalTime hora,
                      Animal animal, Cliente cliente,
                      Veterinario veterinario, String motivo) {

        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.animal = animal;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.motivo = motivo;
    }



    //   CONSTRUCTOR SIN ID
    //   Se usa cuando la cita se crea antes de guardarla

    public CitaMedica(LocalDate fecha, LocalTime hora,
                      Animal animal, Cliente cliente,
                      Veterinario veterinario, String motivo) {

        this.fecha = fecha;
        this.hora = hora;
        this.animal = animal;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.motivo = motivo;
    }



    //         GETTERS / SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    //     REPRESENTACIÓN TEXTO
    @Override
    public String toString() {
        return "Cita: " + fecha + " " + hora + " - " +
                animal.getNombre() + " con " +
                veterinario.getNombre();
    }
}