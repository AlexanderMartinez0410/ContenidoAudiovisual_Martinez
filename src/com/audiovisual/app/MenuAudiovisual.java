package com.audiovisual.app;

import com.audiovisual.core.ContenidoAudiovisual;
import com.audiovisual.models.pelicula.*;
import com.audiovisual.models.serietv.*;
import com.audiovisual.models.documental.*;
import com.audiovisual.models.webinar.Webinar;
import com.audiovisual.models.anuncio.AnuncioPublicitario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuAudiovisual {
    private static List<ContenidoAudiovisual> contenidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Carga inicial de ejemplos para todas las clases
        cargarEjemplos();

        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE GESTIÓN AUDIOVISUAL");
            System.out.println("========================================");
            System.out.println("1. Ver Menús (Tipos de Contenido)");
            System.out.println("2. Ver Todo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> verMenus();
                case 2 -> listarTodo();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void cargarEjemplos() {
        Pelicula p = new Pelicula("Avatar", 162, "Acción", "Lightstorm");
        p.agregarActor(new Actor("Sam", "Worthington"));
        contenidos.add(p);

        SerieDeTV s = new SerieDeTV("Breaking Bad", 45, "Drama");
        s.agregarTemporada(new Temporada(1, 7));
        contenidos.add(s);

        Documental d = new Documental("Nuestro Planeta", 50, "Naturaleza", "Vida Silvestre");
        d.setInvestigador(new Investigador("David", "Attenborough"));
        contenidos.add(d);

        Webinar w = new Webinar("Java para Expertos", 120, "Educación", new Investigador("Dr. James", "Arquitectura"), 50, "2026-07-20");
        contenidos.add(w);

        AnuncioPublicitario a = new AnuncioPublicitario("Coca-Cola Refresh", 1, "Comercial", "Coca-Cola Co.", 100000.0, "Global");
        contenidos.add(a);
    }

    private static void verMenus() {
        System.out.println("\n--- Seleccione Tipo de Contenido ---");
        System.out.println("1. Películas");
        System.out.println("2. Series de TV");
        System.out.println("3. Documentales");
        System.out.println("4. Webinars");
        System.out.println("5. Anuncios Publicitarios");
        System.out.print("Seleccione: ");
        int tipo;
        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Entrada no válida.");
            return;
        }

        mostrarContenidoPorTipo(tipo);

        System.out.print("\n¿Desea realizar algún cambio? (si/no): ");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("si")) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Crear");
            System.out.println("2. Editar");
            System.out.println("3. Eliminar");
            System.out.print("Opción: ");
            int accion;
            try {
                accion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Acción no válida.");
                return;
            }

            switch (accion) {
                case 1 -> menuCrear(tipo);
                case 2 -> menuEditar();
                case 3 -> menuEliminar();
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarContenidoPorTipo(int tipo) {
        System.out.println("\n--- Datos del Contenido Seleccionado ---");
        boolean encontrado = false;
        for (ContenidoAudiovisual c : contenidos) {
            boolean coincide = switch (tipo) {
                case 1 -> c instanceof Pelicula;
                case 2 -> c instanceof SerieDeTV;
                case 3 -> c instanceof Documental;
                case 4 -> c instanceof Webinar;
                case 5 -> c instanceof AnuncioPublicitario;
                default -> false;
            };
            if (coincide) {
                c.mostrarDetalles();
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay registros para este tipo.");
    }

    private static void menuCrear(int tipo) {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Duración (min): ");
            int duracion = Integer.parseInt(scanner.nextLine());
            System.out.print("Género: ");
            String genero = scanner.nextLine();

            switch (tipo) {
                case 1 -> {
                    System.out.print("Estudio: ");
                    String estudio = scanner.nextLine();
                    contenidos.add(new Pelicula(titulo, duracion, genero, estudio));
                }
                case 2 -> contenidos.add(new SerieDeTV(titulo, duracion, genero));
                case 3 -> {
                    System.out.print("Tema: ");
                    String tema = scanner.nextLine();
                    contenidos.add(new Documental(titulo, duracion, genero, tema));
                }
                case 4 -> {
                    System.out.print("Conferencista: ");
                    String conf = scanner.nextLine();
                    System.out.print("Especialidad: ");
                    String esp = scanner.nextLine();
                    System.out.print("Cupos: ");
                    int cupos = Integer.parseInt(scanner.nextLine());
                    System.out.print("Fecha: ");
                    String fecha = scanner.nextLine();
                    contenidos.add(new Webinar(titulo, duracion, genero, new Investigador(conf, esp), cupos, fecha));
                }
                case 5 -> {
                    System.out.print("Cliente: ");
                    String cliente = scanner.nextLine();
                    System.out.print("Presupuesto: ");
                    double pre = Double.parseDouble(scanner.nextLine());
                    System.out.print("Público: ");
                    String pub = scanner.nextLine();
                    contenidos.add(new AnuncioPublicitario(titulo, duracion, genero, cliente, pre, pub));
                }
            }
            System.out.println("¡Creado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear. Revise los datos.");
        }
    }

    private static void menuEditar() {
        try {
            System.out.print("Ingrese ID del objeto a editar: ");
            int id = Integer.parseInt(scanner.nextLine());
            ContenidoAudiovisual c = buscarPorId(id);
            if (c != null) {
                System.out.print("Nuevo Título (enter para omitir): ");
                String nt = scanner.nextLine();
                if (!nt.isBlank()) c.setTitulo(nt);
                System.out.print("Nuevo Género (enter para omitir): ");
                String ng = scanner.nextLine();
                if (!ng.isBlank()) c.setGenero(ng);
                System.out.println("Editado con éxito.");
            } else {
                System.out.println("ID no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al editar.");
        }
    }

    private static void menuEliminar() {
        try {
            System.out.print("Ingrese ID del objeto a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            ContenidoAudiovisual c = buscarPorId(id);
            if (c != null) {
                contenidos.remove(c);
                System.out.println("Eliminado con éxito.");
            } else {
                System.out.println("ID no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar.");
        }
    }

    private static ContenidoAudiovisual buscarPorId(int id) {
        return contenidos.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    private static void listarTodo() {
        System.out.println("\n--- MOSTRANDO TODO EL CONTENIDO ---");
        if (contenidos.isEmpty()) {
            System.out.println("No hay nada que mostrar.");
        } else {
            for (ContenidoAudiovisual c : contenidos) {
                c.mostrarDetalles();
            }
        }
    }


}
