package Project.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.CodeArea;
import static Project.Level.currentLevel;

public class CodeEditorController implements Initializable {
    @FXML
    public CodeArea codeArea;
    public static CodeArea stcCodeArea;
    public static String text = "";
    private static SyntaxHighlightingController syntaxHighlightingController = null; 
    @FXML
    private JFXButton button;
    @FXML
    private void testWithMouseEvent(){}

    @FXML
    private void testWithOutMouseEvent(){}

    private static void save() {
         text = stcCodeArea.getText();
         if(currentLevel.lvl2){
             text = "";
         }
    }

    @FXML
    private void refreshSyntaxHighlighting() {
        syntaxHighlightingController.refreshSyntaxHighlighting(false);
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        stcCodeArea = codeArea;
        syntaxHighlightingController = new SyntaxHighlightingController(codeArea, text);
    }
}
