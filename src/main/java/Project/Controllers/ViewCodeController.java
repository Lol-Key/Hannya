package Project.Controllers;
import Project.CurrentTask;
import Project.App;
import Project.Task;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Project.App.currentTask;

public class ViewCodeController implements Initializable {
    @FXML
    public Button compileButton;
    @FXML
    public TextArea mainTextArea;
    public ImageView exitButton;
    @FXML
    private Label menu;
    @FXML
    private  Label menuBack;
    @FXML
    private AnchorPane slider;
    @FXML
    public Label resultLabel;

    private final String fileSeparator = System.getProperty("file.separator");
    private final String absolutePath = System.getProperty("user.dir")+fileSeparator+"res"+fileSeparator;

//    private MainModel model;
//    MainController(MainModel model){
//        this.model = model;
//    }

    @FXML
    private void onCompile(){
        //System.out.println("tak");
        String code = mainTextArea.getText();
        String res;
        if(code ==  null || code.equals("")){
            res = "ERROR";
            resultLabel.setText(res);
            return;
        }
        //System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        Task task = new Task(absolutePath+"tasks"+fileSeparator+currentTask.nameOfTheTask+fileSeparator);
        if(task.Test(code)){
            res = "OK";
           resultLabel.setText(res);
            System.out.println("ok");
        }else{
            res = "ERROR";
            resultLabel.setText(res);
            System.out.println("error");
        }
    }
    @FXML
    private void onSwitch() throws IOException {
        App.setRoot("ViewText");
    }

    public void changeCurrentTask(ActionEvent event){
        currentTask.generateRandom();
    }
    public void onSave() {
        //TO DO zmien gdzie zapisuje plik  nie do folderu task tylko jakises submit
        File newFile = new File(absolutePath+"solve.txt");
        if(newFile.exists()){
            newFile.delete();
        }
        try{
            newFile.createNewFile();
        }catch(IOException e){
            System.out.println("nie utworzono pliku");
        }
    }

    public void onHelp() {
        //TO DO:
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        exitButton.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(0);
        movingLabel(menu, slider, menuBack);
    }

    static void movingLabel(Label menu, AnchorPane slider, Label menuBack) {
        menu.setOnMouseClicked(event -> {
            TranslateTransition move = new TranslateTransition();
            move.setDuration(Duration.seconds(0.2));
            move.setNode(slider);
            move.setToX(0);
            move.play();
            slider.setTranslateX(-176);
            move.setOnFinished(ActionEvent -> {
                menu.setVisible(false);
                menuBack.setVisible(true);
            });

        });


        menuBack.setOnMouseClicked(event -> {
            TranslateTransition move = new TranslateTransition();
            move.setDuration(Duration.seconds(0.2));
            move.setNode(slider);
            move.setToX(-176);
            move.play();
            slider.setTranslateX(0);
            move.setOnFinished(ActionEvent -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });

        });
    }
}
