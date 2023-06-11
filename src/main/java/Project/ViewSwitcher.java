package Project;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;

public class ViewSwitcher {

    private Random rng;

    private Scene scene;

    private boolean finalized = true;

    private PositionedScene currentScene;

    private Group positionedSceneGroup;

    private ArrayList<PositionedScene> positionedScenes;

    public ViewSwitcher(Scene scene, PositionedScene defaultScene, Group positionedSceneGroup, ArrayList<PositionedScene> positionedScenes) {
        this.scene = scene;
        this.currentScene = defaultScene;
        this.positionedSceneGroup = positionedSceneGroup;
        this.positionedScenes = positionedScenes;
        this.scene.setRoot(this.positionedSceneGroup);
        currentScene.getRoot().translateXProperty().set(0);
        currentScene.getRoot().translateYProperty().set(0);
        for (PositionedScene positionedScene : this.positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot()) {
                positionedScene.getRoot().translateXProperty().set(this.scene.getWidth());
                positionedScene.getRoot().translateYProperty().set(this.scene.getHeight());
            }
        rng = new Random(System.currentTimeMillis());
    }

    private void finalizeSwitch() {
        finalized = true;
    }

    private void switchView(PositionedScene newScene, int xChange, int yChange) {
        if (!finalized)
            return;
        finalized = false;
        System.out.println("Switching scenes : X(" + xChange + ") Y(" + yChange + ")");
        System.out.println("Going from" + currentScene + " to " + newScene);
        for (PositionedScene positionedScene : positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot() &&
                positionedScene.getRoot() != newScene.getRoot()
            ) {
                positionedScene.getRoot().translateXProperty().set(scene.getWidth());
                positionedScene.getRoot().translateYProperty().set(scene.getHeight());
            }

            newScene.getRoot().translateXProperty().set(-xChange * scene.getWidth());
            newScene.getRoot().translateYProperty().set(-yChange * scene.getHeight());

            Timeline timeline = new Timeline();

            KeyValue newSceneXKeyValue = new KeyValue(newScene.getRoot().translateXProperty(), 0, Interpolator.EASE_OUT);
            KeyValue newSceneYKeyValue = new KeyValue(newScene.getRoot().translateYProperty(), 0, Interpolator.EASE_OUT);
            KeyFrame newSceneXKeyFrame = new KeyFrame(Duration.seconds(0.3), newSceneXKeyValue);
            KeyFrame newSceneYKeyFrame = new KeyFrame(Duration.seconds(0.3), newSceneYKeyValue);

            timeline.getKeyFrames().add(newSceneXKeyFrame);
            timeline.getKeyFrames().add(newSceneYKeyFrame);
            
            KeyValue currentSceneXKeyValue = new KeyValue(currentScene.getRoot().translateXProperty(), xChange * scene.getWidth(), Interpolator.EASE_OUT);
            KeyValue currentSceneYKeyValue = new KeyValue(currentScene.getRoot().translateYProperty(), yChange * scene.getHeight(), Interpolator.EASE_OUT);
            KeyFrame currentSceneXKeyFrame = new KeyFrame(Duration.seconds(0.3), currentSceneXKeyValue);
            KeyFrame currentSceneYKeyFrame = new KeyFrame(Duration.seconds(0.3), currentSceneYKeyValue);

            timeline.getKeyFrames().add(currentSceneXKeyFrame);
            timeline.getKeyFrames().add(currentSceneYKeyFrame);

            timeline.setOnFinished(t -> {
                finalizeSwitch();
            });

            timeline.play();

            currentScene = newScene;
    }

    public void moveUp() {
        PositionedScene closestGreaterScene = null;
        PositionedScene closestSmallerScene = null;
        for (PositionedScene positionedScene : positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot() && positionedScene.getX() == currentScene.getX()) {
                if (positionedScene.getY() > currentScene.getY()) {
                    if (closestGreaterScene == null || positionedScene.getY() > closestGreaterScene.getY())
                        closestGreaterScene = positionedScene;
                } else {
                    if (closestSmallerScene == null || positionedScene.getY() > closestSmallerScene.getY())
                        closestSmallerScene = positionedScene;
                }
            }
        if (closestSmallerScene != null)
            switchView(closestSmallerScene, 0, 1);
        /*else if (closestGreaterScene != null)
            switchView(closestGreaterScene, 0, 1);*/
    }

    public void moveDown() {
        PositionedScene closestGreaterScene = null;
        PositionedScene closestSmallerScene = null;
        for (PositionedScene positionedScene : positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot() && positionedScene.getX() == currentScene.getX()) {
                if (positionedScene.getY() > currentScene.getY()) {
                    if (closestGreaterScene == null || positionedScene.getY() < closestGreaterScene.getY())
                        closestGreaterScene = positionedScene;
                } else {
                    if (closestSmallerScene == null || positionedScene.getY() < closestSmallerScene.getY())
                        closestSmallerScene = positionedScene;
                }
            }
        if (closestGreaterScene != null)
            switchView(closestGreaterScene, 0, -1);
        /*else if (closestSmallerScene != null)
            switchView(closestSmallerScene, 0, -1);*/
    }

    public void moveLeft() {
        PositionedScene closestGreaterScene = null;
        PositionedScene closestSmallerScene = null;
        for (PositionedScene positionedScene : positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot() && positionedScene.getY() == currentScene.getY()) {
                if (positionedScene.getX() > currentScene.getX()) {
                    if (closestGreaterScene == null || positionedScene.getX() > closestGreaterScene.getX())
                        closestGreaterScene = positionedScene;
                } else {
                    if (closestSmallerScene == null || positionedScene.getX() > closestSmallerScene.getX())
                        closestSmallerScene = positionedScene;
                }
            }
        if (closestSmallerScene != null)
            switchView(closestSmallerScene, 1, 0);
        /*else if (closestGreaterScene != null)
            switchView(closestGreaterScene, 1, 0);*/
    }

    public void moveRight() {
        PositionedScene closestGreaterScene = null;
        PositionedScene closestSmallerScene = null;
        for (PositionedScene positionedScene : positionedScenes)
            if (positionedScene.getRoot() != currentScene.getRoot() && positionedScene.getY() == currentScene.getY()) {
                if (positionedScene.getX() > currentScene.getX()) {
                    if (closestGreaterScene == null || positionedScene.getX() < closestGreaterScene.getX())
                        closestGreaterScene = positionedScene;
                } else {
                    if (closestSmallerScene == null || positionedScene.getX() < closestSmallerScene.getX())
                        closestSmallerScene = positionedScene;
                }
            }
        if (closestGreaterScene != null)
            switchView(closestGreaterScene, -1, 0);
        /*else if (closestSmallerScene != null)
            switchView(closestSmallerScene, -1, 0);*/
    }

    public void moveRandom() {
        int position = -1;
        for (int i = 0; i < 10; ++ i) {
            position = rng.nextInt(positionedScenes.size());
            if (positionedScenes.get(position).getRoot() != currentScene.getRoot())
                break;
        }
        if (position != -1) {
            int dir = rng.nextInt(4);
            int xChange = 1, yChange = 0;
            if (dir == 0) {
                xChange = -1;
                yChange = 0;
            } else if (dir == 1) {
                xChange = 0;
                yChange = 1;
            } else if (dir == 2) {
                xChange = 0;
                yChange = -1;
            }
            switchView(positionedScenes.get(position), xChange, yChange);
        }
    }
}
