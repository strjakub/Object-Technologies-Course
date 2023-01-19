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

    public byte[] convertToThumbnail(Image image, Size size) throws IOException {
        log.info(String.format("...Converting photo... : original size = %s, target size = %s", image.getData().length, size));
        var result = resize(image, size);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(String.format("...Converted photo... : id = %s, original size = %s, target size = %s", image.getId(),
                image.getData().length, size));
        return result;
    }

    private byte[] resize(Image image, Size size) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(image.getData());
        BufferedImage inputImage = ImageIO.read(inputStream);
        BufferedImage outImage = Scalr.resize(inputImage, size.getWidth(), size.getHeight());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outImage, image.getExtension(), outputStream);
        return outputStream.toByteArray();
    }

}
