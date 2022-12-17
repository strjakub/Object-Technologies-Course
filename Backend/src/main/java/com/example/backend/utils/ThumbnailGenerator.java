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
import java.util.Random;

@Component
@Slf4j
public class ThumbnailGenerator {

    private static final int WIDTH = 300;

    private static final int HEIGHT = 300;

    private final Random random = new Random();

    public byte[] convertToThumbnail(Image image) throws IOException {
        log.info(String.format("...Converting photo... : size = %s", image.getData().length));
        var result = resize(image);
        log.info(String.format("...Converted photo... : id = %s, size = %s", image.getId(), image.getData().length));
        return result;
    }

    private byte[] resize(Image image) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(image.getData());
        BufferedImage inputImage = ImageIO.read(inputStream);
        BufferedImage outImage = Scalr.resize(inputImage, WIDTH, HEIGHT);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outImage, image.getExtension(), outputStream);

        try {
            var time = random.nextInt(2, 10) * 1000;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

}
