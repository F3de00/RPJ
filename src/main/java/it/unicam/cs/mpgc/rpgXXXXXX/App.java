package it.unicam.cs.mpgc.rpgXXXXXX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("RPJ - Dungeon Crawler");
        StackPane root = new StackPane(title);
        stage.setTitle("RPJ - Dungeon Crawler");
        stage.setScene(new Scene(root, 960, 640));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
