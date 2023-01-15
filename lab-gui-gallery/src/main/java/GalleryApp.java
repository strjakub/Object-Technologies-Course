import java.io.IOException;

import controller.GalleryController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.RetrofitClient;
import services.RetrofitService;
import services.Root;

public class GalleryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        var controller = new GalleryController(new RetrofitService(new RetrofitClient()));
        var rootLayout = (Pane)Root.createElement("view/galleryView.fxml", controller);

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
