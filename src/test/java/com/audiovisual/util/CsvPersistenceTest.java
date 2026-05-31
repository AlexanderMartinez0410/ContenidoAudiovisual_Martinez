package com.audiovisual.util;

import com.audiovisual.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvPersistenceTest {

    @TempDir
    Path tempDir;

    private Path csvFile;

    @BeforeEach
    void setUp() {
        csvFile = tempDir.resolve("contenidos.csv");
        // Reiniciar el contador estático para las pruebas
        ContenidoAudiovisual.setContar(0);
    }

    @Test
    void testFlujoCompletoPersistencia() throws IOException {
        // 1. Crear contenidos iniciales
        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

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
        a.agregarActor(new Actor("Lionel", "Messi"));
        contenidos.add(a);

        // 2. Escribir a CSV
        CsvWriterService.escribirContenidos(csvFile, contenidos);
        assertTrue(Files.exists(csvFile));

        // 3. Leer desde CSV
        List<ContenidoAudiovisual> cargados = CsvReaderService.leerContenidos(csvFile);
        assertEquals(5, cargados.size());

        // Verificar integridad de tipos y datos
        assertTrue(cargados.get(0) instanceof Pelicula);
        Pelicula pCargada = (Pelicula) cargados.get(0);
        assertEquals("Avatar", pCargada.getTitulo());
        assertEquals("Lightstorm", pCargada.getEstudio());
        assertEquals(1, pCargada.getActores().size());
        assertEquals("Sam", pCargada.getActores().get(0).getNombre());

        assertTrue(cargados.get(1) instanceof SerieDeTV);
        SerieDeTV sCargada = (SerieDeTV) cargados.get(1);
        assertEquals("Breaking Bad", sCargada.getTitulo());
        assertEquals(1, sCargada.getTemporadas().size());
        assertEquals(7, sCargada.getTemporadas().get(0).getEpisodios());

        assertTrue(cargados.get(2) instanceof Documental);
        Documental dCargada = (Documental) cargados.get(2);
        assertEquals("Nuestro Planeta", dCargada.getTitulo());
        assertNotNull(dCargada.getInvestigador());
        assertEquals("David", dCargada.getInvestigador().getNombre());

        assertTrue(cargados.get(3) instanceof Webinar);
        Webinar wCargado = (Webinar) cargados.get(3);
        assertEquals("Java para Expertos", wCargado.getTitulo());
        assertEquals(50, wCargado.getCuposDisponibles());

        assertTrue(cargados.get(4) instanceof AnuncioPublicitario);
        AnuncioPublicitario aCargado = (AnuncioPublicitario) cargados.get(4);
        assertEquals("Coca-Cola Co.", aCargado.getCliente());
        assertEquals(100000.0, aCargado.getPresupuesto());

        // 4. Modificar datos y re-escribir
        pCargada.setTitulo("Avatar Remastered");
        pCargada.setEstudio("Disney Studio");
        pCargada.agregarActor(new Actor("Zoe", "Saldana"));

        sCargada.agregarTemporada(new Temporada(2, 13));

        CsvWriterService.escribirContenidos(csvFile, cargados);

        // 5. Leer de nuevo y validar integridad de modificaciones
        List<ContenidoAudiovisual> cargados2 = CsvReaderService.leerContenidos(csvFile);
        assertEquals(5, cargados2.size());

        Pelicula pModificada = (Pelicula) cargados2.get(0);
        assertEquals("Avatar Remastered", pModificada.getTitulo());
        assertEquals("Disney Studio", pModificada.getEstudio());
        assertEquals(2, pModificada.getActores().size());

        SerieDeTV sModificada = (SerieDeTV) cargados2.get(1);
        assertEquals(2, sModificada.getTemporadas().size());
        assertEquals(2, sModificada.getTemporadas().get(1).getNumero());
        assertEquals(13, sModificada.getTemporadas().get(1).getEpisodios());
    }

    @Test
    void testValidacionFormatosIncorrectos() throws IOException {
        // Crear un archivo CSV con líneas malformadas
        List<String> lineas = List.of(
                "id;tipo;titulo;duracion;genero;extra1;extra2;extra3;subElementos", // Cabecera
                "0;PELICULA;Peli Invalida;no_un_numero;Accion;Estudio;;;",           // Duración inválida
                "1;PELICULA;;120;Accion;Estudio;;;",                                  // Título vacío
                "2;WEBINAR;Java Webinar;100;Educacion;;no_cupos;2026-01-01;",          // Cupos no numéricos
                "3;ANUNCIO;Promo;30;Comercial;Cliente;no_presupuesto;Publico;",       // Presupuesto no numérico
                "4;TIPO_INEXISTENTE;Titulo;60;Genero;;;;",                            // Tipo inválido
                "5;PELICULA;Peli Valida;90;Drama;Universal;;;"                        // Válida
        );

        Files.write(csvFile, lineas);

        List<ContenidoAudiovisual> cargados = CsvReaderService.leerContenidos(csvFile);
        // Solo la línea con ID 5 debería cargarse, el resto fallará validación y se omitirá elegantemente
        assertEquals(1, cargados.size());
        assertEquals(5, cargados.get(0).getId());
        assertEquals("Peli Valida", cargados.get(0).getTitulo());
    }

    @Test
    void testArchivoNoExiste() {
        Path rutaInexistente = tempDir.resolve("inexistente.csv");
        List<ContenidoAudiovisual> cargados = CsvReaderService.leerContenidos(rutaInexistente);
        assertTrue(cargados.isEmpty());
    }
}
