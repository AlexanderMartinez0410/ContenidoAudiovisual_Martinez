package com.audiovisual.repository;

import com.audiovisual.model.ContenidoAudiovisual;
import java.util.List;

public interface IContentRepository {
    List<ContenidoAudiovisual> findAll();
    ContenidoAudiovisual findById(int id);
    void save(ContenidoAudiovisual contenido);
    void deleteById(int id);
    void saveAll();
    void loadAll();
}
