package Project;

import Project.Controllers.CodeEditorController;
import Project.Controllers.ShowTaskController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import com.sandec.mdfx.MarkdownView;

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
        //markdown example for fjuciur refernece
        /*String mdfxTxt = IOUtils.toString(App.class.getResourceAsStream("sample.md"), "UTF-8");

        MarkdownView markdownView = new MarkdownView(mdfxTxt) {
            @Override
            protected List<String> getDefaultStylesheets() {
                return List.of(App.class.getResource("sample.css").toExternalForm());
            }

            @Override
            public void setLink(Node node, String link, String description) {
                System.out.println("setLink: " + link);
                node.setCursor(Cursor.HAND);
                node.setOnMouseClicked(e -> {
                    System.out.println("link: " + link);
                });
            }
        };
        TextArea textArea = new TextArea(mdfxTxt);
        markdownView.mdStringProperty().bind(textArea.textProperty());
        markdownView.getStylesheets().add(App.class.getResource("sample.css").toExternalForm());
        ScrollPane content = new ScrollPane();
        content.setContent(markdownView);
        content.setFitToWidth(true);
        javafx.scene.layout.HBox.setHgrow(content, javafx.scene.layout.Priority.ALWAYS);
        textArea.setMinWidth(350);
        HBox root = new HBox(content);
        Scene scene2 = new Scene(root, 700,700);
        stage.setScene(scene2);*/
        //markdown example
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
