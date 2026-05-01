package com.audiovisual.models.documental;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.investigador.Investigador;

public class Documental extends ContenidoAudiovisual {
    private String tema;
    private Investigador investigador;

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema;
    }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public void setInvestigador(Investigador investigador) { this.investigador = investigador; }

    @Override
    public void mostrarDetalles() {
        System.out.println("--- Documental ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Tema: " + tema);
        if (investigador != null) System.out.println("Investigador: " + investigador);
        System.out.println();
    }
}
