package Project.Controllers;
import Project.App;
import Project.Task;
import Project.TasksList;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Random;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import static Project.App.*;

public class ViewTextController implements Initializable {
    @FXML
    private Label menu;

    @FXML
    private  Label menuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private Label taskNumber;

    @FXML
    private Label taskText;

    @FXML
    private ImageView exitButton;

    @FXML
    private Label in;

    @FXML
    private Label out;

    public void changeCurrentTask(ActionEvent event){
        //currentTask.generateRandom();
        //taskNumber.setText(currentTask.nameOfTheTask);
        //taskText.setText(currentTask.textFromTask);
//        in.setText(currentTask.inPath);
//        out.setText(currentTask.outPath);
    }

    @FXML
    private void onSwitch() throws IOException {
        App.setRoot("ViewCode");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(0);
        ViewCodeController.movingLabel(menu, slider, menuBack);
    }
}
