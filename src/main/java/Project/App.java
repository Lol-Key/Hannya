package Project;

import Project.Controllers.CodeEditorController;
import Project.Controllers.ShowTaskController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import com.sandec.mdfx.MarkdownView;

public class App extends Application {

    public static Scene scene;
    public static int sizeOfTasks;

    private double xOffset = 0;
    private double yOffset = 0;

    private static Parent root;

    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("ShowText"));
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(scene);
        Image img = new Image(App.class.getResource("loadingScreen.jpg").toExternalForm());
        ImageView imgView = new ImageView(img);
        ImageView imgView2 = new ImageView(img);
        imgView.setBlendMode(BlendMode.MULTIPLY);
        imgView.setFitWidth(1200);
        imgView.setFitHeight(800);
        imgView2.setFitWidth(1200);
        imgView2.setFitHeight(800);
        Group loadingRoot = new Group(imgView2, imgView);
        scene.setRoot(loadingRoot);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), imgView);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1000 * 1000);
        ft.setAutoReverse(true);
        ft.play();
        scene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            if (scene.getRoot() != root) {
                ft.stop();
                double reachedOpacity = imgView.opacityProperty().doubleValue();
                FadeTransition ft2 = new FadeTransition(Duration.millis(3000 * reachedOpacity), imgView);
                ft2.setFromValue(reachedOpacity);
                ft2.setToValue(0.0);
                ft2.play();
                ft2.setOnFinished(t -> {
                    quitLoadingScreen();
                });
            }
        });
        stage.show();
    }

    private void quitLoadingScreen() {
        scene.setRoot(root);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, KE ->  {
            if (Objects.requireNonNull(KE.getCode()) == KeyCode.ESCAPE) {
                System.exit(0);
            }
            if(Objects.requireNonNull(KE.getCode()) == KeyCode.ALT) {
                System.out.println("F1 PRESSED");
                if (scene == null)
                    System.out.println("Scene is null");
                else
                System.out.println("Scene is not null");
                Initializable con = (Initializable)fxmlLoader.getController();
                System.out.println(con);
                try {
                    if (con instanceof ShowTaskController) {
                        ShowTaskController STcon = (ShowTaskController)con;
                        STcon.loadSecond();
                    } else {
                        CodeEditorController CEcon = (CodeEditorController)con;
                        CEcon.loadSecond();
                    }
                } catch (IOException ignored) {}
            }
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        root = fxmlLoader.load();
        return root;
    }

    public static void main(String[] args) {

        launch();

    }

}
