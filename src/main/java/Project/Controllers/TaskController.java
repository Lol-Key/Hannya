package Project.Controllers;

import Project.App;
import Project.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;

public class TaskController {
    @FXML
    public Label resultLabel;
    @FXML
    private TextArea areaText;
    private final String fileSeparator = System.getProperty("file.separator");
    private final String absolutePath = System.getProperty("user.dir")+fileSeparator+"res"+fileSeparator;

//    private MainModel model;
//    MainController(MainModel model){
//        this.model = model;
//    }

    @FXML
    private void onCompile(){
        //System.out.println("tak");
        String code = areaText.getText();
        String res;
        if(code ==  null || code.equals("")){
            res = "ERROR";
            resultLabel.setText(res);
            return;
        }
        //System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        Task task = new Task(absolutePath+"tasks"+fileSeparator+"tree-permutations"+fileSeparator);
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
        App.setRoot("description");
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
}
