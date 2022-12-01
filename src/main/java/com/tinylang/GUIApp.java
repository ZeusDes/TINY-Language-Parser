package com.tinylang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Scanner");
        //Image image = new Image("icon.png");
        //stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void run(String[] args) {
        launch();
    }
}