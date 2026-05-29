package com.audiovisual.app;

import com.audiovisual.controller.AudiovisualController;
import com.audiovisual.repository.CsvContentRepository;
import com.audiovisual.repository.IContentRepository;
import com.audiovisual.view.ConsoleView;
import com.audiovisual.view.IView;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // Inicializar los componentes principales del patrón MVC
        IContentRepository repository = new CsvContentRepository(Paths.get("data/contenidos.csv"));
        IView view = new ConsoleView();
        
        // Coordinar todo a través del controlador
        AudiovisualController controller = new AudiovisualController(repository, view);
        controller.iniciar();
    }
}
