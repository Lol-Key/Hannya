package Project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatController implements Initializable {
    @FXML
    public Label lab1;
    @FXML
    public Label lab2;
    @FXML
    public Label lab3;
    @FXML
    public Label lab4;
    @FXML
    public Label lab5;
    @FXML
    public Label lab6;

    private static Label stclab1;
    private static Label stclab2;
    private static Label stclab3;
    private static Label stclab4;
    private static Label stclab5;
    private static Label stclab6;

    private static int lab1Value;
    private static int lab2Value;
    private static int lab3Value;
    private static int lab4Value;
    private static int lab5Value;
    private static int lab6Value;

    private static Collection<String> solvedTasks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        solvedTasks = new LinkedList<String>();
        stclab1 = lab1;
        stclab2 = lab2;
        stclab3 = lab3;
        stclab4 = lab4;
        stclab5 = lab5;
        stclab6 = lab6;
        lab1Value = 0;
        lab2Value = 0;
        lab3Value = 0;
        lab4Value = 0;
        lab5Value = 0;
        lab6Value = 0;
        lab1.setText(Integer.toString(lab1Value));
        lab2.setText(Integer.toString(lab2Value));
        lab3.setText(Integer.toString(lab3Value));
        lab4.setText(Integer.toString(lab4Value));
        lab5.setText(Integer.toString(lab5Value));
        lab6.setText(Integer.toString(lab6Value));
        String absolutePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        int i = 0;
        System.out.println("obtaining: " + absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File projDir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File[] candidates = projDir.listFiles();
        assert candidates != null;
        for (File candidate : candidates) {
            if (candidate.isFile()) continue;
            i++;
        }
        StatController.setUnsolvedTasks(i);
    }

    public static void incrementSolvedTasks(String dir) {
        if (solvedTasks.contains(dir))
            return;
        solvedTasks.add(dir);
        ++ lab1Value;
        -- lab2Value;
        stclab1.setText(Integer.toString(lab1Value));
        stclab2.setText(Integer.toString(lab2Value));
    }

    public static void setUnsolvedTasks(int val) {
        lab2Value = val; stclab2.setText(Integer.toString(val));
    }

    public static void incrementSuccessfulSubmissions() {
        ++ lab3Value;
        stclab3.setText(Integer.toString(lab3Value));
    }

    public static void incrementUnsuccessfulSubmissions() {
        ++ lab4Value;
        stclab4.setText(Integer.toString(lab4Value));
    }

    public static void incrementCharactersTyped(int val) {
        lab5Value += val;
        stclab5.setText(Integer.toString(lab5Value));
    }

    public static void incrementLinesOfCode() {
        ++lab6Value;
        stclab6.setText(Integer.toString(lab6Value));
    }
}
