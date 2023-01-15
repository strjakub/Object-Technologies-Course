package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.File;
import services.FileSenderStrategyBuilder;
import services.IRetrofitService;
import services.NetworkCallback;

public class SteeringController {

    private final IRetrofitService retrofitService;
    private final GalleryController galleryController;

    public SteeringController(IRetrofitService retrofitService, GalleryController galleryController) {
        this.retrofitService = retrofitService;
        this.galleryController = galleryController;
    }

    @FXML
    private VBox container;
    
    @FXML
    private TextField textField;

    @FXML
    private void createDirectory(ActionEvent event) throws IOException {
        
        var loader = new FXMLLoader(
            getClass().getResource("../view/folder.fxml"),
            null,
            new JavaFXBuilderFactory(),
            new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    var path = galleryController.getRelativePath() + "/" + textField.getText();
                    textField.clear();
                    return new FolderController(galleryController, path);
                }
            }
        );

        galleryController.loadRootLayout(loader);
    }

    @FXML
    private void goUp(ActionEvent event) throws IOException {
        this.galleryController.goUp();
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
        var relativePath = galleryController.getRelativePath();
        
        FileSenderStrategyBuilder.Build(extension, retrofitService)
            .sendFile(absolutePath, relativePath, new NetworkCallback<Integer>() {

            @Override
            public void process(Integer result) throws IOException {
                var loader = new FXMLLoader(
                    getClass().getResource("../view/picture.fxml"),
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
                
                galleryController.loadRootLayout(loader);
            }
        });

    }
}
