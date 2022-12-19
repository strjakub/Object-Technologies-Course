package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.FileService;
import services.IFileService;
import services.NetworkCallback;

public class GalleryController {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private final IFileService fileService = new FileService();

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
        var args = fileService.getExtensionFilter();
        var filter = new FileChooser.ExtensionFilter("ZIP lub Images", args);
        chooser.getExtensionFilters().add(filter);

        var file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }

        fileService.postFile(file, new NetworkCallback<Integer>() {
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
}
