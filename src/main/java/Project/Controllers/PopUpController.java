package Project.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpController implements Initializable {

    public Label labelResult;
    public AnchorPane popUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelResult.setText(ShowTaskController.error);
    }
}
