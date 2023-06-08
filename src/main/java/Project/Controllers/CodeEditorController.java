package Project.Controllers;

import Project.App;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static Project.App.fxmlLoader;
import static Project.Level.currentLevel;


public class CodeEditorController implements Initializable {
    @FXML
    public TextArea mainTextArea;
    public JFXButton buttonNieWiem;
    public static String text = "";
    @FXML
    private JFXButton button;
    @FXML
    private AnchorPane container;
    @FXML
    private void testWithMouseEvent(){}

    @FXML
    private void testWithOutMouseEvent(){}

    private void save(){
         text = mainTextArea.getText();
         if(currentLevel.lvl2){
             text = "";
         }
    }


    public void initialize(URL url, ResourceBundle rb) {
        mainTextArea.setText(text);
    }
    @FXML
     public void loadSecond() throws IOException {
        save();
        fxmlLoader = new FXMLLoader(App.class.getResource(  "ShowTask.fxml"));

        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(  "ShowText.fxml")));
        Scene scene = container.getScene();
        root.translateXProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) container.getScene().getRoot();

        Transit(root, parentContainer, container);

    }

    protected static <T> void Transit(Parent root, AnchorPane parentContainer, T container) {
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(container);
        });
        timeline.play();
    }


}
