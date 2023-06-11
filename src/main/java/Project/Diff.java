package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Diff
{
	boolean different;

	String first,second;

	public boolean isDifferent(){ return different; }

	public String getFirst(){ return first; }

	public String getSecond(){ return second; }

	public static Diff Factory(File dir,String name1,String name2)
	{
		Diff result = new Diff();
		result.different = false;

		ProcessBuilder diff = new ProcessBuilder("diff", name1, name2, "-w");
		diff.directory(dir);
		try {

			Process diffProcess = diff.start();
			diffProcess.waitFor();
			BufferedReader diffOut = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));
			while(true) 
			{
				String line = diffOut.readLine();
				if(line==null) break;
				result.different = true;
				break;
			}
		} catch (IOException | InterruptedException ignored) {}

		result.first = FileHelper.fileToString(new File(dir,name1));	
		result.second = FileHelper.fileToString(new File(dir,name2));

		return result;
	}
}
