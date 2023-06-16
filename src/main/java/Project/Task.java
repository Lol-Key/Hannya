package Project;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Task {
    public static Task current = new Task();

    private final File dir;
    String statement = "";
    int timeLimit = 1500;

    Task() {
        String absolutePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        dir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator + "group-projects" + fileSeparator);
	statement = TaskInfoLoader.LoadStatment(dir);
	timeLimit = TaskInfoLoader.loadTimeLimit(dir);
    }

    Task(File DIR) {
        this.dir = DIR;
        statement = TaskInfoLoader.LoadStatment(dir);
        timeLimit = TaskInfoLoader.loadTimeLimit(dir);
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public String getStatement() {
        return statement;
    }

    public File getDirectory() {
        return dir;
    }

    public static Task randomTask() {
        String absolutePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");

        System.out.println("obtaining: " + absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File projDir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
        File[] candidates = projDir.listFiles();

	Collections.shuffle(Arrays.asList( candidates ));

        for (File candidate : candidates)
	{
		if (candidate.isFile()) continue;
		return new Task(candidate);	
	}
	return new Task();

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task c = (Task) o;
        return (c.dir.equals(dir));
    }
}
