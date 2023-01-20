package com.example.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
public class ByteReader {


    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000))
    public byte[] readFile(Path path) throws IOException {
        log.info("TRY TO READ FILE");
        return Files.readAllBytes(path);
    }
}
