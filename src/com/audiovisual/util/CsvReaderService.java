package com.audiovisual.util;

import com.audiovisual.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderService {

    public static List<ContenidoAudiovisual> leerContenidos(Path ruta) {
        List<ContenidoAudiovisual> lista = new ArrayList<>();
        if (ruta == null) {
            System.err.println("La ruta del archivo no puede ser nula.");
            return lista;
        }

        if (!Files.exists(ruta)) {
            System.err.println("El archivo no existe en la ruta: " + ruta.toAbsolutePath());
            return lista;
        }

        try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String cabecera = reader.readLine(); // Leer cabecera
            if (cabecera == null) {
                System.err.println("El archivo CSV está vacío.");
                return lista;
            }

            String linea;
            int numeroLinea = 1;
            int maxId = -1;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }

                try {
                    ContenidoAudiovisual contenido = parsearLinea(linea, numeroLinea);
                    if (contenido != null) {
                        lista.add(contenido);
                        if (contenido.getId() > maxId) {
                            maxId = contenido.getId();
                        }
                    }
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    System.err.println("Error de formato en línea " + numeroLinea + ": " + e.getMessage() + ". Línea omitida.");
                } catch (Exception e) {
                    System.err.println("Error inesperado al procesar línea " + numeroLinea + ": " + e.getMessage() + ". Línea omitida.");
                }
            }

            // Ajustar el contador estático para evitar colisiones con IDs cargados
            if (maxId >= 0) {
                ContenidoAudiovisual.setContar(maxId + 1);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Error de seguridad al acceder al archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de Entrada/Salida al leer el archivo: " + e.getMessage());
        }

        return lista;
    }

    private static ContenidoAudiovisual parsearLinea(String linea, int numeroLinea) {
        // Separamos por punto y coma (;) pero teniendo cuidado de no separar por punto y coma escapados (\;)
        // Una expresión regular simple para dividir por ';' excluyendo '\;' es: (?<!\\);
        String[] columnas = linea.split("(?<!\\\\);", -1);

        if (columnas.length < 9) {
            throw new IllegalArgumentException("Número de columnas incorrecto. Se esperaban 9, se encontraron " + columnas.length);
        }

        // Limpiar escapes en las columnas básicas
        int id = parseInteger(columnas[0], "ID", numeroLinea);
        String tipoStr = columnas[1].trim().toUpperCase();
        String titulo = unescapeCsv(columnas[2]);
        int duracion = parseInteger(columnas[3], "Duración", numeroLinea);
        String genero = unescapeCsv(columnas[4]);

        if (titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser un entero positivo");
        }
        if (genero.isBlank()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }

        String extra1 = unescapeCsv(columnas[5]);
        String extra2 = unescapeCsv(columnas[6]);
        String extra3 = unescapeCsv(columnas[7]);
        String subElementos = columnas[8];

        ContenidoAudiovisual c;

        switch (tipoStr) {
            case "PELICULA" -> {
                String estudio = extra1;
                if (estudio.isBlank()) {
                    throw new IllegalArgumentException("El estudio de la película no puede estar vacío");
                }
                Pelicula p = new Pelicula(titulo, duracion, genero, estudio);
                p.setId(id);
                
                if (!subElementos.isBlank()) {
                    String[] actoresToken = subElementos.split("(?<!\\\\)\\|");
                    for (String token : actoresToken) {
                        if (token.isBlank()) continue;
                        String[] nombreApellido = token.split("(?<!\\\\):", -1);
                        if (nombreApellido.length == 2) {
                            String nom = unescapeSubElement(nombreApellido[0]);
                            String ape = unescapeSubElement(nombreApellido[1]);
                            p.agregarActor(new Actor(nom, ape));
                        }
                    }
                }
                c = p;
            }
            case "SERIETV" -> {
                SerieDeTV s = new SerieDeTV(titulo, duracion, genero);
                s.setId(id);

                if (!subElementos.isBlank()) {
                    String[] tempTokens = subElementos.split("(?<!\\\\)\\|");
                    for (String token : tempTokens) {
                        if (token.isBlank()) continue;
                        String[] numEps = token.split("(?<!\\\\):", -1);
                        if (numEps.length == 2) {
                            int num = parseInteger(numEps[0], "Número de Temporada", numeroLinea);
                            int eps = parseInteger(numEps[1], "Episodios de Temporada", numeroLinea);
                            s.agregarTemporada(new Temporada(num, eps));
                        }
                    }
                }
                c = s;
            }
            case "DOCUMENTAL" -> {
                String tema = extra1;
                if (tema.isBlank()) {
                    throw new IllegalArgumentException("El tema del documental no puede estar vacío");
                }
                Documental d = new Documental(titulo, duracion, genero, tema);
                d.setId(id);

                if (!extra2.isBlank()) {
                    String[] nomEsp = extra2.split("(?<!\\\\):", -1);
                    if (nomEsp.length == 2) {
                        String nom = unescapeSubElement(nomEsp[0]);
                        String esp = unescapeSubElement(nomEsp[1]);
                        d.setInvestigador(new Investigador(nom, esp));
                    }
                }
                c = d;
            }
            case "WEBINAR" -> {
                Investigador conferencista = null;
                if (!extra1.isBlank()) {
                    String[] nomEsp = extra1.split("(?<!\\\\):", -1);
                    if (nomEsp.length == 2) {
                        String nom = unescapeSubElement(nomEsp[0]);
                        String esp = unescapeSubElement(nomEsp[1]);
                        conferencista = new Investigador(nom, esp);
                    }
                }
                if (conferencista == null) {
                    throw new IllegalArgumentException("El conferencista del webinar es obligatorio y está incompleto");
                }
                int cupos = parseInteger(extra2, "Cupos del Webinar", numeroLinea);
                String fecha = extra3;
                if (fecha.isBlank()) {
                    throw new IllegalArgumentException("La fecha programada del webinar no puede estar vacía");
                }

                Webinar w = new Webinar(titulo, duracion, genero, conferencista, cupos, fecha);
                w.setId(id);
                c = w;
            }
            case "ANUNCIO" -> {
                String cliente = extra1;
                if (cliente.isBlank()) {
                    throw new IllegalArgumentException("El cliente del anuncio no puede estar vacío");
                }
                double presupuesto = parseDouble(extra2, "Presupuesto del Anuncio", numeroLinea);
                String publico = extra3;
                if (publico.isBlank()) {
                    throw new IllegalArgumentException("El público objetivo del anuncio no puede estar vacío");
                }

                AnuncioPublicitario a = new AnuncioPublicitario(titulo, duracion, genero, cliente, presupuesto, publico);
                a.setId(id);

                if (!subElementos.isBlank()) {
                    String[] actoresToken = subElementos.split("(?<!\\\\)\\|");
                    for (String token : actoresToken) {
                        if (token.isBlank()) continue;
                        String[] nombreApellido = token.split("(?<!\\\\):", -1);
                        if (nombreApellido.length == 2) {
                            String nom = unescapeSubElement(nombreApellido[0]);
                            String ape = unescapeSubElement(nombreApellido[1]);
                            a.agregarActor(new Actor(nom, ape));
                        }
                    }
                }
                c = a;
            }
            default -> throw new IllegalArgumentException("Tipo de contenido desconocido: " + tipoStr);
        }

        return c;
    }

    private static int parseInteger(String val, String nombreCampo, int numeroLinea) {
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser un número entero válido. Encontrado: '" + val + "'");
        }
    }

    private static double parseDouble(String val, String nombreCampo, int numeroLinea) {
        try {
            return Double.parseDouble(val.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser un número decimal válido. Encontrado: '" + val + "'");
        }
    }

    private static String unescapeCsv(String value) {
        if (value == null) return "";
        return value.replace("\\;", ";");
    }

    private static String unescapeSubElement(String value) {
        if (value == null) return "";
        return value.replace("\\|", "|").replace("\\:", ":").replace("\\;", ";");
    }
}
