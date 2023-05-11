package Project.Controllers;


import Project.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class DescriptionController {
    @FXML
    private void onSwitch() throws IOException {
        App.setRoot("main");
    }
}
