package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    public void initialize() {}

    @FXML
    private void click(ActionEvent event) {
        RetrofitService.getThumbnail(id, new NetworkCallback<Image>() {
            @Override
            public void process(Image result) throws IOException {
                System.out.println("mnnn");
                if (result != null)
                {
                    System.out.println(result.getData().length);
                }
            }
        });
    }
}
