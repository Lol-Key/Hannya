package Project;

import javafx.event.ActionEvent;

import java.util.Random;

import static Project.App.sizeOfTasks;
import static Project.App.tasksList;

public class CurrentTask {

    public String nameOfTheTask;
    public String textFromTask;
    public String pathOfTheTask;

    private String inPath;
    private String outPath;

    private final String fileSeparator = System.getProperty("file.separator");
    private final String absolutePath = System.getProperty("user.dir")+fileSeparator+"res"+fileSeparator;
    //TO DO:: tylko jeden curren task na raz -> singleton
    public CurrentTask(){
        nameOfTheTask = tasksList.listOfTasks.get(0).getFirst();
        textFromTask = tasksList.listOfTasks.get(0).getSecond();
        pathOfTheTask = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator;
        inPath = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator+"in.txt";
        outPath = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator+"out.txt";
    }
    public void generateRandom(){
        Random rand = new Random();
        int myRand = rand.nextInt(sizeOfTasks);
        nameOfTheTask = tasksList.listOfTasks.get(myRand).getFirst();
        textFromTask = tasksList.listOfTasks.get(myRand).getSecond();
        pathOfTheTask = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator;
        inPath = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator+"in.txt";
        outPath = absolutePath+"tasks"+fileSeparator+nameOfTheTask+fileSeparator+"out.txt";
        //System.out.println(inPath);
    }
}
