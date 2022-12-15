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
import java.util.Collections;
import java.util.zip.ZipFile;

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

        var absolutePath = file.getAbsolutePath();
        var extension = ImageHelper.getExtension(absolutePath);

        if (extension.equals(".zip")) {
            var zip = new ZipFile(absolutePath);
            for (var entry: Collections.list(zip.entries())) {
                if (entry.getName().endsWith(".png")) {
                    var stream = zip.getInputStream(entry);
                    var bytes = stream.readAllBytes();
                    var ext = ImageHelper.getExtension(entry.getName());
                    sendOneImage(bytes, ext);
                }
            }
            zip.close();
            return;
        }
        
        sendOneImage(file.getPath());
    }

    private void sendOneImage(byte[] bytes, String extension) throws IOException {

        if (!extension.equals(".png")) {
            System.out.println("Dopuszczalne rozszerzenie to .png, a jest: " + extension);
            return;
        }

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

    private void sendOneImage(String fullPath) throws IOException {
        var extension = ImageHelper.getExtension(fullPath);
        var path  = Paths.get(fullPath);
        var bytes = Files.readAllBytes(path);
        sendOneImage(bytes, extension);
    }
}
