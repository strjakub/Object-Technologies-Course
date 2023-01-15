package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.ImageSizeChangeListener;
import model.PictureSizes;

public class FolderController implements ImageSizeChangeListener {

    private final GalleryController galleryController;
    private final SteeringController steeringController;
    private final String relativePath;

    private ImageView imageView;

    public FolderController(GalleryController galleryController, SteeringController steeringController, String relativePath) {
        this.galleryController = galleryController;
        this.steeringController = steeringController;
        this.relativePath = relativePath;
    }

    @FXML
    private VBox container;

    public void initialize() {
        var pane = new StackPane();
        var img = new Image(getClass().getResource("/folder.png").toString());
        imageView = new ImageView(img);
        var size = steeringController.getCurrentSize().toInt();
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.setOnMouseClicked(event -> { showDirectory(); });
        var label = new Label(relativePath);
        label.setTextFill(Color.color(1, 0, 0));
        label.setStyle("-fx-font-weight: bold");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 80, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
        pane.getChildren().addAll(imageView, label);
        container.getChildren().add(pane);
        steeringController.addListener(this);
    }

    private void showDirectory() {
        galleryController.refresh(relativePath);
    }

    @Override
    public void changed(PictureSizes size) {
        var s = steeringController.getCurrentSize().toInt();
        imageView.setFitHeight(s);
        imageView.setFitWidth(s);
    }
}
