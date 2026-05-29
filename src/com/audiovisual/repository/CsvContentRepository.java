package com.audiovisual.repository;

import com.audiovisual.model.ContenidoAudiovisual;
import com.audiovisual.util.CsvReaderService;
import com.audiovisual.util.CsvWriterService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvContentRepository implements IContentRepository {
    private final Path fileRoute;
    private final List<ContenidoAudiovisual> cache;

    public CsvContentRepository(Path fileRoute) {
        this.fileRoute = fileRoute;
        this.cache = new ArrayList<>();
    }

    @Override
    public List<ContenidoAudiovisual> findAll() {
        return new ArrayList<>(cache);
    }

    @Override
    public ContenidoAudiovisual findById(int id) {
        return cache.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(ContenidoAudiovisual contenido) {
        if (contenido == null) return;
        
        int index = -1;
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == contenido.getId()) {
                index = i;
                break;
            }
        }
        
        if (index >= 0) {
            cache.set(index, contenido);
        } else {
            cache.add(contenido);
        }
    }

    @Override
    public void deleteById(int id) {
        cache.removeIf(c -> c.getId() == id);
    }

    @Override
    public void saveAll() {
        try {
            CsvWriterService.escribirContenidos(fileRoute, cache);
        } catch (IOException e) {
            System.err.println("Error al persistir datos a CSV: " + e.getMessage());
        }
    }

    @Override
    public void loadAll() {
        cache.clear();
        List<ContenidoAudiovisual> cargados = CsvReaderService.leerContenidos(fileRoute);
        cache.addAll(cargados);
    }
}
