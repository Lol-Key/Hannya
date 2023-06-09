package Project.Controllers;

import Project.App;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.fxmisc.richtext.CodeArea;
import static Project.Level.currentLevel;
import static Project.App.fxmlLoader;


public class CodeEditorController implements Initializable {
    @FXML
    public CodeArea codeArea;
    public static String text = "";
    private SyntaxHighlightingController syntaxHighlightingController = null; 
    @FXML
    private JFXButton button;
    @FXML
    private AnchorPane container;
    @FXML
    private void testWithMouseEvent(){}

    @FXML
    private void testWithOutMouseEvent(){}

    private void save(){
         text = codeArea.getText();
         if(currentLevel.lvl2){
             text = "";
         }
    }

    @FXML
    private void refreshSyntaxHighlighting() {
        syntaxHighlightingController.refreshSyntaxHighlighting(false);
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        syntaxHighlightingController = new SyntaxHighlightingController(codeArea, text);
    }
    @FXML
    public void loadSecond() throws IOException {
        save();

        fxmlLoader = new FXMLLoader(Objects.requireNonNull(App.class.getResource("ShowText.fxml")));

        Parent root = fxmlLoader.load();

        Scene scene = container.getScene();
        root.translateXProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) container.getScene().getRoot();

        Transit(root, parentContainer, container, - scene.getWidth() + 5);
    }

    protected static <T> void Transit(Parent root, AnchorPane parentContainer, T container, double npos) {
        System.out.println(root);
        System.out.println(container);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.25), kv);
        timeline.getKeyFrames().add(kf);
        KeyValue kv2 = new KeyValue(((Node) container).translateXProperty(), npos, Interpolator.EASE_OUT);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.25), kv2);
        timeline.getKeyFrames().add(kf2);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(container);
        });
        parentContainer.getChildren().add(root);
        timeline.play();
    }
}
