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
import model.DirectoryContentsDAO;
import model.ImageSizeChangeListener;
import model.PictureSizes;
import services.IRetrofitService;
import services.NetworkCallback;
import services.Root;

public class GalleryController implements ImageSizeChangeListener {

    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int NUMBER_OF_ROWS = 6;

    private int rowIndex = 0;
    private int columnIndex = 0;

    private final HashSet<String> names = new HashSet<String>();

    private final SteeringController steeringController;
    private final IRetrofitService retrofitService;
    private final PathController pathController = new PathController();

    @FXML
    private HBox box;

    @FXML
    private GridPane gridPane;
    
    public GalleryController(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
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
        refresh();
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

    public void refresh() {
        rowIndex = 0;
        columnIndex = 0;
        gridPane.getChildren().clear();
        names.clear();
        retrofitService.cancelAll();
        var path = pathController.getCurrentPath();
        System.out.println(path);
        retrofitService.getPathContents(path, new NetworkCallback<DirectoryContentsDAO>() {
            @Override
            public void process(DirectoryContentsDAO result) throws IOException {
                var content = DirectoryContentsDAO.convertTo(result);
                for (var thumbnail: content.getThumbnails()) {
                    var controller = new PictureController(retrofitService, steeringController);
                    controller.setThumbnail(thumbnail);
                    var rootLayout = Root.<VBox>createElement("view/picture.fxml", controller);
                    loadRootLayout(rootLayout);
                }
            } 
        });
    }

    public void goUp() {
        if (pathController.tryGoUp()) {
            refresh();
        }
    }

    public void goToDirectory(String name) {
        pathController.goToDirectory(name);
        refresh();
    }

    public String getRelativePath() {
        return pathController.getCurrentPath();
    }

    public boolean hasDirectory(String name) {
        return names.contains(name);
    }

    public void createDirectory(String name) {
        names.add(name);
        var controller = new FolderController(this, steeringController, name);
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
