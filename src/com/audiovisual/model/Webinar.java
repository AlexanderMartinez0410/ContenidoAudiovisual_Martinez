package com.audiovisual.model;

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

    public Investigador getConferencista() { return conferencista; }
    public int getCuposDisponibles() { return cuposDisponibles; }

    public boolean registrarAsistente() {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            return true;
        }
        return false;
    }

    @Override
    public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Webinar ---\n");
        sb.append("ID: ").append(getId()).append(" | Título: ").append(getTitulo()).append("\n");
        sb.append("Conferencista: ").append(conferencista).append("\n");
        sb.append("Cupos: ").append(cuposDisponibles).append(" | Fecha: ").append(fechaProgramada).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
