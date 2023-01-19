package com.example.backend.model;

import java.util.Collection;

public record DirectoryContents(Collection<Thumbnail> thumbnails, Collection<String> directories) {}
