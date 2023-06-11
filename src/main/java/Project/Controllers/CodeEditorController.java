package Project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeEditorController implements Initializable {
    @FXML
    public CodeArea codeArea;
    public static CodeArea stcCodeArea;
    public static String text = "";
    private static SyntaxHighlightingController syntaxHighlightingController = null;

    @FXML
    private void refreshSyntaxHighlighting() {
        syntaxHighlightingController.refreshSyntaxHighlighting(false);
    }

    public void initialize(URL url, ResourceBundle rb) {
        stcCodeArea = codeArea;
        syntaxHighlightingController = new SyntaxHighlightingController(codeArea, text);
    }
}
