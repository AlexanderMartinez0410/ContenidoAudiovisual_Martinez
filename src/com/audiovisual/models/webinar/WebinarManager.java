package com.audiovisual.models.webinar;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.investigador.Investigador;
import java.util.List;
import java.util.Scanner;

public class WebinarManager {
    public static void crear(List<ContenidoAudiovisual> contenidos, Scanner scanner) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            
            System.out.print("Conferencista (Nombre): ");
            String conf = scanner.nextLine();
            System.out.print("Especialidad: ");
            String esp = scanner.nextLine();
            
            System.out.print("Cupos: ");
            int cupos = Integer.parseInt(scanner.nextLine());
            System.out.print("Fecha (AAAA-MM-DD): ");
            String fecha = scanner.nextLine();

            Webinar w = new Webinar(titulo, duracion, genero, new Investigador(conf, esp), cupos, fecha);
            contenidos.add(w);
            System.out.println("¡Webinar creado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear webinar.");
        }
    }

    public static void editar(Webinar w, Scanner scanner) {
        System.out.print("Nueva Fecha (enter para omitir): ");
        String fecha = scanner.nextLine();
        if (!fecha.isBlank()) {
            w.setFechaProgramada(fecha);
        }
    }
}
