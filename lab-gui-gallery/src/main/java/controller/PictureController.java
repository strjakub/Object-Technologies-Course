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
import model.PictureDAO;

import services.NetworkCallback;
import services.RetrofitService;

public class PictureController {

    private static final int IMAGE_SIZE = 100;

    private Integer id;
    private ProgressIndicator progress;

    @FXML
    private VBox container;

    public void setId(Integer id) {
        this.id = id;
        RetrofitService.getThumbnail(id, new NetworkCallback<PictureDAO>() {
            @Override
            public void process(PictureDAO result) throws IOException {
                var image = PictureDAO.convertTo(result);
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                imageView.setOnMouseClicked(event -> { showPicture(); });
                imageView.setFitHeight(IMAGE_SIZE);
                imageView.setFitWidth(IMAGE_SIZE);
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
        RetrofitService.getImage(id, new NetworkCallback<PictureDAO>() {
            @Override
            public void process(PictureDAO result) throws IOException {
                var image = PictureDAO.convertTo(result);
                var stage = new Stage();
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                var box = new HBox();
                box.getChildren().add(imageView);
                stage.setScene(new Scene(box));
                stage.setTitle("Original Image");
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(container.getScene().getWindow() );
                stage.show();
            }
        });
    }
}
