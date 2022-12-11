package com.example.backend.util;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ThumbnailGenerator {

    private final int width = 300;

    private final int height = 300;

    public Thumbnail convertToThumbnail(Image image) throws IOException {
        log.info("...Converting photo... : " + image.getData().length);
        return resize(image);
    }

    private Thumbnail resize(Image image) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(image.getData());
        BufferedImage inputImage = ImageIO.read(inputStream);
        Dimension scaledDimension = rescaleKeepingRatio(inputImage);
        BufferedImage outputImage = new BufferedImage(scaledDimension.width, scaledDimension.height,
                inputImage.getType());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, image.getExtension(), outputStream);
        return new Thumbnail(12345, outputStream.toByteArray(), image.getExtension(), image);
    }

    private Dimension rescaleKeepingRatio(BufferedImage inputImage) {
        double widthRatio = inputImage.getWidth() / (double) width;
        double heightRatio = inputImage.getHeight() / (double) height;

        int finalWidth = width;
        int finalHeight = height;
        if (widthRatio > heightRatio) {
            finalHeight = (int) (inputImage.getHeight() / heightRatio);
        } else {
            finalWidth = (int) (inputImage.getWidth() / widthRatio);
        }
        return new Dimension(finalWidth, finalHeight);
    }

}
