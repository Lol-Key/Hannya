package Project.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import static Project.App.mainScene;
import static Project.Controllers.CodeEditorController.syntaxHighlightingController;
import static Project.Level.currentLevel;

public class MenuController implements Initializable {
    public VBox anchorRoot;

    public void onFKey(ActionEvent event){
        JFXToggleButton bt = (JFXToggleButton) event.getSource();
        String st = bt.getId();
        int idx = st.charAt(st.length()-1) - '0';
        currentLevel.switchLvl(idx-1);
        if(idx-1 == 4 && currentLevel.isMarked(4)){
            currentLevel.submit = false;
        }
        if(idx-1 == 0 && currentLevel.isMarked(0)){
            syntaxHighlightingController.disableSuggestions();
        }
        if(idx-1 == 0 && !currentLevel.isMarked(0)){
            syntaxHighlightingController.enableSuggestions();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

