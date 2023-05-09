module org.Hannya {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.Hannya to javafx.fxml;
    exports org.Hannya;
}
