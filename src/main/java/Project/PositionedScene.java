package Project;

import javafx.scene.Parent;

public class PositionedScene {
    
    private Parent root;
    private int xPosition;
    private int yPosition;
    private String name;

    PositionedScene(Parent root, int xPosition, int yPosition, String name) {
        this.name = name;
        this.root = root;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Parent getRoot() {
        return root;
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }
    
    public String toString() {
        return name;
    }
}
