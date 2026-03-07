package com.dev.studentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label helloLabel;

    @FXML
    public void initialize() {
        System.out.println("HomeController inicializado.");
    }
}
