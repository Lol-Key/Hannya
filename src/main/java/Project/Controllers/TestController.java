package Project.Controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    public VBox anchorRoot;
    @FXML
    public JFXTextArea areaTest;
    @FXML
    public JFXTextArea areaRes;
    @FXML
    public JFXTextArea areaSolv;



    public void onTest(){

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaRes.setEditable(false);
        areaTest.setEditable(false);
        areaSolv.setEditable(false);
    }
}
