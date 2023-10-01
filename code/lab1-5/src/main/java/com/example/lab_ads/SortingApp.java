package com.example.lab_ads;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class SortingApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SortingApp.fxml"));

        ScrollPane root = loader.load();

        Scene scene = new Scene(root, 900, 600);
        String css = Objects.requireNonNull(this.getClass().getResource("hello.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image icon = new Image("Vector.png");
        stage.getIcons().add(icon);

        // todo add kitty
        Image image = new Image("kitty.png");
        ImageView kitty = new ImageView(image);

        stage.setScene(scene);
        stage.setTitle("Sorting Array Application");
        stage.show();
    }
}
