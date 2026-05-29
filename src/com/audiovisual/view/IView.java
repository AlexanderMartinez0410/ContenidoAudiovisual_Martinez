package com.audiovisual.view;

import com.audiovisual.model.ContenidoAudiovisual;
import java.util.List;

public interface IView {
    int mostrarMenuPrincipal();
    int mostrarMenuTipos();
    int mostrarMenuAcciones();
    
    void mostrarMensaje(String mensaje);
    void mostrarError(String error);
    void mostrarLista(List<ContenidoAudiovisual> lista, String titulo);
    void mostrarDetalles(ContenidoAudiovisual c);

    String pedirCadena(String campo);
    int pedirEntero(String campo);
    double pedirDouble(String campo);
    boolean pedirConfirmacion(String pregunta);
}
