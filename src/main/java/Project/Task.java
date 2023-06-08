package Project;

import java.io.File;
import java.util.Random;

public class Task
{
	static Random gen = new Random( 2137 );
	public static Task current = new Task();
	public File dir=null;

	Task(){
		String absolutePath = System.getProperty("user.dir");
		String fileSeparator = System.getProperty("file.separator");
		dir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator+ "Zadanie 1" + fileSeparator);
	}

	public static Task randomTask()
	{
		String absolutePath = System.getProperty("user.dir");
		String fileSeparator = System.getProperty("file.separator");

		System.out.println( "obtaining: " + absolutePath + fileSeparator +"res"+fileSeparator + "tasks" + fileSeparator ) ;
		File projDir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
		File[] candidates = projDir.listFiles();
		
		Task res = new Task();
		assert candidates != null;
		int rand = gen.nextInt()%candidates.length;
		for (File candidate : candidates) {
			if (candidate.isFile()) continue;
			if (0 == rand--) continue;
			res.dir = candidate;
			break;
		}

		return res;
	}
}
