package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.File;
import services.FileSenderStrategyBuilder;
import services.IRetrofitService;
import services.NetworkCallback;
import services.Root;

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

        var name = textField.getText();
        var error = "";

        if (name == "") {
            error = "Folder nie może mieć pustej nazwy";
        }
        else if (galleryController.hasDirectory(name)) {
            error = "Folder o podanej nazwie juz istnieje";
        }

        if (error != "") {
            var alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("To jest informacja na pasku");
            alert.setHeaderText(error);
            var okButton = new ButtonType("ok");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
            return;
        }

        textField.clear();
        galleryController.createDirectory(name);
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
                var controller = new PictureController(retrofitService);
                controller.setId(result);
                var rootLayout = (VBox)Root.createElement("view/picture.fxml", controller);
                galleryController.loadRootLayout(rootLayout);
            }
        });

    }
}
