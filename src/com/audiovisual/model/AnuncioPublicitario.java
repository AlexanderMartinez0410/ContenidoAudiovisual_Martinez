package com.audiovisual.model;

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

    public double getPresupuesto() { return presupuesto; }
    public String getPublicoObjetivo() { return publicoObjetivo; }
    public List<Actor> getActores() { return actores; }

    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }

    public double calcularCostoPorSegundo() {
        return presupuesto / (getDuracionEnMinutos() * 60.0);
    }

    @Override
    public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Anuncio Publicitario ---\n");
        sb.append("ID: ").append(getId()).append(" | Título: ").append(getTitulo()).append("\n");
        sb.append("Cliente: ").append(cliente).append(" | Presupuesto: $").append(presupuesto).append("\n");
        if (!actores.isEmpty()) {
            sb.append("Actores: ").append(actores).append("\n");
        }
        sb.append(String.format("Costo/seg: $%.2f%n", calcularCostoPorSegundo()));
        sb.append("\n");
        return sb.toString();
    }
}
