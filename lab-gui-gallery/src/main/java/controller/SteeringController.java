package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ImageSizeChangeListener;
import model.PictureSizes;
import services.File;
import services.FileSenderStrategyBuilder;
import services.IRetrofitService;
import services.NetworkCallback;
import services.Root;

public class SteeringController implements Initializable {

    private final IRetrofitService retrofitService;
    private final GalleryController galleryController;

    private final List<ImageSizeChangeListener> listeners = new ArrayList<ImageSizeChangeListener>();

    private PictureSizes currentSize = PictureSizes.Small;

    public SteeringController(IRetrofitService retrofitService, GalleryController galleryController) {
        this.retrofitService = retrofitService;
        this.galleryController = galleryController;
    }

    @FXML
    private VBox container;
    
    @FXML
    private TextField textField;

    @FXML
    private ComboBox<String> comboBox;

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
        
        var reference = this;

        FileSenderStrategyBuilder.Build(extension, retrofitService)
            .sendFile(absolutePath, relativePath, new NetworkCallback<Integer>() {

            @Override
            public void process(Integer result) throws IOException {
                var controller = new PictureController(retrofitService, reference);
                controller.setId(result);
                var rootLayout = Root.<VBox>createElement("view/picture.fxml", controller);
                galleryController.loadRootLayout(rootLayout);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                var size = PictureSizes.create(newValue);
                currentSize = size;
                for (var listener: listeners) {
                    listener.changed(size);
                }
            } 
        });
    }

    public void addListener(ImageSizeChangeListener listener) {
        listeners.add(listener);
    }

    public PictureSizes getCurrentSize() {
        return currentSize;
    }

    public int getCurrentIntSize() {
        return currentSize.toInt();
    }
}
