package com.example.backend.dao;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import javax.persistence.PersistenceException;
import java.util.Optional;

public class ThumbnailDAO extends GenericDAO<Thumbnail>{

    public Optional<Thumbnail> create(final int id, final byte[] content, final String extension, final Image image) {
        try {
            save(new Thumbnail(id, content, extension, image));
            return findById(id);
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
