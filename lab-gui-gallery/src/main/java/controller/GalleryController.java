package controller;

import java.io.IOException;
import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.ImageSizeChangeListener;
import model.PictureSizes;
import services.IRetrofitService;
import services.Root;

public class GalleryController implements ImageSizeChangeListener {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private int rowIndex = 0;
    private int columnIndex = 0;
    private String relativePath = ".";
    private final HashSet<String> names = new HashSet<String>();

    private final SteeringController steeringController;

    @FXML
    private HBox box;

    @FXML
    private GridPane gridPane;
    
    public GalleryController(IRetrofitService retrofitService) {
        steeringController = new SteeringController(retrofitService, this);
        steeringController.addListener(this);
    }

    @FXML
    public void initialize() throws IOException {
        var size = steeringController.getCurrentIntSize();
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * size);
        gridPane.setMinHeight(NUMBER_OF_ROWS * size);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), new Insets(0))));
        var rootLayout = Root.<VBox>createElement("view/steering.fxml", steeringController);
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
        if (relativePath.equals(".")) {
            return;
        }

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
        var controller = new FolderController(this, steeringController, path);
        var rootLayout = Root.<VBox>createElement("view/folder.fxml", controller);
        loadRootLayout(rootLayout);
    }

    @Override
    public void changed(PictureSizes size) {
        var s = size.toInt();
        gridPane.setMinWidth(NUMBER_OF_COLUMNS * s);
        gridPane.setMinHeight(NUMBER_OF_ROWS * s);
    }
}
