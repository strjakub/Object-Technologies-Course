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
import model.ImageSizeChangeListener;
import model.PictureDAO;
import model.PictureSizes;
import model.Thumbnail;
import model.ThumbnailDAO;
import services.IRetrofitService;
import services.NetworkCallback;

public class PictureController implements ImageSizeChangeListener {

    private final IRetrofitService retrofitService;
    private final SteeringController steeringController;

    private Integer id;
    private ProgressIndicator progress;
    private Thumbnail thumbnail;
    private ImageView imageView;

    @FXML
    private VBox container;

    public PictureController(IRetrofitService retrofitService, SteeringController steeringController) {
        this.retrofitService = retrofitService;
        this.steeringController = steeringController;
    }

    public void setId(Integer id) {
        this.id = id;
        retrofitService.getThumbnail(id, new NetworkCallback<ThumbnailDAO>() {
            @Override
            public void process(ThumbnailDAO result) throws IOException {
                thumbnail = ThumbnailDAO.convertTo(result);
                var img = new Image(new ByteArrayInputStream(thumbnail.getImage(steeringController.getCurrentSize())));
                imageView = new ImageView(img);
                imageView.setOnMouseClicked(event -> { showPicture(); });
                var size = steeringController.getCurrentIntSize();
                imageView.setFitHeight(size);
                imageView.setFitWidth(size);
                container.getChildren().remove(progress);
                container.getChildren().add(imageView);
            }
        });
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.id = thumbnail.getImageId();
        this.thumbnail = thumbnail;
    }

    public void initialize() {
        if (thumbnail == null) {
            progress = new ProgressIndicator();
            var size = steeringController.getCurrentIntSize();
            progress.setMinSize(size, size);
            container.getChildren().add(progress);
        } else {
            var img = new Image(new ByteArrayInputStream(thumbnail.getImage(steeringController.getCurrentSize())));
            imageView = new ImageView(img);
            imageView.setOnMouseClicked(event -> { showPicture(); });
            var size = steeringController.getCurrentIntSize();
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            container.getChildren().add(imageView);
        }

        steeringController.addListener(this);
    }

    private void showPicture() {
        retrofitService.getImage(id, new NetworkCallback<PictureDAO>() {
            @Override
            public void process(PictureDAO result) throws IOException { 
                var stage = new Stage();
                var image = PictureDAO.convertTo(result);
                var bytes = image.getData();
                var img = new Image(new ByteArrayInputStream(bytes));
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

    @Override
    public void changed(PictureSizes size) {

        if (thumbnail == null) {
            progress.setMinSize(size.toInt(), size.toInt());
            return;
        }

        var image = new Image(new ByteArrayInputStream(thumbnail.getImage(size)));
        var s = steeringController.getCurrentIntSize();
        imageView.setImage(image);
        imageView.setFitHeight(s);
        imageView.setFitWidth(s);
    }
}
