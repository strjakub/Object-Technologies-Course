package services;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.util.Callback;

public class Root {
    
    public static <T extends Node> T createElement(String resourceName, Object controller) {
        var classLoader = ClassLoader.getSystemClassLoader();
        var resource = classLoader.getResource(resourceName);
        var loader = new FXMLLoader(
            resource,
            null,
            new JavaFXBuilderFactory(),
            new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    return controller;
                }
            }
        );

        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
