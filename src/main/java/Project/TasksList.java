package Project;

import Project.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;



public class TasksList {
     public ArrayList<Pair<String, String>> listOfTasks;
    private final String fileSeparator = System.getProperty("file.separator");
    private final String absolutePath = System.getProperty("user.dir")+fileSeparator+"res"+fileSeparator;

    public TasksList(){
        listOfTasks = new ArrayList<>();
    }
    public void takeNamesOfFiles(){
        File[] folders = new File(absolutePath+"tasks").listFiles();
        if(folders == null){
            return;
        }
        for (File folder : folders) {

            if (folder.isDirectory()) {
                String path = folder.getAbsolutePath() + fileSeparator + "text.txt";
                String text;
                try {
                    text = readF(path);
                } catch (IOException e) {
                    continue;
                }
                Pair<String, String> pair = new Pair<>(folder.getName(), text);
                listOfTasks.add(pair);
            }
        }
    }

    public String readF(String nameOfFile) throws IOException {
        return Files.readString(Paths.get(nameOfFile));
    }
}
