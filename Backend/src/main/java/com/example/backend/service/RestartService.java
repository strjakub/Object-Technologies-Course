package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.utils.Size;
import com.example.backend.utils.ThumbnailGenerator;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RestartService {

    private final ImageRepository imageRepository;
    private final ThumbnailService thumbnailService;

    public RestartService(ImageRepository imageRepository, ThumbnailService thumbnailService) {
        this.imageRepository = imageRepository;
        this.thumbnailService = thumbnailService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void restart(){
        for(Image i : imageRepository.findNotProcessedToSmallImages())
            thumbnailService.generateThumbnail(i);
        for(Image i : imageRepository.findNotProcessedToMediumImages())
            thumbnailService.generateThumbnail(i, false, true, true);
        for(Image i : imageRepository.findNotProcessedToLargeImages())
            thumbnailService.generateThumbnail(i, false, false, true);
    }

}