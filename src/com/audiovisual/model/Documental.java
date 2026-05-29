package com.audiovisual.model;

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
    public Investigador getInvestigador() { return investigador; }

    @Override
    public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Documental ---\n");
        sb.append("ID: ").append(getId()).append(" | Título: ").append(getTitulo()).append("\n");
        sb.append("Tema: ").append(tema).append("\n");
        if (investigador != null) {
            sb.append("Investigador: ").append(investigador).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
