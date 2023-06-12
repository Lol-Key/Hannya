package Project;

import Project.Controllers.TaskStatementController;
import Project.Controllers.TestController;
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
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static Project.Level.currentLevel;

public class App extends Application {

    public static Scene mainScene;
    double xOffset;
    double yOffset;
    int i = 0;

    public static ViewSwitcher viewSwitcher;
    public static ViewSwitcherBuilder viewSwitcherBuilder;
    private boolean duringSkip = false;
    private boolean duringCompile = false;
    private boolean duringTest = false;

    final KeyCombination UP_EVENT = new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN);
    final KeyCombination DOWN_EVENT = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN);
    final KeyCombination LEFT_EVENT = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN);
    final KeyCombination RIGHT_EVENT = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN);
    final KeyCombination SUBMIT_EVENT = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
    final KeyCombination TEST_EVENT = new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN);
    final KeyCombination SKIP_EVENT = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN);

    final KeyCombination FUNNY_EVENT = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN);

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hannya");
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
        imgView.setFitWidth(1280);
        imgView.setFitHeight(720);
        imgView2.setFitWidth(1280);
        imgView2.setFitHeight(720);
        Group loadingRoot = new Group(imgView2, imgView);
        mainScene = new Scene(loadingRoot);

        // Populating scenes (MUST be after scene loaded)
        viewSwitcherBuilder = new ViewSwitcherBuilder(mainScene, loadFXML("Menu"), 1, 0, "Menu");
        viewSwitcherBuilder.addScene(loadFXML("TaskStatement"), 2, 1, "TaskStatement");
        viewSwitcherBuilder.addScene(loadFXML("Test"), 0, 1, "Test");
        viewSwitcherBuilder.addScene(loadFXML("CodeEditor"), 1, 1, "CodeEditor");
        viewSwitcherBuilder.addScene(loadFXML("Stats"), 1, 2, "Stats");
	
	// Emojis
	viewSwitcherBuilder.addEmoji(new CryingLaughingEmoji());
    viewSwitcherBuilder.addEmoji(new FashionDemonEmoji());
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

        mainScene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            quitSession(loadingRoot, ft, imgView, howItGo);
        });
        stage.show();
    }

    private void quitSession(Group loadingRoot, FadeTransition ft, ImageView imgView, MediaPlayer howItGo) {
        if (loadingRoot == mainScene.getRoot()) {
            ft.stop();
            double reachedOpacity = imgView.opacityProperty().doubleValue();
            FadeTransition ft2 = new FadeTransition(Duration.millis(5000 * reachedOpacity), imgView);
            ft2.setFromValue(reachedOpacity);
            ft2.setToValue(0.0);
            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(7000 * reachedOpacity),new KeyValue(howItGo.volumeProperty(), 0)));
            timeline2.play();
            ft2.play();
            timeline2.setOnFinished(t -> {
                howItGo.stop();
                quitLoadingScreen();
            });
        }
    }

    private void quitLoadingScreen() {
        viewSwitcher = viewSwitcherBuilder.build();
        mainScene.addEventFilter(KeyEvent.KEY_RELEASED, KE -> {
            if(currentLevel.isMarked(5) && (UP_EVENT.match(KE) || DOWN_EVENT.match(KE) || LEFT_EVENT.match(KE) || RIGHT_EVENT.match(KE))){
                viewSwitcher.moveRandom();
            }
            else if(currentLevel.isMarked(1) && TEST_EVENT.match(KE)){
                return;
            }
            else if (KE.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }else if (FUNNY_EVENT.match(KE)){ viewSwitcher.showEmojiByIndex(0); }
	    else if (UP_EVENT.match(KE)) {
                viewSwitcher.moveUp();
            } else if (DOWN_EVENT.match(KE)) {
                viewSwitcher.moveDown();
            } else if (LEFT_EVENT.match(KE)) {
                viewSwitcher.moveLeft();
            } else if (RIGHT_EVENT.match(KE)) {
                viewSwitcher.moveRight();
            } else if (SUBMIT_EVENT.match(KE) && !duringCompile) {
                duringCompile = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        duringCompile = false;
                    }
                }, 1000);
                TestController.submit();
            } else if (TEST_EVENT.match(KE) && !duringTest) {
                duringTest = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        duringTest = false;
                    }
                }, 1000);
                TestController.test();
            }else if (SKIP_EVENT.match(KE) && !duringSkip){
                duringSkip = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        duringSkip = false;
                    }
                }, 1000);
                TaskStatementController.skip();
                if(currentLevel.isMarked(2)){
                    if(currentLevel.startedTimer){
                        currentLevel.timer.cancel();
                        currentLevel.startedTimer = false;
                    }else{
                        currentLevel.startTimer();
                        currentLevel.startedTimer = true;
                    }
                }
            }
        });
    }

    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml + ".fxml")));
    }
}

//
