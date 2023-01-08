package com.example.backend.model;

import lombok.AllArgsConstructor;

import java.util.Optional;

public class ThumbnailOption {

    private final Thumbnail data;

    private ThumbnailState state;

    public ThumbnailOption(Thumbnail data, Optional<Image> img) {
        this.data = data;
        // this.state = img.isEmpty().map(r -> );
    }
}
