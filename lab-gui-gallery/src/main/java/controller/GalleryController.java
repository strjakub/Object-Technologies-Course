package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Picture;
import services.FileSenderStrategyBuilder;
import services.IRetrofitService;
import services.File;
import services.NetworkCallback;

public class GalleryController {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private int rowIndex = 0;
    private int columnIndex = 0;
    private String relativePath = "";

    private final IRetrofitService retrofitService;

    @FXML
    private PictureController imageController;

    @FXML
    private GridPane gridPane;

    public GalleryController(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @FXML
    public void initialize() {
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * Picture.SIZE);
        gridPane.setMinHeight(NUMBER_OF_ROWS * Picture.SIZE);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), new Insets(0))));
    }


    @FXML
    private void createDirectory(ActionEvent event) throws IOException
    {
        var loader = new FXMLLoader(
            getClass().getResource("../view/folder.fxml"),
            null,
            new JavaFXBuilderFactory(),
            new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    return new FolderController(relativePath);
                }
            }
        );
        
        VBox rootLayout = loader.load();
        gridPane.add(rootLayout, columnIndex, rowIndex);
    }

    @FXML
    private void sendData(ActionEvent event) throws IOException {
        event.consume();

        var chooser = new FileChooser();
        var args = File.getExtensionFilter();
        var filter = new FileChooser.ExtensionFilter("ZIP lub Images", args);
        chooser.getExtensionFilters().add(filter);

        var file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }

        var absolutePath = file.getAbsolutePath();
        var extension = File.getExtension(absolutePath);
        FileSenderStrategyBuilder.Build(extension, retrofitService)
            .sendFile(absolutePath, relativePath, new NetworkCallback<Integer>() {

            @Override
            public void process(Integer result) throws IOException {


                var loader = new FXMLLoader(
                    getClass().getResource("../view/folder.fxml"),
                    null,
                    new JavaFXBuilderFactory(),
                    new Callback<Class<?>, Object>() {
                        @Override
                        public Object call(Class<?> param) {
                            var c = new PictureController(retrofitService);
                            c.setId(result);
                            return c;
                        }
                    }
                );
                
                VBox rootLayout = loader.load();
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
