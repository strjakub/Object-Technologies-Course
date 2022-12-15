package com.example.backend.dao;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Component
public class ThumbnailDAO extends GenericDAO<Thumbnail>{

    public Optional<Thumbnail> create(final byte[] content, final String extension, final Image image) {
        try {
            Thumbnail thumbnail = new Thumbnail(content, extension, image);
            save(thumbnail);
            image.setThumbnail(thumbnail);
            return Optional.of(thumbnail);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Thumbnail> findById(final int id) {
        try {
            Thumbnail thumbnail = currentSession()
                    .createQuery("SELECT t FROM Thumbnail t WHERE t.id = :id", Thumbnail.class)
                    .setParameter("id", id).getSingleResult();
            return Optional.of(thumbnail);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
