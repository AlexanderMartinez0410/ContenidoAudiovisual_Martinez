package poo;
import uni1a.*;

public class PruebaAudioVisual {
	public static void main(String[] args) {
        System.out.println("Hello from Eclipse!");

        // Crear instancias de las subclases
        Pelicula pelicula = new Pelicula("Avatar", 125, "Accion", "20th Century Studios");
        pelicula.agregarActor(new Actor("Sam", "Worthington"));
        pelicula.agregarActor(new Actor("Zoe", "Saldana"));

        SerieDeTV serie = new SerieDeTV("Game of Thrones", 60, "Fantasy");
        serie.agregarTemporada(new Temporada(1, 10));
        serie.agregarTemporada(new Temporada(2, 10));

        Documental documental = new Documental("Cosmos", 45, "Science", "Astronomy");
        documental.setInvestigador(new Investigador("Carl", "Sagan"));

        Webinar webinar = new Webinar("Java Avanzado", 90, "Educación", new Investigador("Dr. Java", "Software Architecture"), 2, "2026-06-15");
        webinar.registrarAsistente(); // Quedan 1

        AnuncioPublicitario anuncio = new AnuncioPublicitario("Super Promo", 1, "Comercial", "TechCorp", 5000.0, "Jóvenes");

        ContenidoAudiovisual[] contenidos = new ContenidoAudiovisual[5];
        contenidos[0] = pelicula;
        contenidos[1] = serie;
        contenidos[2] = documental;
        contenidos[3] = webinar;
        contenidos[4] = anuncio;



        // Mostrar los detalles de cada contenido audiovisual
        for (ContenidoAudiovisual contenido : contenidos) {
            contenido.mostrarDetalles();
        }
    }
}