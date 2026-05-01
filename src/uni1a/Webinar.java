package uni1a;

public class Webinar extends ContenidoAudiovisual {
    private Investigador conferencista;
    private int cuposDisponibles;
    private String fechaProgramada;

    public Webinar(String titulo, int duracionEnMinutos, String genero, Investigador conferencista, int cuposDisponibles, String fechaProgramada) {
        super(titulo, duracionEnMinutos, genero);
        this.conferencista = conferencista;
        this.cuposDisponibles = Math.max(0, cuposDisponibles);
        this.fechaProgramada = fechaProgramada;
    }

    public Investigador getConferencista() {
        return conferencista;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void registrarAsistente() {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            System.out.println("Asistente registrado en: " + getTitulo());
        } else {
            System.out.println("Lo sentimos, no hay cupos para: " + getTitulo());
        }
    }

    public void iniciarSesionPreguntas() {
        System.out.println("Iniciando sesión de preguntas para el webinar: " + getTitulo());
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Webinar:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " min");
        System.out.println("Género: " + getGenero());
        System.out.println("Conferencista: " + conferencista);
        System.out.println("Cupos disponibles: " + cuposDisponibles);
        System.out.println("Fecha: " + fechaProgramada);
        System.out.println();
    }
}
