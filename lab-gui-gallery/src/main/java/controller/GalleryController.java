package controller;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
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
    public void initialize() {
        System.out.println("initializing");
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        // bindSelectedPhoto(gallery.getPhotos().get(0));
    }

    private void bindSelectedPhoto(Image selectedPhoto) {
        // TODO view <-> model bindings configuration
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        chooser.setTitle("Open File");
        var file = chooser.showSaveDialog(new Stage());

        var fileName = file.getPath();
        if (fileName == null) {
            return;
        }

        var extension = ImageHelper.getExtension(fileName);
        var path  = Paths.get(file.getPath());
        var bytes = Files.readAllBytes(path);
        var image = new Image(bytes, extension);

        RetrofitService.postImage(image, new NetworkCallback<Integer>(4) {
            @Override
            public void process(Integer result) {
                System.out.println("Received: " + result.toString());
            }
        });
    }
}
