package com.audiovisual.models.pelicula;

public class Actor {
    private String nombre;
    private String apellido;

    public Actor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String toString() { return nombre + " " + apellido; }
}
