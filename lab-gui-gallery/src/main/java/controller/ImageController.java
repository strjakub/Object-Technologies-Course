package controller;

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

    public void setId(Integer id) {
        this.id = id;
        button.setText("Alibaba: " + id.toString());
    }

    @FXML
    private Button button;

    @FXML
    private VBox container;

    public void initialize() {
        var progress = new ProgressIndicator();
        container.getChildren().add(progress);
        // button.setDisable(true);
    }

    @FXML
    private void click(ActionEvent event) {
        RetrofitService.getThumbnail(id, new NetworkCallback<Dto>() {
            @Override
            public void process(Dto result) throws IOException {
                System.out.println("mnnn");
                if (result != null)
                {
                    System.out.println(result.extension);
                }
            }
        });
    }
}
