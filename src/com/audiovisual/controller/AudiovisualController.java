package com.audiovisual.controller;

import com.audiovisual.model.*;
import com.audiovisual.repository.IContentRepository;
import com.audiovisual.view.IView;

import java.util.List;
import java.util.stream.Collectors;

public class AudiovisualController {
    private final IContentRepository repository;
    private final IView view;

    public AudiovisualController(IContentRepository repository, IView view) {
        this.repository = repository;
        this.view = view;
    }

    public void iniciar() {
        repository.loadAll();
        
        if (repository.findAll().isEmpty()) {
            cargarEjemplos();
            repository.saveAll();
        }

        int opcion;
        do {
            opcion = view.mostrarMenuPrincipal();
            switch (opcion) {
                case 1 -> verMenus();
                case 2 -> listarTodo();
                case 0 -> view.mostrarMensaje("Saliendo del programa...");
                default -> view.mostrarError("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void cargarEjemplos() {
        Pelicula p = new Pelicula("Avatar", 162, "Acción", "Lightstorm");
        p.agregarActor(new Actor("Sam", "Worthington"));
        repository.save(p);

        SerieDeTV s = new SerieDeTV("Breaking Bad", 45, "Drama");
        s.agregarTemporada(new Temporada(1, 7));
        repository.save(s);

        Documental d = new Documental("Nuestro Planeta", 50, "Naturaleza", "Vida Silvestre");
        d.setInvestigador(new Investigador("David", "Attenborough"));
        repository.save(d);

        Webinar w = new Webinar("Java para Expertos", 120, "Educación", new Investigador("Dr. James", "Arquitectura"), 50, "2026-07-20");
        repository.save(w);

        AnuncioPublicitario a = new AnuncioPublicitario("Coca-Cola Refresh", 1, "Comercial", "Coca-Cola Co.", 100000.0, "Global");
        a.agregarActor(new Actor("Lionel", "Messi"));
        repository.save(a);
    }

    private void verMenus() {
        int tipo = view.mostrarMenuTipos();
        if (tipo < 1 || tipo > 5) {
            view.mostrarError("Tipo de contenido no válido.");
            return;
        }

        mostrarContenidoPorTipo(tipo);

        if (view.pedirConfirmacion("¿Desea realizar algún cambio?")) {
            int accion = view.mostrarMenuAcciones();
            switch (accion) {
                case 1 -> crearContenido(tipo);
                case 2 -> editarContenido();
                case 3 -> eliminarContenido();
                default -> view.mostrarError("Opción no válida.");
            }
        }
    }

    private void mostrarContenidoPorTipo(int tipo) {
        List<ContenidoAudiovisual> filtrado = repository.findAll().stream()
                .filter(c -> coincideTipo(c, tipo))
                .collect(Collectors.toList());

        String tipoNombre = obtenerNombreTipo(tipo);
        view.mostrarLista(filtrado, "Datos del Contenido: " + tipoNombre);
    }

    private boolean coincideTipo(ContenidoAudiovisual c, int tipo) {
        return switch (tipo) {
            case 1 -> c instanceof Pelicula;
            case 2 -> c instanceof SerieDeTV;
            case 3 -> c instanceof Documental;
            case 4 -> c instanceof Webinar;
            case 5 -> c instanceof AnuncioPublicitario;
            default -> false;
        };
    }

    private String obtenerNombreTipo(int tipo) {
        return switch (tipo) {
            case 1 -> "Películas";
            case 2 -> "Series de TV";
            case 3 -> "Documentales";
            case 4 -> "Webinars";
            case 5 -> "Anuncios Publicitarios";
            default -> "Desconocido";
        };
    }

    private void listarTodo() {
        view.mostrarLista(repository.findAll(), "MOSTRANDO TODO EL CONTENIDO");
    }

    private void crearContenido(int tipo) {
        try {
            String titulo = view.pedirCadena("Título");
            if (titulo.isBlank()) {
                view.mostrarError("El título no puede estar vacío.");
                return;
            }

            int duracion = view.pedirEntero("Duración (min)");
            if (duracion <= 0) {
                view.mostrarError("La duración debe ser un entero positivo.");
                return;
            }

            String genero = view.pedirCadena("Género");
            if (genero.isBlank()) {
                view.mostrarError("El género no puede estar vacío.");
                return;
            }

            ContenidoAudiovisual nuevo = switch (tipo) {
                case 1 -> crearPelicula(titulo, duracion, genero);
                case 2 -> crearSerieDeTV(titulo, duracion, genero);
                case 3 -> crearDocumental(titulo, duracion, genero);
                case 4 -> crearWebinar(titulo, duracion, genero);
                case 5 -> crearAnuncioPublicitario(titulo, duracion, genero);
                default -> null;
            };

            if (nuevo != null) {
                repository.save(nuevo);
                repository.saveAll();
                view.mostrarMensaje("¡Contenido creado con éxito!");
            }
        } catch (Exception e) {
            view.mostrarError("Ocurrió un error inesperado al crear: " + e.getMessage());
        }
    }

    private Pelicula crearPelicula(String titulo, int duracion, String genero) {
        String estudio = view.pedirCadena("Estudio");
        if (estudio.isBlank()) {
            view.mostrarError("El estudio no puede estar vacío.");
            return null;
        }
        Pelicula p = new Pelicula(titulo, duracion, genero, estudio);
        if (view.pedirConfirmacion("¿Desea agregar un actor?")) {
            String nom = view.pedirCadena("Nombre del actor");
            String ape = view.pedirCadena("Apellido del actor");
            if (!nom.isBlank() && !ape.isBlank()) {
                p.agregarActor(new Actor(nom, ape));
            }
        }
        return p;
    }

    private SerieDeTV crearSerieDeTV(String titulo, int duracion, String genero) {
        SerieDeTV s = new SerieDeTV(titulo, duracion, genero);
        if (view.pedirConfirmacion("¿Desea agregar una temporada?")) {
            int num = view.pedirEntero("Número de temporada");
            int eps = view.pedirEntero("Cantidad de episodios");
            if (num > 0 && eps > 0) {
                s.agregarTemporada(new Temporada(num, eps));
            }
        }
        return s;
    }

    private Documental crearDocumental(String titulo, int duracion, String genero) {
        String tema = view.pedirCadena("Tema");
        if (tema.isBlank()) {
            view.mostrarError("El tema no puede estar vacío.");
            return null;
        }
        Documental d = new Documental(titulo, duracion, genero, tema);
        if (view.pedirConfirmacion("¿Desea agregar un investigador?")) {
            String nom = view.pedirCadena("Nombre del investigador");
            String esp = view.pedirCadena("Especialidad");
            if (!nom.isBlank() && !esp.isBlank()) {
                d.setInvestigador(new Investigador(nom, esp));
            }
        }
        return d;
    }

    private Webinar crearWebinar(String titulo, int duracion, String genero) {
        String nomConferencista = view.pedirCadena("Nombre del Conferencista");
        String espConferencista = view.pedirCadena("Especialidad del Conferencista");
        if (nomConferencista.isBlank() || espConferencista.isBlank()) {
            view.mostrarError("Los datos del conferencista son requeridos.");
            return null;
        }
        int cupos = view.pedirEntero("Cupos disponibles");
        if (cupos < 0) {
            view.mostrarError("Los cupos no pueden ser negativos.");
            return null;
        }
        String fecha = view.pedirCadena("Fecha programada (AAAA-MM-DD)");
        if (fecha.isBlank()) {
            view.mostrarError("La fecha no puede estar vacía.");
            return null;
        }
        return new Webinar(titulo, duracion, genero, new Investigador(nomConferencista, espConferencista), cupos, fecha);
    }

    private AnuncioPublicitario crearAnuncioPublicitario(String titulo, int duracion, String genero) {
        String cliente = view.pedirCadena("Cliente");
        if (cliente.isBlank()) {
            view.mostrarError("El cliente no puede estar vacío.");
            return null;
        }
        double presupuesto = view.pedirDouble("Presupuesto");
        if (presupuesto < 0) {
            view.mostrarError("El presupuesto no puede ser negativo.");
            return null;
        }
        String publico = view.pedirCadena("Público Objetivo");
        if (publico.isBlank()) {
            view.mostrarError("El público objetivo no puede estar vacío.");
            return null;
        }
        AnuncioPublicitario a = new AnuncioPublicitario(titulo, duracion, genero, cliente, presupuesto, publico);
        if (view.pedirConfirmacion("¿Desea agregar un actor para el anuncio?")) {
            String nom = view.pedirCadena("Nombre del actor");
            String ape = view.pedirCadena("Apellido del actor");
            if (!nom.isBlank() && !ape.isBlank()) {
                a.agregarActor(new Actor(nom, ape));
            }
        }
        return a;
    }

    private void editarContenido() {
        int id = view.pedirEntero("Ingrese ID del objeto a editar");
        ContenidoAudiovisual c = repository.findById(id);
        if (c == null) {
            view.mostrarError("ID no encontrado.");
            return;
        }

        try {
            String nt = view.pedirCadena("Nuevo Título (enter para omitir)");
            if (!nt.isBlank()) c.setTitulo(nt);

            int nd = view.pedirEntero("Nueva Duración (minutos, -1 o 0 para omitir)");
            if (nd > 0) c.setDuracionEnMinutos(nd);

            String ng = view.pedirCadena("Nuevo Género (enter para omitir)");
            if (!ng.isBlank()) c.setGenero(ng);

            editarDetallesEspecificos(c);

            repository.save(c);
            repository.saveAll();
            view.mostrarMensaje("¡Editado con éxito!");
        } catch (Exception e) {
            view.mostrarError("Error al editar: " + e.getMessage());
        }
    }

    private void editarDetallesEspecificos(ContenidoAudiovisual c) {
        if (c instanceof Pelicula p) {
            String estudio = view.pedirCadena("Nuevo Estudio (enter para omitir)");
            if (!estudio.isBlank()) p.setEstudio(estudio);
        } else if (c instanceof Documental d) {
            String tema = view.pedirCadena("Nuevo Tema (enter para omitir)");
            if (!tema.isBlank()) d.setTema(tema);
        } else if (c instanceof Webinar w) {
            String fecha = view.pedirCadena("Nueva Fecha (AAAA-MM-DD, enter para omitir)");
            if (!fecha.isBlank()) w.setFechaProgramada(fecha);
        } else if (c instanceof AnuncioPublicitario a) {
            String cliente = view.pedirCadena("Nuevo Cliente (enter para omitir)");
            if (!cliente.isBlank()) a.setCliente(cliente);
        }
    }

    private void eliminarContenido() {
        int id = view.pedirEntero("Ingrese ID del objeto a eliminar");
        ContenidoAudiovisual c = repository.findById(id);
        if (c != null) {
            repository.deleteById(id);
            repository.saveAll();
            view.mostrarMensaje("¡Eliminado con éxito!");
        } else {
            view.mostrarError("ID no encontrado.");
        }
    }
}
