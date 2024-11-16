module org.example.project_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;


    opens org.example.project_oop to javafx.fxml;
    exports org.example.project_oop;
}