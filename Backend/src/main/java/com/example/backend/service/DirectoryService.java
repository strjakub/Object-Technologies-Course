package com.example.backend.service;

import com.example.backend.model.Directory;
import com.example.backend.repositories.DirectoryRepository;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;


    public DirectoryService(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    public Single<Integer> uploadDirectory(Directory directory) {
        return Single.fromCallable(() -> directoryRepository.save(directory).getId());
    }

}
