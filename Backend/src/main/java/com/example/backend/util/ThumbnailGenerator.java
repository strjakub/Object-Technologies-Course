package com.example.backend.util;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
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

        BufferedImage outImage = Scalr.resize(inputImage, width);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outImage, image.getExtension(), outputStream);
        return outputStream.toByteArray();
    }

}
