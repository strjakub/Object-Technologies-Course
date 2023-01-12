package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Picture;

public class FolderController {

    private final String relativePath;

    public FolderController(String relativePath) {
        this.relativePath = relativePath;
    }

    @FXML
    private VBox container;

    public void initialize() {
        var img = new Image(getClass().getResource("/folder.png").toString());
        var imageView = new ImageView(img);
        imageView.setFitHeight(Picture.SIZE);
        imageView.setFitWidth(Picture.SIZE);
        container.getChildren().add(imageView);
    }
}
