package Project.Controllers;

import java.io.IOException;

import Project.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("main");
    }
}