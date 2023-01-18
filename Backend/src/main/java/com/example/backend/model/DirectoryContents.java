package com.example.backend.model;

import java.util.Collection;
import java.util.List;

public record DirectoryContents(Collection<Thumbnail> thumbnails, List<String> directories) {}
