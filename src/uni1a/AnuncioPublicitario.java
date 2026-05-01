package uni1a;

public class AnuncioPublicitario extends ContenidoAudiovisual {
    private String cliente;
    private double presupuesto;
    private String publicoObjetivo;

    public AnuncioPublicitario(String titulo, int duracionEnMinutos, String genero, String cliente, double presupuesto, String publicoObjetivo) {
        super(titulo, duracionEnMinutos, genero);
        this.cliente = cliente;
        this.presupuesto = presupuesto;
        this.publicoObjetivo = publicoObjetivo;
    }

    public String getCliente() {
        return cliente;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public String getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public double calcularCostoPorSegundo() {
        if (getDuracionEnMinutos() > 0) {
            return presupuesto / (getDuracionEnMinutos() * 60.0);
        }
        return 0;
    }

    public void verificarDuracionLegal() {
        if (getDuracionEnMinutos() > 1) {
            System.out.println("Advertencia: El anuncio '" + getTitulo() + "' excede la duración legal de 60 segundos.");
        } else {
            System.out.println("El anuncio '" + getTitulo() + "' cumple con la normativa de duración.");
        }
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Anuncio:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " min");
        System.out.println("Cliente: " + cliente);
        System.out.println("Presupuesto: $" + presupuesto);
        System.out.println("Público: " + publicoObjetivo);
        System.out.printf("Costo por segundo: $%.2f%n", calcularCostoPorSegundo());
        System.out.println();
    }
}
