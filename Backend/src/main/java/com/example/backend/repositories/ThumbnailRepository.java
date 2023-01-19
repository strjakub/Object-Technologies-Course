package com.example.backend.repositories;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Integer> {

    @Query("SELECT t FROM Thumbnail t WHERE t.image = ?1")
    Optional<Thumbnail> findByImage_Id(Image img);

    @Query("SELECT t FROM Thumbnail t WHERE t.large IS NOT NULL AND t.medium IS NOT NULL AND t.small IS NOT NULL AND t.path = ?1")
    Collection<Thumbnail> findAllThumbnailsByPath(String path);
    @Query("SELECT DISTINCT case when substring(t.path, ?2 + 2) LIKE '%/%' then substring(substring(t.path, ?2 + 2), 1, instr(substring(t.path, ?2 + 2), '/') - 1) else substring(t.path, ?2 + 2) end FROM Thumbnail t WHERE t.large IS NOT NULL AND t.medium IS NOT NULL AND t.small IS NOT NULL AND t.path LIKE ?1% AND length(t.path) > ?2")
    Collection<String> findDirectories(String path, int pathLength);

}
