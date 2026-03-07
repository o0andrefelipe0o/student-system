package com.dev.studentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/org/dev/studentsystem/fxml/home.fxml");

        if (fxmlUrl == null) {
            throw new RuntimeException("FXML não encontrado: /org/dev/studentsystem/fxml/home.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Student System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
