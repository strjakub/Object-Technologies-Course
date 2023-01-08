package com.example.backend.service;

import com.example.backend.repositories.ImageRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RestartService {

    private final ImageRepository imageRepository;

    public RestartService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void restart(){
//        System.out.println(imageRepository.findNotProcessedImages());
    }

}
