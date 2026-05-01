package com.audiovisual.models.anuncio;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.actor.Actor;
import java.util.ArrayList;
import java.util.List;

public class AnuncioPublicitario extends ContenidoAudiovisual {
    private String cliente;
    private double presupuesto;
    private String publicoObjetivo;
    private List<Actor> actores;

    public AnuncioPublicitario(String titulo, int duracionEnMinutos, String genero, String cliente, double presupuesto, String publicoObjetivo) {
        super(titulo, duracionEnMinutos, genero);
        this.cliente = cliente;
        this.presupuesto = presupuesto;
        this.publicoObjetivo = publicoObjetivo;
        this.actores = new ArrayList<>();
    }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }

    public double calcularCostoPorSegundo() {
        return presupuesto / (getDuracionEnMinutos() * 60.0);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("--- Anuncio Publicitario ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Cliente: " + cliente + " | Presupuesto: $" + presupuesto);
        if (!actores.isEmpty()) {
            System.out.println("Actores: " + actores);
        }
        System.out.printf("Costo/seg: $%.2f%n", calcularCostoPorSegundo());
        System.out.println();
    }
}
