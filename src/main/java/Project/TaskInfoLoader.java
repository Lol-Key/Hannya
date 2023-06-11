package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskInfoLoader {

    static String loadFileByName(File dir, String name) {
        File fileDir = new File(dir, name);

        String result = "";
        try {
            Scanner reader = new Scanner(fileDir);
            StringBuilder resultBuiler = new StringBuilder();

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                resultBuiler.append(data);
                resultBuiler.append('\n');
            }

            reader.close();
            result = resultBuiler.toString();
        } catch (FileNotFoundException ignored) {
        }

        return result;

    }

    static public String LoadStatment(File taskDir) {
        return loadFileByName(taskDir, "statement.md");
    }

    static public int loadTimeLimit(File taskDir) {
        String ans = loadFileByName(taskDir, "info.txt");
        int result = 1500;

        Matcher findTimeLimit = Pattern.compile("<timelimit:[0-9]+").matcher(ans);
        if (findTimeLimit.find()) {
            String found = findTimeLimit.group();
            found = found.substring(11);
            result = Integer.parseInt(found);
        }

        return result;
    }
}
