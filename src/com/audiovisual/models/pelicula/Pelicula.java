package com.audiovisual.models.pelicula;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.actor.Actor;
import java.util.ArrayList;
import java.util.List;

public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private List<Actor> actores;

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;
        this.actores = new ArrayList<>();
    }

    public String getEstudio() { return estudio; }
    public void setEstudio(String estudio) { this.estudio = estudio; }

    public void agregarActor(Actor actor) { actores.add(actor); }

    @Override
    public void mostrarDetalles() {
        System.out.println("--- Película ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " min | Género: " + getGenero());
        System.out.println("Estudio: " + estudio);
        if (!actores.isEmpty()) System.out.println("Actores: " + actores);
        System.out.println();
    }
}
