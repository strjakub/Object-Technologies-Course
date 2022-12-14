import controller.GalleryController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.Gallery;

import java.io.IOException;

public class GalleryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        var gallery = new Gallery();

        var loader = new FXMLLoader();
        loader.setLocation(GalleryApp.class.getResource("view/galleryView.fxml"));
        BorderPane rootLayout = loader.load();

        // set initial data into controller
        GalleryController controller = loader.getController();
        controller.setModel(gallery);

        // add layout to a scene and show them all
        configureStage(primaryStage, rootLayout);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gallery app");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }
}
