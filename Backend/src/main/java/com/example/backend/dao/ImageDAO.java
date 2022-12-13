package com.example.backend.dao;

import com.example.backend.model.Image;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Component
public class ImageDAO extends GenericDAO<Image>{

    public Optional<Image> create(final byte[] content, final String extension) {
        try {
            Image image = new Image(content, extension);
            save(image);
            return Optional.of(image);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Image> findById(final int id) {
        try {
            Image image = currentSession()
                    .createQuery("SELECT i FROM Image i WHERE i.id = :id", Image.class)
                    .setParameter("id", id).getSingleResult();
            return Optional.of(image);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
