package Project;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {

    public static Scene mainScene;
    double xOffset;
    double yOffset;

    public static boolean duringSwitching = false;
    public static boolean fullyLoaded = false;
    public static Parent codeEditorRoot;
    public static Parent showTaskRoot;
    public static ViewSwitcher viewSwitcher;
    public static ViewSwitcherBuilder viewSwitcherBuilder;

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);

//        showTaskRoot.setOnMousePressed(event -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        });
//        showTaskRoot.setOnMouseDragged(event -> {
//            stage.setX(event.getScreenX() - xOffset);
//            stage.setY(event.getScreenY() - yOffset);
//        });
        Media yeat = new Media(Objects.requireNonNull(App.class.getResource("yeat.mp3")).toExternalForm());
        Image img = new Image(Objects.requireNonNull(App.class.getResource("newLoadingScreen.jpg")).toExternalForm());
        ImageView imgView = new ImageView(img);
        ImageView imgView2 = new ImageView(img);
        imgView.setBlendMode(BlendMode.MULTIPLY);
        imgView.setFitWidth(1200);
        imgView.setFitHeight(800);
        imgView2.setFitWidth(1200);
        imgView2.setFitHeight(800);
        Group loadingRoot = new Group(imgView2, imgView);
        mainScene = new Scene(loadingRoot);

        // Populating scenes (MUST be after scene loaded)
        codeEditorRoot = loadFXML("CodeEditor");
        showTaskRoot = loadFXML("TaskStatment");
        viewSwitcherBuilder = new ViewSwitcherBuilder(mainScene, codeEditorRoot, 0, 0);
        viewSwitcherBuilder.addScene(showTaskRoot, 1, 0);

        mainScene.setRoot(loadingRoot);
        stage.setScene(mainScene);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), imgView);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1000 * 1000);
        ft.setAutoReverse(true);
        ft.play();
        MediaPlayer howItGo = new MediaPlayer(yeat);
        howItGo.setVolume(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7000),new KeyValue(howItGo.volumeProperty(), 1)));
        howItGo.play();
        timeline.play();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                quitSession(loadingRoot, ft, imgView, howItGo);
//            }
//        }, 2500);

        mainScene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            quitSession(loadingRoot, ft, imgView, howItGo);
        });
        stage.show();
    }

    private void quitSession(Group loadingRoot, FadeTransition ft, ImageView imgView, MediaPlayer howItGo) {
        if (loadingRoot == mainScene.getRoot()) {
            ft.stop();
            double reachedOpacity = imgView.opacityProperty().doubleValue();
            FadeTransition ft2 = new FadeTransition(Duration.millis(7000 * reachedOpacity), imgView);
            ft2.setFromValue(reachedOpacity);
            ft2.setToValue(0.0);
            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(5000),new KeyValue(howItGo.volumeProperty(), 0)));
            timeline2.play();
            ft2.play();
            ft2.setOnFinished(t -> {
                quitLoadingScreen();
            });
        }
    }

    private void quitLoadingScreen() {
        viewSwitcher = viewSwitcherBuilder.build();
        mainScene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            if (KE.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
            if (KE.getCode() == KeyCode.ALT /*&& fullyLoaded*/) {
                if (!duringSwitching) {
                    //duringSwitching = true;
                    viewSwitcher.moveRandom();
                }
            }
        });
        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                fullyLoaded = true;
//            }
//        }, 1000);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml + ".fxml")));
    }
}
