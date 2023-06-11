package Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewSwitcherBuilder {
    
    private Scene scene;

    private PositionedScene defaultScene;

    private ArrayList<PositionedScene> positionedScenes;

    public ViewSwitcherBuilder(Scene scene, Parent root, int xPosition, int yPosition, String rootName) {
        this.scene = scene;
        defaultScene = new PositionedScene(root, xPosition, yPosition, rootName);
        positionedScenes = new ArrayList<PositionedScene>();
        positionedScenes.add(defaultScene);
    }

    public void addScene(Parent root, int xPosition, int yPosition, String rootName) {
        positionedScenes.add(new PositionedScene(root, xPosition, yPosition, rootName));
    }

    public ViewSwitcher build() {
        Collection<Node> positionedSceneRoots = new LinkedList<Node>();
        for (PositionedScene positionedScene : positionedScenes)
            positionedSceneRoots.add(positionedScene.getRoot());
        Group positionedSceneGroup = new Group(positionedSceneRoots);
        ViewSwitcher viewSwitcher = new ViewSwitcher(scene, defaultScene, positionedSceneGroup, positionedScenes);
        return viewSwitcher;
    }
}
