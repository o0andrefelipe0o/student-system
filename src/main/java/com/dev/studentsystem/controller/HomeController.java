package com.dev.studentsystem.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    @FXML private TableView<StudentRow>           studentTable;
    @FXML private TableColumn<StudentRow, HBox>   colActions; // 1ª - apenas professor
    @FXML private TableColumn<StudentRow, String> colName;    // 2ª - nome do aluno
    @FXML private TableColumn<StudentRow, String> colFreq;    // 3ª - frequência geral
    @FXML private Label statusLabel;

    // Painel lateral
    @FXML private Label aboveAverageLabel;
    @FXML private Label lowFrequencyLabel;
    @FXML private Label classAverageLabel;

    // ────────────────────────────────────────────────────────
    // Dados mockados, migrar para banco de dados
    // Nomes das disciplinas
    private final String[] DISCIPLINE_NAMES = {
            "Matemática", "Português", "História", "Ciências", "Educação Física"
    };
    // Alunos
    private final ObservableList<StudentRow> students = FXCollections.observableArrayList(
            new StudentRow("João Silva",   new double[]{7, 8, 6, 9, 10}, 80),
            new StudentRow("Maria Souza",  new double[]{5, 6, 4, 7,  8}, 70),
            new StudentRow("Pedro Lima",   new double[]{9, 9, 8, 10, 9}, 90),
            new StudentRow("Ana Costa",    new double[]{4, 5, 3,  6,  5}, 60),
            new StudentRow("Lucas Mendes", new double[]{6, 7, 7,  8,  6}, 78)
    );
    // ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        // Estado inicial de visitante, sem sessão
        headerUserLabel.setText("Visitante");
        manageUsersButton.setVisible(false);
        manageDisciplinesButton.setVisible(false);
        manageStudentButton.setVisible(false);
        logoutButton.setVisible(false);
        userProfileButton.setVisible(false);
        loginButton.setVisible(true);

        // Tabela
        // Colunas estáticas
        colActions.setVisible(false); // Coluna de ações oculta para visitante
        // Coluna Nome do aluno
        colName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getName())
        );
        // Coluna frequência de presença
        colFreq.setCellValueFactory(
                data -> new SimpleStringProperty(
                        String.format("%.0f%%", data.getValue().getFrequency())
                )
        );

        // Colunas dinâmicas por disciplina
        buildDisciplineColumns();

        // Tabela e painel lateral
        studentTable.setItems(students);
        studentTable.setPlaceholder(new Label("Nenhum aluno cadastrado."));
        refreshSidePanel();
        statusLabel.setText("Exibindo " + students.size() + " aluno(s).");
    }

    // Gera uma coluna de nota para cada disciplina
    private void buildDisciplineColumns() {
        for (int i = 0; i < DISCIPLINE_NAMES.length; i++) {
            final int idx = i;
            TableColumn<StudentRow, String> col = new TableColumn<>(DISCIPLINE_NAMES[i]);
            col.setPrefWidth(110);
            col.setCellValueFactory(data ->
                    new SimpleStringProperty(
                            String.format("%.1f", data.getValue().getGrades()[idx])
                    )
            );
            studentTable.getColumns().add(col);
        }
    }

    // Painel lateral
    private void refreshSidePanel() {
        if (students.isEmpty()) {
            classAverageLabel.setText("Sem dados disponíveis");
            aboveAverageLabel.setText("Sem dados disponíveis");
            lowFrequencyLabel.setText("Sem dados disponíveis");
            return;
        }

        // Média por disciplina
        double[] discAvg = calcDisciplineAverages();
        StringBuilder sbAvg = new StringBuilder();
        for (int i = 0; i < DISCIPLINE_NAMES.length; i++) {
            sbAvg.append(DISCIPLINE_NAMES[i])
                    .append(": ")
                    .append(String.format("%.2f", discAvg[i]));
            if (i < DISCIPLINE_NAMES.length - 1) sbAvg.append("\n");
        }
        classAverageLabel.setText(sbAvg.toString());

        // Alunos acima da média geral da turma
        /** Pega a média individual de cada aluno (calculada acima) e soma todas.
         * No final divide pelo número de alunos. */
        double classAvg = calcClassAvgGrade();
        StringBuilder sbAbove = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            StudentRow s = students.get(i);
            if (s.getAvgGrade() > classAvg) {
                if (sbAbove.length() > 0) sbAbove.append("\n");
                sbAbove.append(s.getName());
            }
        }
        aboveAverageLabel.setText(sbAbove.length() == 0 ? "(nenhum)" : sbAbove.toString());

        // Alunos com frequência abaixo de 75%
        StringBuilder sbLow = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            StudentRow s = students.get(i);
            if (s.getFrequency() < 75) {
                if (sbLow.length() > 0) sbLow.append("\n");
                sbLow.append(s.getName());
            }
        }
        lowFrequencyLabel.setText(sbLow.length() == 0 ? "(nenhum)" : sbLow.toString());
    }

    // Calculos
    /** Média geral da turma (média das médias individuais). */
    private double calcClassAvgGrade() {
        double total = 0;
        for (int i = 0; i < students.size(); i++) {
            total = total + students.get(i).getAvgGrade();
        }
        return total / students.size();
    }

    // Média por disciplina
    /**  Através do loop interno com o j representando a disciplina, sums[0] vai acumulando todas as notas de Matemática, sums[1] todas de Português, e assim por diante, coluna por coluna.
     * Depois, divide cada soma pelo número de alunos para obter a média de cada disciplina separadamente. */
    private double[] calcDisciplineAverages() {
        int n = DISCIPLINE_NAMES.length;
        double[] sums = new double[n];

        for (int i = 0; i < students.size(); i++) {
            double[] grades = students.get(i).getGrades();
            for (int j = 0; j < n; j++) {
                sums[j] = sums[j] + grades[j];
            }
        }

        double[] averages = new double[n];
        for (int i = 0; i < n; i++) {
            averages[i] = sums[i] / students.size();
        }
        return averages;
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

    // Linha da tabela
    /** Representa uma linha de aluno na TableView.
     */
    public static class StudentRow {

        private final String   name;
        private final double[] grades;    // Nota por disciplina
        private final double   frequency; // Frequência geral (%)

        public StudentRow(String name, double[] grades, double frequency) {
            this.name      = name;
            this.grades    = grades;
            this.frequency = frequency;
        }

        public String   getName()      { return name; }
        public double[] getGrades()    { return grades; }
        public double   getFrequency() { return frequency; }

        // Média aritmética de todas as notas.
        /** Percorre o array de notas do aluno somando tudo, depois divide pela quantidade de disciplinas. */
        public double getAvgGrade() {
            double sum = 0;
            for (int i = 0; i < grades.length; i++) {
                sum = sum + grades[i];
            }
            return sum / grades.length;
        }
    }
}
