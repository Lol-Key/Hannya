module org.Hannya {
    requires javafx.controls;
    requires javafx.fxml;

    opens Project to javafx.fxml;
    exports Project;
    exports Project.Controllers;
    opens Project.Controllers to javafx.fxml;
}
