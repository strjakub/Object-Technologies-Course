package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Picture;
import model.PictureDAO;
import model.Thumbnail;
import model.ThumbnailDAO;
import services.IRetrofitService;
import services.NetworkCallback;

public class PictureController {

    private final IRetrofitService retrofitService;
    private Integer id;
    private ProgressIndicator progress;

    @FXML
    private VBox container;

    public PictureController(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public void setId(Integer id) {
        this.id = id;
        retrofitService.getImage(id, new NetworkCallback<PictureDAO>() {
            @Override
            public void process(PictureDAO result) throws IOException {
                var image = PictureDAO.convertTo(result);
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                imageView.setOnMouseClicked(event -> { showPicture(); });
                imageView.setFitHeight(Picture.SIZE);
                imageView.setFitWidth(Picture.SIZE);
                container.getChildren().remove(progress);
                container.getChildren().add(imageView);
            }
        });
    }

    public void initialize() {
        progress = new ProgressIndicator();
        progress.setMinSize(Picture.SIZE, Picture.SIZE);
        container.getChildren().add(progress);
    }

    private void showPicture() {
        retrofitService.getThumbnail(id, new NetworkCallback<ThumbnailDAO>() {
            @Override
            public void process(ThumbnailDAO result) throws IOException {
                var alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("To jest informacja na pasku");
                alert.setHeaderText("Tu powinno byc pytanie");
                
                var buttonTypeOne = new ButtonType("Small");
                var buttonTypeTwo = new ButtonType("Medium");
                var buttonTypeThree = new ButtonType("Big");
                
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);
                
                var stage = new Stage();
                var resultType = alert.showAndWait();
                var image = ThumbnailDAO.convertTo(result);
                var bytes = image.getImage(resultType.get().getText());
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
}
