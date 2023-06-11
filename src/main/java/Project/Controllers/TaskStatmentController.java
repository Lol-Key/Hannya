package Project.Controllers;

import Project.App;
import Project.GppFactory;
import Project.Task;
import Project.TaskTester;
import com.jfoenix.controls.JFXToggleButton;
import com.sandec.mdfx.MarkdownView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static Project.Level.currentLevel;
import static Project.Task.current;
import static javafx.scene.input.KeyCode.ESCAPE;


public class TaskStatmentController implements Initializable {

    @FXML
    private VBox anchorRoot;
    @FXML
    private ScrollPane taskStatementScrollPane;
    public static ScrollPane stTaskStatementScrollPane;
    @FXML
    private Label labelTaskName;
    public static String error = "";

    static String mdfxTxt = "";


    public static void skip()  {
        Task old = current;
        current = Task.randomTask();
        while (old.equals(current)) {
            current = Task.randomTask();
        }
        System.out.println(current.getDirectory());
        mdfxTxt = current.getStatement(); //IOUtils.toString(App.class.getResourceAsStream("sample.md"), "UTF-8");

        MarkdownView markdownView = new MarkdownView(mdfxTxt) {
            @Override
            protected List<String> getDefaultStylesheets() {
                return List.of(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
            }
        };
        markdownView.setMdString(mdfxTxt);
        markdownView.getStylesheets().add(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
        stTaskStatementScrollPane.setContent(markdownView);
        stTaskStatementScrollPane.setFitToWidth(true);
        stTaskStatementScrollPane.setFitToHeight(true);

        CodeEditorController.text = "";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("test");
        try {
            if (mdfxTxt.equals(""))
                mdfxTxt = IOUtils.toString(Objects.requireNonNull(App.class.getResourceAsStream("sample.md")), StandardCharsets.UTF_8);

            MarkdownView markdownView = new MarkdownView(mdfxTxt) {
                @Override
                protected List<String> getDefaultStylesheets() {
                    return List.of(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
                }
            };
            markdownView.setMdString(mdfxTxt);
            markdownView.getStylesheets().add(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
            markdownView.setFillWidth(false);

            taskStatementScrollPane.setContent(markdownView);
            taskStatementScrollPane.setFitToWidth(true);
            taskStatementScrollPane.setFitToHeight(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stTaskStatementScrollPane = taskStatementScrollPane;


    }


}
