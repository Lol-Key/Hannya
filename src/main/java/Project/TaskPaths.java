package Project;

import java.lang.String;

public class TaskPaths
{
	
	public static String UnixToWin(String path)
	{
		return "";		
	}

	public static String WinToUnix(String path)
	{
		return "";
	}

	public static String Solve(String path)
	{
		return path.concat("solve");
	}

	public static String Compile(String path,String name)
	{
		return "g++ ".concat(path).concat(name).concat(" -o").concat(path).concat(name);
	}
}
