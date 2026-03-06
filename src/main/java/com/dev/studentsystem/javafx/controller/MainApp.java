package com.dev.studentsystem.javafx.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        // Componente com texto
        Label label = new Label("Hello World");

        // Layout
        StackPane root = new StackPane(label);

        // Cena
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Student System");
        stage.setScene(scene);
        stage.show();
    }

}