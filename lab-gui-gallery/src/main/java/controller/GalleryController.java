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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipFile;

import model.Picture;

import services.NetworkCallback;
import services.RetrofitService;

import util.ImageHelper;

public class GalleryController {

    private final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp", "zip");

    private int rowIndex = 0;
    private int columnIndex = 0;

    @FXML
    private PictureController imageController;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        gridPane.setMinWidth(500);
        gridPane.setMinHeight(600);
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(140, 200, 140), new CornerRadii(0), new Insets(0))));
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        var args = photosExtensions.stream().map(x -> String.format("*.%s", x)).toArray(String[]::new);
        var filter = new FileChooser.ExtensionFilter("ZIP lub Images", args);
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
                if (photosExtensions.stream().anyMatch(e -> !entry.getName().endsWith("zip") 
                && entry.getName().endsWith(e))) {
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

        if (extension == "zip" || photosExtensions.stream().allMatch(e -> !e.equals(extension))) {
            System.out.println("Bledne rozszerzenie, dozwolone tylko zdjecia");
            return;
        }

        var image = new Picture(bytes, extension);
        RetrofitService.postImage(image, new NetworkCallback<Integer>() {
            @Override
            public void process(Integer result) throws IOException {
                var loader = new FXMLLoader();
                loader.setLocation(GalleryController.class.getResource("../view/picture.fxml"));
                VBox rootLayout = loader.load();
                PictureController controller = loader.getController();
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
