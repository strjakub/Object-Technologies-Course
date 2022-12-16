package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ImageDto;

import services.NetworkCallback;
import services.RetrofitService;

public class ImageController {

    private Integer id;
    private ProgressIndicator progress;

    @FXML
    private VBox container;

    public void setId(Integer id) {
        this.id = id;
        RetrofitService.getThumbnail(id, new NetworkCallback<ImageDto>() {
            @Override
            public void process(ImageDto result) throws IOException {
                var image = ImageDto.convertTo(result);
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
        RetrofitService.getImage(id, new NetworkCallback<ImageDto>() {
            @Override
            public void process(ImageDto result) throws IOException {
                var image = ImageDto.convertTo(result);
                var stage = new Stage();
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                var box = new HBox();
                box.getChildren().add(imageView);
                stage.setScene(new Scene(box));
                stage.setTitle("Powiekszone zdjecie małego zdjecia");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(container.getScene().getWindow() );
                stage.show();
            }
        });
    }
}
