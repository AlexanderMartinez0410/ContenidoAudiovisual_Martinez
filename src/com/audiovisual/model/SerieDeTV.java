package com.audiovisual.model;

import java.util.ArrayList;
import java.util.List;

public class SerieDeTV extends ContenidoAudiovisual {
    private List<Temporada> temporadas;

    public SerieDeTV(String titulo, int duracionEnMinutos, String genero) {
        super(titulo, duracionEnMinutos, genero);
        this.temporadas = new ArrayList<>();
    }

    public void agregarTemporada(Temporada temporada) { temporadas.add(temporada); }
    public List<Temporada> getTemporadas() { return temporadas; }

    @Override
    public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Serie de TV ---\n");
        sb.append("ID: ").append(getId()).append(" | Título: ").append(getTitulo()).append("\n");
        sb.append("Temporadas: ").append(temporadas.size()).append("\n");
        for (Temporada t : temporadas) {
            sb.append("  ").append(t).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
