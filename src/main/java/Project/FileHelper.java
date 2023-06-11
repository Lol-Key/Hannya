package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHelper
{
	public static String fileToString(File fileDir)
	{
		String result = "";
		try {
			Scanner reader = new Scanner(fileDir);
			StringBuilder resultBuiler = new StringBuilder();

			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				resultBuiler.append(data);
				resultBuiler.append('\n');
			}
			reader.close();
			result = resultBuiler.toString();
		} catch (FileNotFoundException ignored) { }

		return result;
	}
}
