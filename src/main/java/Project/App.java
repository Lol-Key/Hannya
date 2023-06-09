package Project;

import Project.Controllers.CodeEditorController;
import Project.Controllers.ShowTaskController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static Scene scene;
    public static int sizeOfTasks;

    private double xOffset = 0;
    private double yOffset = 0;

    private static Parent root;

    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ShowText"));
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, KE ->  {
            if (Objects.requireNonNull(KE.getCode()) == KeyCode.ESCAPE) {
                System.exit(0);
            }
            if(Objects.requireNonNull(KE.getCode()) == KeyCode.ALT) {
                System.out.println("F1 PRESSED");
                if (scene == null)
                    System.out.println("Scene is null");
                else
                System.out.println("Scene is not null");
                Initializable con = (Initializable)fxmlLoader.getController();
                System.out.println(con);
                try {
                    if (con instanceof ShowTaskController) {
                        ShowTaskController STcon = (ShowTaskController)con;
                        STcon.loadSecond();
                    } else {
                        CodeEditorController CEcon = (CodeEditorController)con;
                        CEcon.loadSecond();
                    }
                } catch (IOException ignored) {}
            }

        });
        stage.setScene(scene);
        stage.show();
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
