package Project;

import Project.GppFactory.GppCompilationException;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TaskTester {

    public static class WrongAnwserException extends Exception
    {
	public Diff diff;
	public File test;
	WrongAnwserException(Diff diff_,File test_)
	{
	    diff=diff_;
	    test=test_;
	}
    }

    static void runOne(Task task, File test) throws WrongAnwserException{
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

	Diff result = Diff.Factory(taskDir, "user_solution.out", "model_solution.out");
        System.out.println(result.isDifferent());

        if(result.isDifferent())throw new WrongAnwserException(result,test);
    }

    static void saveCode(Task task, String code) {
        File dest = new File(task.getDirectory(), "user_solution.cpp");
        try {
            Files.write(dest.toPath(), code.getBytes());
        } catch (IOException ignored) {
        }
    }

    public static void runAll(Task task, String code) throws GppCompilationException, WrongAnwserException{
            saveCode(task, code);
            GppFactory.compile(task.getDirectory(), "user_solution");


        if(!(new File(task.getDirectory(),"model_solution")).exists())GppFactory.compile(task.getDirectory(), "model_solution");

        File testDir = new File(task.getDirectory(), "tests");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".in");
            }
        };
        File[] tests = testDir.listFiles(filter);

        for (int i = 0; i < Objects.requireNonNull(tests).length; i++)runOne(task, tests[i]);

    }

    public static void userTest(Task task, String code,File test) throws GppCompilationException, WrongAnwserException
    {
	if(test == null)return;

        if(!(new File(task.getDirectory(),"model_solution")).exists())GppFactory.compile(task.getDirectory(), "model_solution");


	    saveCode(task, code);
	    GppFactory.compile(task.getDirectory(), "user_solution.cpp" );

	
	runOne(task, test);
    }	
}
//
//#include <iostream>
//using namespace std;
//int main(){ cout << "Hello world";}
