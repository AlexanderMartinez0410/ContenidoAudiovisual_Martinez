package com.audiovisual.model;

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
    public List<Actor> getActores() { return actores; }

    @Override
    public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Película ---\n");
        sb.append("ID: ").append(getId()).append(" | Título: ").append(getTitulo()).append("\n");
        sb.append("Duración: ").append(getDuracionEnMinutos()).append(" min | Género: ").append(getGenero()).append("\n");
        sb.append("Estudio: ").append(estudio).append("\n");
        if (!actores.isEmpty()) {
            sb.append("Actores: ").append(actores).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
