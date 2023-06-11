package Project.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static Project.App.mainScene;
import static Project.Level.currentLevel;

public class MenuController implements Initializable {
    public VBox anchorRoot;

    public void onFKey(ActionEvent event){
        JFXToggleButton bt = (JFXToggleButton) event.getSource();
        String st = bt.getId();
        int idx = st.charAt(st.length()-1) - '0';
        currentLevel.switchLvl(idx-1);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}



//bomba
//czasowka
//nie korzystnaie  z bilblioteczki
//pijany mode
//jeden submit
//nie mozesz testowac