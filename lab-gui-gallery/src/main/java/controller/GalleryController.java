package controller;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private int rowIndex = 0;
    private int columnIndex = 0;

    private Gallery galleryModel;

    @FXML
    private ImageController imageController;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        System.out.println("initializing");
        gridPane.setMinWidth(565);
        gridPane.setMinHeight(600);
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(140, 200, 140), new CornerRadii(0), new Insets(0))));
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        var filter = new FileChooser.ExtensionFilter("ZIP lub PNG files", "*.zip", "*.png");
        chooser.getExtensionFilters().add(filter);

        var file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }

        var absolutePath = file.getAbsolutePath();
        var extension = ImageHelper.getExtension(absolutePath);

        if (extension.equals("zip")) {
            var zip = new ZipFile(absolutePath);
            for (var entry: Collections.list(zip.entries())) {
                if (entry.getName().endsWith("png")) {
                    var stream = zip.getInputStream(entry);
                    var bytes = stream.readAllBytes();
                    var ext = ImageHelper.getExtension(entry.getName());
                    sendOneImage(bytes, ext);
                }
            }
            zip.close();
            return;
        }

        sendOneImage(absolutePath);
    }

    private void sendOneImage(byte[] bytes, String extension) throws IOException {

        if (!extension.equals("png")) {
            System.out.println("Dopuszczalne rozszerzenie to png, a jest: " + extension);
            return;
        }

        var image = new Image(bytes, extension);
        RetrofitService.postImage(image, new NetworkCallback<Integer>() {
            @Override
            public void process(Integer result) throws IOException {
                var loader = new FXMLLoader();
                loader.setLocation(GalleryController.class.getResource("../view/image.fxml"));
                VBox rootLayout = loader.load();
                ImageController controller = loader.getController();
                controller.setId(result);
                gridPane.add(rootLayout, columnIndex, rowIndex);

                if (columnIndex == 4) {
                    columnIndex = 0;
                    rowIndex++;
                }
                else {
                    columnIndex++;
                }
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
