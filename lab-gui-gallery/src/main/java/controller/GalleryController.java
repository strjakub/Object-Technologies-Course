package controller;

import javafx.stage.FileChooser;

import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.ActionEvent;

import javafx.stage.Stage;
import model.Gallery;
import model.Image;
import model.Photo;
import services.RetrofitService;
import util.ImageHelper;


public class GalleryController {

    private Gallery galleryModel;

    @FXML
    public void initialize() {
        // TODO additional FX controls initialization
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        // bindSelectedPhoto(gallery.getPhotos().get(0));
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        // TODO view <-> model bindings configuration
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        chooser.setTitle("Open File");
        var file = chooser.showSaveDialog(new Stage());

        var fileName = file.getPath();
        var extension = ImageHelper.getExtension(fileName);
        var path  = Paths.get(file.getPath());
        var bytes = Files.readAllBytes(path);
        var image = new Image(bytes, extension);
        RetrofitService.postImage(image);
    }
}
