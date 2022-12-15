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
        log.info("...Converting photo... : " + image.getData().length);
        return resize(image);
    }

    private byte[] resize(Image image) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(image.getData());
        BufferedImage inputImage = ImageIO.read(inputStream);

        BufferedImage outImage = Scalr.resize(inputImage, width);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outImage, image.getExtension(), outputStream);
        log.error(image.getExtension());
        return outputStream.toByteArray();
    }

}
