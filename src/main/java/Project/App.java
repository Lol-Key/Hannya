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
    public static Parent currentRoot;
    public static Parent codeEditorRoot;
    public static Parent showTaskRoot;
    public static Group rootGroup;

    @Override
    public void start(Stage stage) throws IOException {
        codeEditorRoot = loadFXML("CodeEditor");
        showTaskRoot = loadFXML("TaskStatment");
        rootGroup = new Group(codeEditorRoot, showTaskRoot);
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
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                quitSession(loadingRoot, ft, imgView, howItGo);
            }
        }, 2500);

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
        mainScene.setRoot(rootGroup);
        showTaskRoot.translateXProperty().set(mainScene.getWidth());
        currentRoot = codeEditorRoot;
        mainScene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            if (KE.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
            if (KE.getCode() == KeyCode.ALT && fullyLoaded) {
                if (!duringSwitching) {
                    duringSwitching = true;
                    loadSecond();
                }
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fullyLoaded = true;
            }
        }, 1000);
    }

    void loadSecond() {
        double codeEditorPos = 0.0;
        double showTaskPos = 0.0;
        if (currentRoot == codeEditorRoot) {
            showTaskRoot.translateXProperty().set(mainScene.getWidth());
            codeEditorPos = -mainScene.getWidth();
            currentRoot = showTaskRoot;
        } else {
            codeEditorRoot.translateXProperty().set(-mainScene.getWidth());
            showTaskPos = mainScene.getWidth();
            currentRoot = codeEditorRoot;
        }

        sceneTranslation(codeEditorPos, showTaskPos);
    }

    protected static <T> void sceneTranslation(double codeEditorPos, double showTaskPos) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(codeEditorRoot.translateXProperty(), codeEditorPos, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        KeyValue kv2 = new KeyValue(showTaskRoot.translateXProperty(), showTaskPos, Interpolator.EASE_OUT);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.3), kv2);
        timeline.getKeyFrames().add(kf2);

        timeline.play();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                duringSwitching = false;
            }
        }, 300);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml + ".fxml")));
    }
}
