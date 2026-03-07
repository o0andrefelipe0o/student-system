module com.dev.studentsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dev.studentsystem to javafx.fxml;
    opens com.dev.studentsystem.controller to javafx.fxml;

    exports com.dev.studentsystem;
}
