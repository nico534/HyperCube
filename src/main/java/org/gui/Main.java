package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //stage.setTitle("Hyper Cube / Tesseract");
        //Parent mainWindow = new FXMLLoader(getClass().getResource("mainScene.fxml")).load();
        stage.setScene(new Scene(new mainController()));
        stage.show();
    }
}
