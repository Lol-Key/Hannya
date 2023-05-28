package Project;
import Project.TasksList;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static TasksList tasksList;
    public static int sizeOfTasks;

    public static CurrentTask currentTask;

    private double xOffset = 0;
    private double yOffset = 0;

    private static Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ViewText"), 707, 435);
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
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        root = fxmlLoader.load();
        return root;
    }

    public static void main(String[] args) {
        tasksList = new TasksList();
        tasksList.takeNamesOfFiles();
        sizeOfTasks = tasksList.listOfTasks.size();
        currentTask = new CurrentTask();
        //TO DO ustaw staly pierwszy task wysiwetlany
        launch();
    }

}