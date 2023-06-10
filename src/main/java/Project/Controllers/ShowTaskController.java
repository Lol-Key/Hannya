package Project.Controllers;

import Project.*;
import com.jfoenix.controls.JFXToggleButton;
import com.sandec.mdfx.MarkdownView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import static Project.Level.currentLevel;
import static Project.Task.current;
import static javafx.scene.input.KeyCode.ESCAPE;



public class ShowTaskController implements Initializable  {

    @FXML
    private  VBox anchorRoot;
    @FXML
    private ScrollPane taskStatementScrollPane;
    @FXML
    private Label labelTaskName;
    @FXML
    private Button buttonRun;
    @FXML
    private Button buttonSkip;
    @FXML
    private Button buttonNieWiem;
    @FXML
    private JFXToggleButton toggleButton1;
    @FXML
    private JFXToggleButton toggleButton2;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String fileSeparator = System.getProperty("file.separator");
    public static String error = "";

    static String mdfxTxt = "";

    @FXML
    private void onRun()  {
        boolean ok;
        if(currentLevel.lvl1){
            CodeEditorController.text = "";
        }
        try{
            ok = TaskTester.runAll(current, CodeEditorController.text);
        } catch (GppFactory.GppCompilationException e) {
            error = "ERROR";
            loadPopUp();
            return;
        }
        System.out.println(ok);
        if(ok){
            error = "OK";
            loadPopUp();
        }
        else{
            error = "ANS";
            loadPopUp();

        }
    }

    @FXML
    private void onSkip() throws IOException {
        current = Task.randomTask();
        mdfxTxt = current.getStatement(); //IOUtils.toString(App.class.getResourceAsStream("sample.md"), "UTF-8");

            MarkdownView markdownView = new MarkdownView(mdfxTxt) {
                @Override
                protected List<String> getDefaultStylesheets() {
                    return List.of(App.class.getResource("sample.css").toExternalForm());
                }
            };
        markdownView.setMdString(mdfxTxt);
        markdownView.getStylesheets().add(App.class.getResource("sample.css").toExternalForm());
        taskStatementScrollPane.setContent(markdownView);
	    taskStatementScrollPane.setFitToWidth(true);
	    taskStatementScrollPane.setFitToHeight(true);

        labelTaskName.setText(current.getDirectory().getName());
        CodeEditorController.text = "";
        currentLevel.toFalse();
    }

    @FXML
    private void onSwitch1(){
        currentLevel.switchLvl1();
    };
    @FXML
    private void onSwitch2(){
        currentLevel.switchLvl2();
    };

    @FXML
    private void onNieWiem() throws IOException{
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
        try {
            if(mdfxTxt.equals(""))mdfxTxt = IOUtils.toString(App.class.getResourceAsStream("sample.md"), "UTF-8");

            MarkdownView markdownView = new MarkdownView(mdfxTxt) {
                @Override
                protected List<String> getDefaultStylesheets() {
                    return List.of(App.class.getResource("sample.css").toExternalForm());
                }
            };
        markdownView.setMdString(mdfxTxt);
        markdownView.getStylesheets().add(App.class.getResource("sample.css").toExternalForm());
	    markdownView.setFillWidth(false);

	    taskStatementScrollPane.setContent(markdownView);
	    taskStatementScrollPane.setFitToWidth(true);
	    taskStatementScrollPane.setFitToHeight(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        labelTaskName.setText(current.getDirectory().getName());
        if(currentLevel.lvl1) {
            toggleButton1.setSelected(true);
        }
        if(currentLevel.lvl2) {
            toggleButton2.setSelected(true);
        }
    }

    private void loadPopUp() {
        try {
            Parent root1 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(  "PopUp.fxml")));
            Stage s = new Stage();
            s.initStyle(StageStyle. UNDECORATED);
            root1.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root1.setOnMouseDragged(event -> {
                s.setX(event.getScreenX() - xOffset);
                s.setY(event.getScreenY() - yOffset);
            });
            Scene ss = new Scene (root1);
            ss.setOnKeyPressed(keyEvent ->  {
                if (Objects.requireNonNull(keyEvent.getCode()) == ESCAPE) {
                    s.close();
                }

            });
            s.setScene (ss);
            s.show();
        } catch (Exception ex) {
            System.out.println("Cant load new window");
        }
    }
}
