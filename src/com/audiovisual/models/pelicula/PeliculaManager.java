package com.audiovisual.models.pelicula;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.actor.Actor;
import java.util.List;
import java.util.Scanner;

public class PeliculaManager {
    public static void crear(List<ContenidoAudiovisual> contenidos, Scanner scanner) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            System.out.print("Estudio: ");
            String estudio = scanner.nextLine();

            Pelicula p = new Pelicula(titulo, duracion, genero, estudio);
            
            System.out.print("¿Desea agregar un actor? (si/no): ");
            if (scanner.nextLine().equalsIgnoreCase("si")) {
                System.out.print("Nombre: ");
                String n = scanner.nextLine();
                System.out.print("Apellido: ");
                String a = scanner.nextLine();
                p.agregarActor(new Actor(n, a));
            }

            contenidos.add(p);
            System.out.println("¡Película creada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear película.");
        }
    }

    public static void editar(Pelicula p, Scanner scanner) {
        System.out.print("Nuevo Estudio (enter para omitir): ");
        String estudio = scanner.nextLine();
        if (!estudio.isBlank()) {
            p.setEstudio(estudio);
        }
    }
}
