package Project.Controllers;

import Project.App;
import Project.Task;
import com.sandec.mdfx.MarkdownView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static Project.Task.current;


public class TaskStatementController implements Initializable {

    @FXML
    private ScrollPane taskStatementScrollPane;
    public static ScrollPane stTaskStatementScrollPane;

    static String mdfxTxt = "";


    public static void skip()  {
        Task old = current;
        current = Task.randomTask();
        while (old.equals(current)) {
            current = Task.randomTask();
        }
        System.out.println(current.getDirectory());
        mdfxTxt = current.getStatement();

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
        stTaskStatementScrollPane.setFitToHeight(false);

        CodeEditorController.text = "";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            if (mdfxTxt.equals(""))mdfxTxt = current.getStatement(); 

            MarkdownView markdownView = new MarkdownView(mdfxTxt) {
                @Override
                protected List<String> getDefaultStylesheets() {
                    return List.of(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
                }
            };
            markdownView.setMdString(mdfxTxt);
            markdownView.getStylesheets().add(Objects.requireNonNull(App.class.getResource("sample.css")).toExternalForm());
            //markdownView.setFillWidth(false);

            taskStatementScrollPane.setContent(markdownView);
            taskStatementScrollPane.setFitToWidth(true);
            taskStatementScrollPane.setFitToHeight(false);

        stTaskStatementScrollPane = taskStatementScrollPane;


    }


}
