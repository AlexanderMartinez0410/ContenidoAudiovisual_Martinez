package com.audiovisual.models.anuncio;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.actor.Actor;
import java.util.List;
import java.util.Scanner;

public class AnuncioManager {
    public static void crear(List<ContenidoAudiovisual> contenidos, Scanner scanner) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            
            System.out.print("Cliente: ");
            String cliente = scanner.nextLine();
            System.out.print("Presupuesto: ");
            double pre = Double.parseDouble(scanner.nextLine());
            System.out.print("Público Objetivo: ");
            String pub = scanner.nextLine();

            AnuncioPublicitario a = new AnuncioPublicitario(titulo, duracion, genero, cliente, pre, pub);
            
            System.out.print("¿Desea agregar un actor? (si/no): ");
            if (scanner.nextLine().equalsIgnoreCase("si")) {
                System.out.print("Nombre: ");
                String n = scanner.nextLine();
                System.out.print("Apellido: ");
                String ap = scanner.nextLine();
                a.agregarActor(new Actor(n, ap));
            }

            contenidos.add(a);
            System.out.println("¡Anuncio creado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear anuncio.");
        }
    }

    public static void editar(AnuncioPublicitario a, Scanner scanner) {
        System.out.print("Nuevo Cliente (enter para omitir): ");
        String cli = scanner.nextLine();
        if (!cli.isBlank()) {
            a.setCliente(cli);
        }
    }
}
