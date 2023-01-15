package controller;

import java.io.IOException;
import java.util.HashSet;

import javafx.fxml.FXML;
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
import model.Picture;
import services.IRetrofitService;
import services.Root;

public class GalleryController {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private int rowIndex = 0;
    private int columnIndex = 0;
    private String relativePath = ".";
    private HashSet<String> names = new HashSet<String>();

    private final IRetrofitService retrofitService;

    @FXML
    private HBox box;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField textField;

    @FXML
    private SteeringController steeringController;

    public GalleryController(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @FXML
    public void initialize() throws IOException {
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * Picture.SIZE);
        gridPane.setMinHeight(NUMBER_OF_ROWS * Picture.SIZE);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), new Insets(0))));
        var controller = new SteeringController(retrofitService, this);
        var rootLayout = (VBox)Root.createElement("view/steering.fxml", controller);
        box.getChildren().add(0, rootLayout);
    }

    public <T extends Node> void loadRootLayout(T rootLayout) {
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

    public boolean hasDirectory(String name) {
        return names.contains(name);
    }

    public void createDirectory(String name) {
        names.add(name);
        var path = getRelativePath() + "/" + name;
        var controller = new FolderController(this, path);
        var rootLayout = (VBox)Root.createElement("view/folder.fxml", controller);
        loadRootLayout(rootLayout);
    }
}
