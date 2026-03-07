package com.dev.studentsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class HomeController {

    // Header
    @FXML private Label  headerUserLabel;
    @FXML private Button manageUsersButton;
    @FXML private Button manageDisciplinesButton;
    @FXML private Button manageStudentButton;
    @FXML private Button logoutButton;
    @FXML private Button loginButton;
    @FXML private Button userProfileButton;

    // Tabela
    @FXML private TableView<Object>           studentTable;
    @FXML private TableColumn<Object, HBox>   colActions;
    @FXML private TableColumn<Object, String> colName;
    @FXML private TableColumn<Object, String> colFreq;
    @FXML private Label statusLabel;

    // Painel lateral
    @FXML private Label aboveAverageLabel;
    @FXML private Label lowFrequencyLabel;
    @FXML private Label classAverageLabel;

    // ────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        // Estado inicial: sem sessão — apenas loginButton visível
        headerUserLabel.setText("Visitante");
        manageUsersButton.setVisible(false);
        manageDisciplinesButton.setVisible(false);
        manageStudentButton.setVisible(false);
        logoutButton.setVisible(false);
        userProfileButton.setVisible(false);
        loginButton.setVisible(true);

        // Tabela vazia
        studentTable.setPlaceholder(new Label("Nenhum aluno cadastrado."));

        // Painel lateral sem dados
        classAverageLabel.setText("Sem dados disponíveis");
        aboveAverageLabel.setText("Sem dados disponíveis");
        lowFrequencyLabel.setText("Sem dados disponíveis");

        statusLabel.setText("...");
    }

    // Handlers
    @FXML
    private void handleManageUser() {
        showInfo("Em Construção", "Gerenciamento de professores em construção.");
    }
    @FXML
    private void handleManageDiscipline() {
        showInfo("Em Construção", "Gerenciamento de disciplinas em construção.");
    }
    @FXML
    private void handleManageStudent() {
        showInfo("Em Construção", "Gerenciamento de alunos em construção.");
    }
    @FXML
    private void handleLogin() {
        showInfo("Em Construção", "Tela de login em construção.");
    }
    @FXML
    private void handleLogout() {
        showInfo("Em Construção", "Logout em construção.");
    }
    @FXML
    private void handleUserProfile() {
        showInfo("Em Construção", "Perfil do usuário em construção.");
    }
    @FXML
    private void handleRefresh() {
        showInfo("Em Construção", "Atualização de dados em construção.");
    }

    // Utilitário
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
