package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Picture;
import services.IRetrofitService;

public class GalleryController {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private int rowIndex = 0;
    private int columnIndex = 0;
    private String relativePath = ".";

    private final IRetrofitService retrofitService;

    @FXML
    private HBox box;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField textField;

    @FXML
    private CreateFolderController createFolderController;

    public GalleryController(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @FXML
    public void initialize() throws IOException {
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * Picture.SIZE);
        gridPane.setMinHeight(NUMBER_OF_ROWS * Picture.SIZE);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), new Insets(0))));

        var reference = this;
        var loader = new FXMLLoader(
            getClass().getResource("../view/create-folder.fxml"),
            null,
            new JavaFXBuilderFactory(),
            new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    return new CreateFolderController(retrofitService, reference);
                }
            }
        );

        var rootLayout = (VBox)loader.load();
        box.getChildren().add(rootLayout);
    }

    public <T extends Node> void loadRootLayout(FXMLLoader loader) throws IOException {
        T rootLayout = loader.load();
        gridPane.add(rootLayout, columnIndex, rowIndex);

        if (columnIndex == NUMBER_OF_COLUMNS - 1) {
            columnIndex = 0;
            rowIndex++;
        }
        else {
            columnIndex++;
        }
    }

    public void refresh(String path) {
        System.out.println(path);
        rowIndex = 0;
        columnIndex = 0;
        relativePath = path;
        gridPane.getChildren().clear();
    }

    public void goUp() {
        var index = relativePath.lastIndexOf("/");
        var path = relativePath.substring(0, index);
        refresh(path);
    }

    public String getRelativePath() {
        return relativePath;
    }

}
