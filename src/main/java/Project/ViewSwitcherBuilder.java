package Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class ViewSwitcherBuilder {
    
    private Scene scene;

    private PositionedScene defaultScene;

    private ArrayList<PositionedScene> positionedScenes;

    private ArrayList<Emoji> emojis;

    private double monitorHeight;

    private double monitorWidth;

    public ViewSwitcherBuilder(Scene scene, double monitorHeight, double monitorWidth, Parent root, int xPosition, int yPosition, String rootName) {
        this.scene = scene;
        this.monitorHeight = monitorHeight;
        this.monitorWidth = monitorWidth;
        defaultScene = new PositionedScene(root, xPosition, yPosition, rootName);
        positionedScenes = new ArrayList<PositionedScene>();
	    emojis = new ArrayList<Emoji>();
        positionedScenes.add(defaultScene);
    }

    public void addScene(Parent root, int xPosition, int yPosition, String rootName) {
        positionedScenes.add(new PositionedScene(root, xPosition, yPosition, rootName));
    }

    public void addEmoji(Emoji it) {
	    emojis.add(it);
    }

    public ViewSwitcher build() {
        Collection<Node> positionedSceneRoots = new LinkedList<Node>();
        for (PositionedScene positionedScene : positionedScenes) {
            positionedSceneRoots.add(positionedScene.getRoot());
            if (positionedScene.getRoot() instanceof GridPane) {
                GridPane gridPane = (GridPane)positionedScene.getRoot();
                gridPane.setPrefHeight(monitorHeight);
                gridPane.setPrefWidth(monitorWidth);
            }
        }
	    for (Emoji emoji : emojis)
	        positionedSceneRoots.add(emoji.elements);
        Group positionedSceneGroup = new Group(positionedSceneRoots);
        ViewSwitcher viewSwitcher = new ViewSwitcher(scene, defaultScene, positionedSceneGroup, positionedScenes, emojis);
        return viewSwitcher;
    }
}
