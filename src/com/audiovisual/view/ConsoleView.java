package com.audiovisual.view;

import com.audiovisual.model.ContenidoAudiovisual;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements IView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("     SISTEMA DE GESTIÓN AUDIOVISUAL ⚡");
        System.out.println("==========================================");
        System.out.println("  1. Ver Menús (Tipos de Contenido) 📂");
        System.out.println("  2. Ver Todo 🔍");
        System.out.println("  0. Salir ❌");
        System.out.println("==========================================");
        System.out.print("👉 Seleccione una opción: ");
        return leerEntero();
    }

    @Override
    public int mostrarMenuTipos() {
        System.out.println("\n==========================================");
        System.out.println("       SELECCIONE TIPO DE CONTENIDO 📁");
        System.out.println("==========================================");
        System.out.println("  1. Películas 🎬");
        System.out.println("  2. Series de TV 📺");
        System.out.println("  3. Documentales 📄");
        System.out.println("  4. Webinars 🎓");
        System.out.println("  5. Anuncios Publicitarios 📢");
        System.out.println("==========================================");
        System.out.print("👉 Seleccione: ");
        return leerEntero();
    }

    @Override
    public int mostrarMenuAcciones() {
        System.out.println("\n==========================================");
        System.out.println("            ¿QUÉ DESEA HACER? 🤔");
        System.out.println("==========================================");
        System.out.println("  1. Crear ✨");
        System.out.println("  2. Editar 📝");
        System.out.println("  3. Eliminar 🗑️");
        System.out.println("==========================================");
        System.out.print("👉 Opción: ");
        return leerEntero();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("✨ INFO: " + mensaje);
    }

    @Override
    public void mostrarError(String error) {
        System.out.println("❌ ERROR: " + error);
    }

    @Override
    public void mostrarLista(List<ContenidoAudiovisual> lista, String titulo) {
        System.out.println("\n==================================================");
        System.out.println(" 📋 " + titulo.toUpperCase());
        System.out.println("==================================================");
        if (lista == null || lista.isEmpty()) {
            System.out.println("   No hay registros para mostrar.");
        } else {
            for (ContenidoAudiovisual c : lista) {
                System.out.print(c.obtenerDetalles());
            }
        }
        System.out.println("==================================================");
    }

    @Override
    public void mostrarDetalles(ContenidoAudiovisual c) {
        if (c != null) {
            System.out.println("\n================ Detalles del Contenido ================");
            System.out.print(c.obtenerDetalles());
            System.out.println("========================================================");
        } else {
            System.out.println("❌ Contenido nulo.");
        }
    }

    @Override
    public String pedirCadena(String campo) {
        System.out.print("🔹 " + campo + ": ");
        return scanner.nextLine().trim();
    }

    @Override
    public int pedirEntero(String campo) {
        System.out.print("🔹 " + campo + ": ");
        return leerEntero();
    }

    @Override
    public double pedirDouble(String campo) {
        System.out.print("🔹 " + campo + ": ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    @Override
    public boolean pedirConfirmacion(String pregunta) {
        System.out.print("❓ " + pregunta + " (si/no): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("si");
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
