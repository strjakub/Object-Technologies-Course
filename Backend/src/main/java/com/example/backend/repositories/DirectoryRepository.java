package com.example.backend.repositories;

import com.example.backend.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Integer> {

    Collection<Directory> findByPathStartsWith(String path);

}
