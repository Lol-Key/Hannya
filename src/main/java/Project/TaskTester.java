package Project;

import Project.GppFactory.GppCompilationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class TaskTester 
{
	static boolean runOne(Task task,File test)
	{
		ProcessBuilder tester = new ProcessBuilder("./user_solution");
		tester.directory(task.dir);
		tester.redirectOutput( new File(task.dir,"user_solution.out") );
		tester.redirectInput(test);

		try{
		Process testerProcess = tester.start();
		testerProcess.waitFor(1500, TimeUnit.MILLISECONDS);
		}
		catch( IOException e ) {}
		catch( InterruptedException e ) {}
	
		tester = new ProcessBuilder("./model_solution");
		tester.directory(task.dir);
		tester.redirectOutput( new File(task.dir,"model_solution.out") );
		tester.redirectInput(test);

		try {
			Process testerProcess = tester.start();
			testerProcess.waitFor();	
		}
		catch( IOException e ) {}
		catch( InterruptedException e ) {}

		ProcessBuilder diff = new ProcessBuilder("diff", "model_solution.out", "user_solution.out");
		diff.directory(task.dir);

		try {
			System.out.println("Diffin");
			Process diffProcess = diff.start();
			diffProcess.waitFor();
			BufferedReader diffOut = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));
			while(true)
			{
				String line=diffOut.readLine();
				if(line==null)break;
				//System.out.println( line );
				return false;
			}
		}
		catch( IOException e ) {}
		catch( InterruptedException e ) {}
		
		return true;
	}

	static void saveCode(Task task,String code)
	{
		File dest = new File(task.dir, "user_solution.cpp");
		try{
			Files.write(dest.toPath(), code.getBytes());
		}catch ( IOException e ){}
	}

	public static boolean runAll(Task task,String code) throws GppCompilationException
	{	
		saveCode(task, code);
		GppFactory.compile(task.dir, "user_solution");
		GppFactory.compile(task.dir, "model_solution");
		
		FilenameFilter filter = new FilenameFilter(){ public boolean accept(File dir, String name) { return name.endsWith(".in"); } };
		File[] tests = task.dir.listFiles( filter );
	
		boolean res = true;

		for(int i=0;i<tests.length;i++)res = res && runOne(task,tests[i]);

		return res;
	}

}
