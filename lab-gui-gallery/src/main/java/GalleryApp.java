import java.io.IOException;
import controller.GalleryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.RetrofitClient;
import services.RetrofitService;

public class GalleryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        var loader = new FXMLLoader(
            getClass().getResource("view/galleryView.fxml"),
            null,
            new JavaFXBuilderFactory(),
            new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    return new GalleryController(new RetrofitService(new RetrofitClient()));
                }
            }
        );

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
