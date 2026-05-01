package com.audiovisual.models.serietv;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.temporada.Temporada;
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
    public void mostrarDetalles() {
        System.out.println("--- Serie de TV ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Temporadas: " + temporadas.size());
        for (Temporada t : temporadas) System.out.println("  " + t);
        System.out.println();
    }
}
