package Project;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskInfoLoader {

    static public String LoadStatment(File taskDir) {
        return FileHelper.fileToString(new File(taskDir, "statement.md"));
    }

    static public int loadTimeLimit(File taskDir) {
        String ans = FileHelper.fileToString(new File(taskDir, "info.txt"));
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
