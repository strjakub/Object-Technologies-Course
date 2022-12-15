package controller;

import java.io.FileOutputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import model.Dto;
import model.Image;
import services.NetworkCallback;
import services.RetrofitService;

public class ImageController {

    private Integer id;
    private Image image;
    private Image thumbnail;

    @FXML
    private Button button;

    @FXML
    private VBox container;

    public void setId(Integer id) {
        this.id = id;
        button.setText("Alibaba show: " + id.toString());
    }

    public void initialize() {
        var progress = new ProgressIndicator();
        container.getChildren().add(progress);
        // button.setDisable(true);
    }

    @FXML
    private void click(ActionEvent event) {
        RetrofitService.getImage(id, new NetworkCallback<Dto>() {
            @Override
            public void process(Dto result) throws IOException {
                var image = Image.fromDto(result);
                try (var fos = new FileOutputStream("C:\\Users\\Piotr\\Pictures\\Camera Roll\\dd.png")) {
                    fos.write(image.getData());
                 }
            }
        });
    }

 
}
