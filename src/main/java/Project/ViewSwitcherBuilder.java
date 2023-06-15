package Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ViewSwitcherBuilder {
    
    private Scene scene;

    private PositionedScene defaultScene;

    private ArrayList<PositionedScene> positionedScenes;

    private ArrayList<Emoji> emojis; //flubbed

    private double monitorHeight;

    private double monitorWidth;

    public ViewSwitcherBuilder(Scene scene, double monitorHeight, double monitorWidth, Parent root, int xPosition, int yPosition, String rootName) {
        this.scene = scene;
        this.monitorHeight = monitorHeight;
        this.monitorWidth = monitorWidth;
        if (root instanceof GridPane) {
            GridPane rootPane = (GridPane)root;
            rootPane.setPrefHeight(monitorHeight);
            rootPane.setPrefWidth(monitorWidth);
        }
        defaultScene = new PositionedScene(root, xPosition, yPosition, rootName);
        positionedScenes = new ArrayList<PositionedScene>();
	    emojis = new ArrayList<Emoji>(); //flubbed
        positionedScenes.add(defaultScene);
    }

    public void addScene(Parent root, int xPosition, int yPosition, String rootName) {
        if (root instanceof GridPane) {
            GridPane rootPane = (GridPane)root;
            rootPane.setPrefHeight(monitorHeight);
            rootPane.setPrefWidth(monitorWidth);
        }
        positionedScenes.add(new PositionedScene(root, xPosition, yPosition, rootName));
    }

    public void addEmoji(Emoji it) // flubbed
    {
	    emojis.add(it);
    }

    public ViewSwitcher build() {
        Collection<Node> positionedSceneRoots = new LinkedList<Node>();
        for (PositionedScene positionedScene : positionedScenes)
            positionedSceneRoots.add(positionedScene.getRoot());
	for (Emoji emoji : emojis)
	    positionedSceneRoots.add(emoji.elements);
        Group positionedSceneGroup = new Group(positionedSceneRoots);
        System.out.println(positionedSceneGroup.maxWidth(monitorHeight));
        ViewSwitcher viewSwitcher = new ViewSwitcher(scene, defaultScene, positionedSceneGroup, positionedScenes , emojis); //flubbed
        return viewSwitcher;
    }
}
