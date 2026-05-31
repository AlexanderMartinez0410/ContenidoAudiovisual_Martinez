package com.audiovisual.util;

import com.audiovisual.model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriterService {

    public static void escribirContenidos(Path ruta, List<ContenidoAudiovisual> contenidos) throws IOException {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta del archivo no puede ser nula.");
        }
        if (contenidos == null) {
            throw new IllegalArgumentException("La lista de contenidos no puede ser nula.");
        }

        // Crear directorios padres si no existen
        Path parent = ruta.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            // Escribir cabecera
            writer.write("id;tipo;titulo;duracion;genero;extra1;extra2;extra3;subElementos");
            writer.newLine();

            for (ContenidoAudiovisual c : contenidos) {
                if (c == null) continue;
                String linea = serializarContenido(c);
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    private static String serializarContenido(ContenidoAudiovisual c) {
        int id = c.getId();
        String titulo = escapeCsv(c.getTitulo());
        int duracion = c.getDuracionEnMinutos();
        String genero = escapeCsv(c.getGenero());

        String tipo = "";
        String extra1 = "";
        String extra2 = "";
        String extra3 = "";
        String subElementos = "";

        if (c instanceof Pelicula p) {
            tipo = "PELICULA";
            extra1 = escapeCsv(p.getEstudio());
            if (p.getActores() != null) {
                subElementos = p.getActores().stream()
                        .map(a -> escapeSubElement(a.getNombre()) + ":" + escapeSubElement(a.getApellido()))
                        .collect(Collectors.joining("|"));
            }
        } else if (c instanceof SerieDeTV s) {
            tipo = "SERIETV";
            if (s.getTemporadas() != null) {
                subElementos = s.getTemporadas().stream()
                        .map(t -> t.getNumero() + ":" + t.getEpisodios())
                        .collect(Collectors.joining("|"));
            }
        } else if (c instanceof Documental d) {
            tipo = "DOCUMENTAL";
            extra1 = escapeCsv(d.getTema());
            if (d.getInvestigador() != null) {
                extra2 = escapeSubElement(d.getInvestigador().getNombre()) + ":" + escapeSubElement(d.getInvestigador().getEspecialidad());
            }
        } else if (c instanceof Webinar w) {
            tipo = "WEBINAR";
            if (w.getConferencista() != null) {
                extra1 = escapeSubElement(w.getConferencista().getNombre()) + ":" + escapeSubElement(w.getConferencista().getEspecialidad());
            }
            extra2 = String.valueOf(w.getCuposDisponibles());
            extra3 = escapeCsv(w.getFechaProgramada());
        } else if (c instanceof AnuncioPublicitario a) {
            tipo = "ANUNCIO";
            extra1 = escapeCsv(a.getCliente());
            extra2 = String.valueOf(a.getPresupuesto());
            extra3 = escapeCsv(a.getPublicoObjetivo());
            if (a.getActores() != null) {
                subElementos = a.getActores().stream()
                        .map(act -> escapeSubElement(act.getNombre()) + ":" + escapeSubElement(act.getApellido()))
                        .collect(Collectors.joining("|"));
            }
        }

        return String.join(";", 
                String.valueOf(id), 
                tipo, 
                titulo, 
                String.valueOf(duracion), 
                genero, 
                extra1, 
                extra2, 
                extra3, 
                subElementos
        );
    }

    private static String escapeCsv(String value) {
        if (value == null) return "";
        return value.replace(";", "\\;").replace("\n", " ").replace("\r", "");
    }

    private static String escapeSubElement(String value) {
        if (value == null) return "";
        return value.replace("|", "\\|").replace(":", "\\:").replace(";", "\\;");
    }
}
