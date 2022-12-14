package controller;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Gallery;
import model.Image;

import services.NetworkCallback;
import services.RetrofitService;

import util.ImageHelper;

public class GalleryController {

    private Gallery galleryModel;

    @FXML
    private ImageController imageController;

    @FXML
    private VBox pane;

    @FXML
    public void initialize() {
        System.out.println("initializing");
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        chooser.setTitle("Open File");
        var file = chooser.showSaveDialog(new Stage());

        if (file == null) {
            return;
        }

        var fileName = file.getPath();
        var extension = ImageHelper.getExtension(fileName);
        var path  = Paths.get(file.getPath());
        var bytes = Files.readAllBytes(path);
        var image = new Image(bytes, extension);

        RetrofitService.postImage(image, new NetworkCallback<Integer>() {
            @Override
            public void process(Integer result) throws IOException {
                System.out.println("Received: " + result.toString());
                var loader = new FXMLLoader();
                loader.setLocation(GalleryController.class.getResource("../view/image.fxml"));
                VBox rootLayout = loader.load();
                ImageController controller = loader.getController();
                controller.setId(result);
                pane.getChildren().add(rootLayout);
            }
        });
    }
}
