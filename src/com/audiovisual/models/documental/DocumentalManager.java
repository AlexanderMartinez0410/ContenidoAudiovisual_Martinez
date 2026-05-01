package com.audiovisual.models.documental;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.investigador.Investigador;
import java.util.List;
import java.util.Scanner;

public class DocumentalManager {
    public static void crear(List<ContenidoAudiovisual> contenidos, Scanner scanner) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            System.out.print("Tema: ");
            String tema = scanner.nextLine();

            Documental d = new Documental(titulo, duracion, genero, tema);
            
            System.out.print("¿Desea agregar un investigador? (si/no): ");
            if (scanner.nextLine().equalsIgnoreCase("si")) {
                System.out.print("Nombre: ");
                String n = scanner.nextLine();
                System.out.print("Especialidad: ");
                String e = scanner.nextLine();
                d.setInvestigador(new Investigador(n, e));
            }

            contenidos.add(d);
            System.out.println("¡Documental creado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear documental.");
        }
    }

    public static void editar(Documental d, Scanner scanner) {
        System.out.print("Nuevo Tema (enter para omitir): ");
        String tema = scanner.nextLine();
        if (!tema.isBlank()) {
            d.setTema(tema);
        }
    }
}
