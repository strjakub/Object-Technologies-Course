package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.ImageSizeChangeListener;
import model.PictureSizes;

public class DirectoryController implements ImageSizeChangeListener {

    private final GalleryController galleryController;
    private final SteeringController steeringController;
    private final String directoryName;

    private ImageView imageView;

    public DirectoryController(GalleryController galleryController, SteeringController steeringController, String directoryName) {
        this.galleryController = galleryController;
        this.steeringController = steeringController;
        this.directoryName = directoryName;
    }

    @FXML
    private VBox container;

    public void initialize() {
        var pane = new StackPane();
        var img = new Image(getClass().getResource("/directory.png").toString());
        imageView = new ImageView(img);
        var size = steeringController.getCurrentIntSize();
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.setOnMouseClicked(event -> { showDirectory(); });
        var label = new Label(directoryName);
        label.setTextFill(Color.color(1, 0, 0));
        label.setOnMouseClicked(event -> { showDirectory(); });
        label.setStyle("-fx-font-weight: bold");
        pane.getChildren().addAll(imageView, label);
        container.getChildren().add(pane);
        steeringController.addListener(this);
    }

    private void showDirectory() {
        galleryController.goToDirectory(directoryName);
    }

    @Override
    public void changed(PictureSizes size) {
        var s = steeringController.getCurrentIntSize();
        imageView.setFitHeight(s);
        imageView.setFitWidth(s);
    }
}
