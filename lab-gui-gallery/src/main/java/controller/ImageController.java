package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private model.Image image;
    private model.Image thumbnail;

    @FXML
    private Button button;

    @FXML
    private VBox container;

    public void setId(Integer id) {
        this.id = id;
        button.setText("Alibaba show: " + id.toString());
    }

    public void initialize() {
        progress = new ProgressIndicator();
        progress.setMinSize(100, 100);
        container.getChildren().add(progress);
        // button.setDisable(true);
    }

    @FXML
    private void click(ActionEvent event) {
        System.out.println("Clicked Alibaba " + id);
        RetrofitService.getThumbnail(id, new NetworkCallback<Dto>() {
            @Override
            public void process(Dto result) throws IOException {
                var image = Dto.convertTo(result);
                var img = new Image(new ByteArrayInputStream(image.getData()));
                var imageView = new ImageView(img);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                container.getChildren().remove(progress);
                container.getChildren().add(imageView);
            }
        });
    }

}
