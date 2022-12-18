package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.zip.ZipFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Picture;
import services.IPhotosService;
import services.IRetrofitService;
import services.NetworkCallback;
import services.PhotosService;
import services.RetrofitService;

public class GalleryController {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private final IPhotosService photosService = new PhotosService();
    private final IRetrofitService retrofitService = RetrofitService.Instance;

    private int rowIndex = 0;
    private int columnIndex = 0;

    @FXML
    private PictureController imageController;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * PictureController.PICTURE_SIZE);
        gridPane.setMinHeight(NUMBER_OF_ROWS * PictureController.PICTURE_SIZE);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), new Insets(0))));
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        var args = photosService.getExtensionFilter();
        var filter = new FileChooser.ExtensionFilter("ZIP lub Images", args);
        chooser.getExtensionFilters().add(filter);

        var file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }

        var absolutePath = file.getAbsolutePath();
        var extension = photosService.getExtension(absolutePath);

        if (extension.equals("zip")) {
            var zip = new ZipFile(absolutePath);
            for (var entry: Collections.list(zip.entries())) {
                var ext = photosService.getExtension(entry.getName());
                if (photosService.isPhoto(ext)) {
                    var stream = zip.getInputStream(entry);
                    var bytes = stream.readAllBytes();
                    sendOneImage(bytes, ext);
                }
            }
            zip.close();
            return;
        }

        sendOneImage(absolutePath);
    }

    private void sendOneImage(byte[] bytes, String extension) throws IOException {

        if (!photosService.isPhoto(extension)) {
            var alert = new Alert(AlertType.CONFIRMATION, "Bledne rozszerzenie, dozwolone tylko zdjecia", ButtonType.YES);
            alert.showAndWait();
            return;
        }

        var image = new Picture(bytes, extension);
        retrofitService.postImage(image, new NetworkCallback<Integer>() {
            @Override
            public void process(Integer result) throws IOException {
                var loader = new FXMLLoader();
                loader.setLocation(GalleryController.class.getResource("../view/picture.fxml"));
                VBox rootLayout = loader.load();
                PictureController controller = loader.getController();
                controller.setId(result);
                gridPane.add(rootLayout, columnIndex, rowIndex);

                if (columnIndex == NUMBER_OF_COLUMNS - 1) {
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
        var extension = photosService.getExtension(fullPath);
        var path  = Paths.get(fullPath);
        var bytes = Files.readAllBytes(path);
        sendOneImage(bytes, extension);
    }
}
