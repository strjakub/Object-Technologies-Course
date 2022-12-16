package controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Dto;

import services.NetworkCallback;
import services.RetrofitService;

public class ImageController {

    private Integer id;
    private ProgressIndicator progress;

    @FXML
    private VBox container;

    public void setId(Integer id) {
        this.id = id;
        RetrofitService.getThumbnail(id, new NetworkCallback<Dto>() {
            @Override
            public void process(Dto result) throws IOException {
                var image = Dto.convertTo(result);
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                imageView.setOnMouseClicked(event -> { showPicture(); });
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                container.getChildren().remove(progress);
                container.getChildren().add(imageView);
            }
        });
    }

    public void initialize() {
        progress = new ProgressIndicator();
        progress.setMinSize(100, 100);
        container.getChildren().add(progress);
    }

    private void showPicture() {
        RetrofitService.getImage(id, new NetworkCallback<Dto>() {
            @Override
            public void process(Dto result) throws IOException {
                var image = Dto.convertTo(result);

                var file = File.createTempFile("ddddd", "." + image.getExtension());
                try (var fos = new FileOutputStream(file)) {
                    fos.write(image.getData());
                }
                
                String expr = "rundll32 \"C:\\Program Files (x86)\\Windows Photo Viewer\\PhotoViewer.dll\", ImageView_Fullscreen " + file.getAbsolutePath();
                Runtime.getRuntime().exec(expr);                
            }
        });
    }

}
