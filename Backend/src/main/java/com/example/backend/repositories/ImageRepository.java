package com.example.backend.repositories;

import com.example.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("SELECT i FROM Image i WHERE i NOT IN (SELECT t.image FROM Thumbnail t)")
    Collection<Image> findNotProcessedImages();
}
