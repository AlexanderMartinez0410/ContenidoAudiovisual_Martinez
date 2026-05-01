package com.audiovisual.models.anuncio;

import com.audiovisual.core.ContenidoAudiovisual;

public class AnuncioPublicitario extends ContenidoAudiovisual {
    private String cliente;
    private double presupuesto;
    private String publicoObjetivo;

    public AnuncioPublicitario(String titulo, int duracionEnMinutos, String genero, String cliente, double presupuesto, String publicoObjetivo) {
        super(titulo, duracionEnMinutos, genero);
        this.cliente = cliente;
        this.presupuesto = presupuesto;
        this.publicoObjetivo = publicoObjetivo;
    }

    public double calcularCostoPorSegundo() {
        return presupuesto / (getDuracionEnMinutos() * 60.0);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("--- Anuncio Publicitario ---");
        System.out.println("ID: " + getId() + " | Título: " + getTitulo());
        System.out.println("Cliente: " + cliente + " | Presupuesto: $" + presupuesto);
        System.out.printf("Costo/seg: $%.2f%n", calcularCostoPorSegundo());
        System.out.println();
    }
}
