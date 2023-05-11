package Project.Controllers;

import java.io.IOException;

import Project.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class MainController {
    @FXML
    private TextArea areaText;


//    private MainModel model;
//    MainController(MainModel model){
//        this.model = model;
//    }

    @FXML
    private void onCompile(){
        System.out.println("tal");
        String code = areaText.getText();

    }
    @FXML
    private void onSwitch() throws IOException {
        App.setRoot("description");
    }
}
