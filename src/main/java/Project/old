package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task
{
	String path;
	public Task(String path)
	{ 
		if(path.charAt(path.length()-1)!='/')path=path.concat("/");
		this.path=path;

		runCommie("g++ ".concat(path).concat("wzor.cpp -o").concat(path).concat("wzor"), false);
		runCommie("chmod ".concat("a+x ").concat(path).concat("wzor"), false);
	}	

	//TODO compile exception, testcase exception

	public boolean runCommie(String commie, boolean log)
	{
		if(log)System.out.println("running : ".concat(commie));
		try 
		{ 
			Process process = Runtime.getRuntime().exec(commie);
			process.waitFor();

			if(!log)return true;
			BufferedReader logStream=new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while(true)
			{
				String line=logStream.readLine();
				if(line==null)break;
				System.out.println(line);
			}
		}
		catch ( Exception ioe){ System.out.println( ioe.getMessage() ); }

		return true;
	}

	boolean Case(String name)
	{
		System.out.println("Testing: ".concat(name));
		try 
		{ 
			Process process = Runtime.getRuntime().exec( path.concat("solve") );
			
			process.getOutputStream().write(Files.readString(Paths.get(path.concat(name) )).getBytes());
			process.getOutputStream().close();
			process.waitFor();
			
			Files.write(Paths.get(path.concat("solve.out")),process.getInputStream().readNBytes(100000));
			process.getInputStream().close();

		}
		catch ( Exception ioe ){ System.out.println( ioe.getMessage() ); }


		//runCommie("chmod ".concat("a+x ").concat(path).concat("wzor"), false);
		try 
		{ 
			Process process = Runtime.getRuntime().exec( path.concat("wzor") );

			process.getOutputStream().write(Files.readString(Paths.get(path.concat(name) )).getBytes());
			process.getOutputStream().close();
			process.waitFor();
			
			Files.write(Paths.get(path.concat("wzor.out")),process.getInputStream().readNBytes(100000));
			process.getInputStream().close();
		}
		catch ( Exception ioe ){ System.out.println( ioe.getMessage() ); }

		boolean res=true;
		try 
		{ 
			Process process = Runtime.getRuntime().exec("diff -b ".concat(path).concat("wzor.out ").concat(path).concat("solve.out"));
			process.waitFor();
			
			BufferedReader logStream=new BufferedReader(new InputStreamReader(process.getInputStream()));
			while(true)
			{
				String line=logStream.readLine();
				if(line==null)break;
				else res=false;
				System.out.println(line);
			}

		}catch ( Exception ioe){ System.out.println( ioe.getMessage() ); }
	
		return res;
	}
	
	public boolean Test(String code)
	{

		try { Files.write(Paths.get(path, "solve.cpp"), code.getBytes()); }
		catch ( IOException ioe ) { System.out.println(ioe.getMessage()); }
		
		runCommie("g++ ".concat(path).concat("solve.cpp -o").concat(path).concat("solve"), true);
		runCommie("chmod ".concat("a+x ").concat(path).concat("solve"), false);


		File dir = new File(path);
		File[] tests = dir.listFiles(new FilenameFilter() { public boolean accept(File dir, String name) { return name.endsWith(".in"); } });

		for(int i=0;i<tests.length;i++)
		{
			if(!Case(tests[i].getName()))return false;
		}

		return true;
	}

//	public static void main(String[] args)
//	{
//
//		Task currentTask=new Task("./task0"); ///should be ${resourcepath}/${taskpath}
//		String code="#include<bits/stdc++.h>\n\nusing namespace std;\n\nint32_t main(){ cout<<\">.<Hello World\"; }\n";
//
//		if(currentTask.Test(code))System.out.println("OK");
//		else System.out.println("ERR");
//
//
//		currentTask=new Task("./task1"); ///should be ${resourcepath}/${taskpath}
//		code="#include<bits/stdc++.h>\n\nusing namespace std;\n\nint32_t main(){ string s;\n cin>>s;\n cout<<\"wow\";\n}\n";
//
//		if(currentTask.Test(code))System.out.println("OK");
//		else System.out.println("ERR");
//
//	}

}
