import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GalleryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        var loader = new FXMLLoader();
        loader.setLocation(GalleryApp.class.getResource("view/galleryView.fxml"));
        Pane rootLayout = loader.load();
        configureStage(primaryStage, rootLayout);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, Pane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Thumbnail Gallery");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }
}
