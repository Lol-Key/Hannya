package Project;

import Project.Controllers.ShowTaskController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static int sizeOfTasks;

    private double xOffset = 0;
    private double yOffset = 0;

    private static Parent root;

    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ShowText"), 900, 600);
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        scene.setOnKeyPressed(keyEvent ->  {
            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ESCAPE) {
                System.exit(0);
            }
            if(Objects.requireNonNull(keyEvent.getCode()) == KeyCode.F1){
                ShowTaskController con = fxmlLoader.getController();
                System.out.println("tak");
                try {
                    con.loadSecond();
                } catch (IOException ignored) {}
            }

        });
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

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
