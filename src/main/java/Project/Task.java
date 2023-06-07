package Project;

import java.io.File;
import java.util.Random;

public class Task
{
	static Random gen = new Random( 2137 );
	File dir=null;

	Task(){ }

	public static Task randomTask()
	{
		String absolutePath = System.getProperty("user.dir");
		String fileSeparator = System.getProperty("file.separator");

		System.out.println( "obtaining: " + absolutePath + fileSeparator +"res"+fileSeparator + "tasks" + fileSeparator ) ;
		File projDir = new File(absolutePath + fileSeparator + "res" + fileSeparator + "tasks" + fileSeparator);
		File[] candidates = projDir.listFiles();
		
		Task res = new Task();
		int rand = gen.nextInt()%candidates.length;
		for(int i=0;i<candidates.length;i++)
		{
			if(candidates[i].isFile())continue;
			if(0==rand--)continue;
			res.dir = candidates[i];
			break;
		}

		return res;
	}
}
