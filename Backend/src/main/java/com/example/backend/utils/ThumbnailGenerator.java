package com.example.backend.utils;

import com.example.backend.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class ThumbnailGenerator {

    private final int width = 300;

    private final int height = 300;

    public byte[] convertToThumbnail(Image image) throws IOException {
        log.info(String.format("...Converting photo... : size = %s", image.getData().length));
        var result = resize(image);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info(String.format("...Converted photo... : id = %s, size = %s", image.getId(), image.getData().length));
        return result;
    }

    private byte[] resize(Image image) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(image.getData());
        BufferedImage inputImage = ImageIO.read(inputStream);

        BufferedImage outImage = Scalr.resize(inputImage, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outImage, image.getExtension(), outputStream);
        return outputStream.toByteArray();
    }

}
