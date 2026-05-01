package com.audiovisual.models.serietv;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.temporada.Temporada;
import java.util.List;
import java.util.Scanner;

public class SerieTVManager {
    public static void crear(List<ContenidoAudiovisual> contenidos, Scanner scanner) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración promedio (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();

            SerieDeTV s = new SerieDeTV(titulo, duracion, genero);
            
            System.out.print("¿Desea agregar una temporada? (si/no): ");
            if (scanner.nextLine().equalsIgnoreCase("si")) {
                System.out.print("Número de temporada: ");
                int num = Integer.parseInt(scanner.nextLine());
                System.out.print("Número de episodios: ");
                int eps = Integer.parseInt(scanner.nextLine());
                s.agregarTemporada(new Temporada(num, eps));
            }

            contenidos.add(s);
            System.out.println("¡Serie de TV creada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear serie.");
        }
    }

    public static void editar(SerieDeTV s, Scanner scanner) {
        System.out.println("\n--- Gestionar Temporadas para: " + s.getTitulo() + " ---");
        System.out.println("1. Agregar Temporada");
        System.out.println("2. Editar Temporada existente");
        System.out.println("3. Eliminar Temporada");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            List<Temporada> temps = s.getTemporadas();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Número de temporada: ");
                    int num = Integer.parseInt(scanner.nextLine());
                    System.out.print("Número de episodios: ");
                    int eps = Integer.parseInt(scanner.nextLine());
                    s.agregarTemporada(new Temporada(num, eps));
                    System.out.println("¡Temporada agregada!");
                }
                case 2 -> {
                    if (temps.isEmpty()) {
                        System.out.println("No hay temporadas registradas.");
                        return;
                    }
                    for (int i = 0; i < temps.size(); i++) {
                        System.out.println(i + ". " + temps.get(i));
                    }
                    System.out.print("Índice a editar: ");
                    int idx = Integer.parseInt(scanner.nextLine());
                    if (idx >= 0 && idx < temps.size()) {
                        Temporada t = temps.get(idx);
                        System.out.print("Nuevo número (actual " + t.getNumero() + "): ");
                        String n = scanner.nextLine();
                        if (!n.isBlank()) t.setNumero(Integer.parseInt(n));
                        
                        System.out.print("Nuevos episodios (actual " + t.getEpisodios() + "): ");
                        String e = scanner.nextLine();
                        if (!e.isBlank()) t.setEpisodios(Integer.parseInt(e));
                        System.out.println("¡Temporada actualizada!");
                    }
                }
                case 3 -> {
                    if (temps.isEmpty()) {
                        System.out.println("No hay temporadas registradas.");
                        return;
                    }
                    for (int i = 0; i < temps.size(); i++) {
                        System.out.println(i + ". " + temps.get(i));
                    }
                    System.out.print("Índice a eliminar: ");
                    int idx = Integer.parseInt(scanner.nextLine());
                    if (idx >= 0 && idx < temps.size()) {
                        temps.remove(idx);
                        System.out.println("¡Temporada eliminada!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar la edición.");
        }
    }
}
