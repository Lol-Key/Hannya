package Project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeEditorController implements Initializable {
    @FXML
    public StyleClassedTextArea codeArea;
    public static StyleClassedTextArea stcCodeArea;
    public static String text = "";
    public static SyntaxHighlightingController syntaxHighlightingController = null;

    public static void stopEditing() {
        stcCodeArea.editableProperty().set(false);
    }

    public static void startEditing() {
        stcCodeArea.editableProperty().set(true);
    }

    @FXML
     void refreshSyntaxHighlighting() {
        syntaxHighlightingController.refreshSyntaxHighlighting(false);
    }

    public void initialize(URL url, ResourceBundle rb) {
        stcCodeArea = codeArea;
        syntaxHighlightingController = new SyntaxHighlightingController(codeArea, text);
    }
}
