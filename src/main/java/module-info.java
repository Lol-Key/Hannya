module org.Hannya {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.fxmisc.flowless;
    requires org.fxmisc.richtext;
    requires com.jfoenix;
    requires com.sandec.mdfx;
    requires org.apache.commons.io;
    requires javafx.media;

    opens Project to javafx.fxml;
    exports Project;
    exports Project.Controllers;
    opens Project.Controllers to javafx.fxml;
}
