package Project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.*;

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

    private static ArrayList<Label> stcLabels = new ArrayList<>();

    public static int[] labValues = new int[6];

    private static Collection<String> solvedTasks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        solvedTasks = new LinkedList<String>();
        stcLabels.add(lab1);
        stcLabels.add(lab2);
        stcLabels.add(lab3);
        stcLabels.add(lab4);
        stcLabels.add(lab5);
        stcLabels.add(lab6);

        Arrays.fill(labValues, 0);

        lab1.setText(Integer.toString(labValues[0]));
        lab2.setText(Integer.toString(labValues[1]));
        lab3.setText(Integer.toString(labValues[2]));
        lab4.setText(Integer.toString(labValues[3]));
        lab5.setText(Integer.toString(labValues[4]));
        lab6.setText(Integer.toString(labValues[5]));

        String absolutePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        int unsolvedTasks = 0;
        System.out.println("obtaining: " + absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File projDir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File[] candidates = projDir.listFiles();
        assert candidates != null;
        for (File candidate : candidates) {
            if (candidate.isFile()) continue;
            unsolvedTasks++;
        }
        setUnsolvedTasks(unsolvedTasks);
    }

    public static void incrementSolvedTasks(String dir) {
        if (solvedTasks.contains(dir))
            return;
        solvedTasks.add(dir);
        labValues[0]++;
        labValues[1]--;
        stcLabels.get(0).setText(Integer.toString(labValues[0]));
        stcLabels.get(1).setText(Integer.toString(labValues[1]));
    }

    public static void setUnsolvedTasks(int val) {
        labValues[1] = val;
        stcLabels.get(1).setText(Integer.toString(val));
    }

    public static void incrementSuccessfulSubmissions() {
        labValues[2]++;
        stcLabels.get(2).setText(Integer.toString(labValues[2]));
    }

    public static void incrementUnsuccessfulSubmissions() {
        labValues[3]++;
        stcLabels.get(3).setText(Integer.toString(labValues[3]));
    }

    public static void incrementCharactersTyped(int val) {
        labValues[4] += val;
        stcLabels.get(4).setText(Integer.toString(labValues[4]));
    }

    public static void incrementLinesOfCode() {
        labValues[5]++;
        stcLabels.get(5).setText(Integer.toString(labValues[5]));
    }
}
