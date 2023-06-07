package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GppFactory
{

	public static class GppCompilationException extends Exception
	{
		List<Integer>lines;
		GppCompilationException(String GppLog, String fileName)
		{
			this.lines=new ArrayList<Integer>();
			Matcher Findlines = Pattern.compile(fileName.concat(":[0-9]+:") ).matcher(GppLog);
			while(Findlines.find())
			{
				String badline = Findlines.group();
				badline = badline.substring(fileName.length()+1, badline.length()-1 );
				this.lines.add( Integer.valueOf(badline) );
			}
		}
	}

	static void compile(File path, String name) throws GppCompilationException
	{
		if( name.endsWith(".cpp") )name = name.substring(0, name.length()-4);
		
		ProcessBuilder gpp = new ProcessBuilder("g++", "-o", name, name.concat(".cpp"), "-w");
		if(path!=null)gpp.directory(path);

		System.out.println("running: ".concat(String.join(" ",gpp.command().toArray(new String[0]))));

		String errlog="";
		try{
			Process gppProcess = gpp.start();
			gppProcess.waitFor();

			BufferedReader logStream=new BufferedReader(new InputStreamReader(gppProcess.getErrorStream()));

			while(true)
			{
				String line=logStream.readLine();
				if(line==null)break;
				errlog = errlog.concat(line);
			}
		}
		catch ( Exception ioe){ System.out.println( ioe.getMessage() ); }
		if(errlog != "")throw new GppCompilationException(errlog, name.concat(".cpp"));
	}
	
	public static void main( String [] args )
	{
		try{
			compile(null, "oktest.cpp");
		
		}catch ( GppCompilationException gce )
		{
			System.out.println("Exception thrown");
		}

		try{
			compile(null, "badtest.cpp");
		
		}catch ( GppCompilationException gce )
		{
			System.out.println("Exception thrown");
			for(int i=0;i<gce.lines.size();i++)System.out.println( gce.lines.get(i) );
		}
	}
}

