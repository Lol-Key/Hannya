module org.Hannya {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.flowless;
    requires org.fxmisc.richtext;
    requires com.jfoenix;

    opens Project to javafx.fxml;
    exports Project;
    exports Project.Controllers;
    opens Project.Controllers to javafx.fxml;
}
