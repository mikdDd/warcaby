module org.example {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
}
