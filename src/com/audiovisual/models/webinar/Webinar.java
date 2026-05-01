package com.audiovisual.models.webinar;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.investigador.Investigador;

public class Webinar extends ContenidoAudiovisual {
    private Investigador conferencista;
    private int cuposDisponibles;
    private String fechaProgramada;

    public Webinar(String titulo, int duracionEnMinutos, String genero, Investigador conferencista, int cuposDisponibles, String fechaProgramada) {
        super(titulo, duracionEnMinutos, genero);
        this.conferencista = conferencista;
        this.cuposDisponibles = cuposDisponibles;
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(String fechaProgramada) { this.fechaProgramada = fechaProgramada; }

    public void registrarAsistente() {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            System.out.println("Asistente registrado.");
        } else {
            System.out.println("Sin cupos.");
        }
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("--- Webinar ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Conferencista: " + conferencista);
        System.out.println("Cupos: " + cuposDisponibles + " | Fecha: " + fechaProgramada);
        System.out.println();
    }
}
