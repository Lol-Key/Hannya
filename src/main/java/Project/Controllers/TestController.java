package Project.Controllers;

import Project.FileHelper;
import Project.GppFactory;
import Project.TaskTester;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

import static Project.Controllers.CodeEditorController.stcCodeArea;
import static Project.Controllers.CodeEditorController.text;
import static Project.Task.current;


public class TestController implements Initializable {
    @FXML
    public VBox anchorRoot;
    @FXML
    public JFXTextArea areaTest;
    @FXML
    public JFXTextArea areaRes;
    @FXML
    public JFXTextArea areaSolv;

    public static JFXTextArea stAreaTest;
    public static JFXTextArea stAreaSolv;
    public static JFXTextArea stAreaRes;

    public static File test = null;
    public static String testContent = "";
    public static String myAns = "";
    public static String solAns = "";


    public static void submit(){
        String textFromArea = stcCodeArea.getText();
        try{
            TaskTester.runAll(current, textFromArea);
        }catch(GppFactory.GppCompilationException gppExp){
            System.out.println("hjkdafhkjdcs");
            stAreaRes.setText("Compilation Error");
            stAreaSolv.setText("");
            stAreaTest.setText("");
            return;
        }catch (TaskTester.WrongAnwserException ansExp){
            saveTextToAreas(ansExp);
            return;
        }
        stAreaRes.setText("OK");
        stAreaSolv.setText("");
        stAreaTest.setText("");
        test = null;
        myAns = "";
        solAns = "";
        testContent = "";
    }

    public static void test(){
        if(test == null){
            return;
        }
        String textFromArea = stcCodeArea.getText();
        try{
            TaskTester.userTest(current, textFromArea, test);
        }catch(GppFactory.GppCompilationException gppExp){
            stAreaRes.setText("Compilation Error");
            stAreaSolv.setText("");
            stAreaTest.setText("");
            return;
        }catch (TaskTester.WrongAnwserException ansExp){
            saveTextToAreas(ansExp);
            return;
        }
        stAreaRes.setText("OK");
        stAreaSolv.setText("");
        stAreaTest.setText("");
        test = null;
        myAns = "";
        solAns = "";
        testContent = "";
        return;
    }

    private static void saveTextToAreas(TaskTester.WrongAnwserException ansExp) {
        test = ansExp.test;
        myAns = ansExp.diff.getFirst();
        solAns = ansExp.diff.getSecond();
        testContent = FileHelper.fileToString(test);
        stAreaTest.setText(testContent);
        stAreaSolv.setText(solAns);
        stAreaRes.setText(myAns);
        return;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stAreaTest = areaTest;
        stAreaRes = areaRes;
        stAreaSolv = areaSolv;
        areaRes.setEditable(false);
        areaTest.setEditable(false);
        areaSolv.setEditable(false);
    }
}

