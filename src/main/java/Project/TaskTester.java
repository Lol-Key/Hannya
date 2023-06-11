package Project;

import Project.GppFactory.GppCompilationException;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TaskTester {
    static boolean runOne(Task task, File test) {
        File taskDir = task.getDirectory();

        ProcessBuilder tester = new ProcessBuilder("./user_solution");
        tester.directory(task.getDirectory());
        tester.redirectOutput(new File(taskDir, "user_solution.out"));
        tester.redirectInput(test);

        try {
            Process testerProcess = tester.start();
            System.out.println(task.timeLimit);
            testerProcess.waitFor(task.timeLimit, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException ignored) {
        }

        tester = new ProcessBuilder("./model_solution");
        tester.directory(task.getDirectory());
        tester.redirectOutput(new File(taskDir, "model_solution.out"));
        tester.redirectInput(test);

        try {
            Process testerProcess = tester.start();
            testerProcess.waitFor();
        } catch (IOException | InterruptedException ignored) {
        }

        ProcessBuilder diff = new ProcessBuilder("diff", "model_solution.out", "user_solution.out", "-w");
        diff.directory(taskDir);

        try {
            System.out.println("Diffin");
            Process diffProcess = diff.start();
            diffProcess.waitFor();
            BufferedReader diffOut = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));
            while (true) {
                String line = diffOut.readLine();
                if (line == null) break;
                return false;
            }
        } catch (IOException | InterruptedException ignored) {
        }

        return true;
    }

    static void saveCode(Task task, String code) {
        File dest = new File(task.getDirectory(), "user_solution.cpp");
        try {
            Files.write(dest.toPath(), code.getBytes());
        } catch (IOException ignored) {
        }
    }

    public static boolean runAll(Task task, String code) throws GppCompilationException {
        saveCode(task, code);
        GppFactory.compile(task.getDirectory(), "user_solution");
        GppFactory.compile(task.getDirectory(), "model_solution");

        File testDir = new File(task.getDirectory(), "tests");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".in");
            }
        };
        File[] tests = testDir.listFiles(filter);

        for (int i = 0; i < Objects.requireNonNull(tests).length; i++) {
            if (!runOne(task, tests[i])) return false;
        }

        return true;
    }

}
