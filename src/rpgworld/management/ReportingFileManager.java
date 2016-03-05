package rpgworld.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportingFileManager {
	
	private File file;
	
	private final static ReportingFileManager INSTANCE = new ReportingFileManager();
	
	private ReportingFileManager() {
		createFile();
	}
	
	public static ReportingFileManager getInstance(){
		return INSTANCE;
	}
	
	private void createFile(){
		file = new File("plugins/RPGWorld/reports.txt");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void report(String... messages){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		PrintWriter out = null;
		BufferedReader in = null;		
		File f = new File("file.temp");
		try {
			in = new BufferedReader(new FileReader(file));
			try {
				out = new PrintWriter(new FileWriter(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		out.println(dateFormat.format(date) + " : " + messages[0]);
		for(int i=1;i<messages.length;i++){
			String spaces = "                   ";
			out.println(spaces + messages[i]);
		}
		
		String line;
		try {
			while((line = in.readLine()) != null){
				out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.close();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.delete();
		f.renameTo(file);
	}
	
	
}
