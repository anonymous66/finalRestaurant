package mvc_model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Log {

	//instance variables, including the very important singleton 'instance' of this class
	private static Log instance = new Log();//this is a singleton
	private String logString = "";//this holds the log String used to write the log report
	public static final String NL = System.getProperty("line.separator");

	/**
	 * Constructor is private because this is a singleton, it's a singleton because there should only be one CreateReports 
	 * object being used in this program. 
	 * 
	 */
	private Log( ) {

	}

	/**
	 * This method is essential for accessing the instance of this class
	 * @return returns the instance of this class, held as an instance variable
	 */
	public static Log getInstance(){
		return instance;
	}

	/**
	 * This method is used to append a string to the log report String
	 * @param ev is the event you want to append to the log String
	 */
	public void logEvent(String ev){
		logString += ev + NL + NL;
	}

	/**
	 * This method writes the log String of events to file
	 */
	public void writeLogToFile(){

		//writes report to file then displays message saying the path to the report file
		String reportHolder = logString;

		//writes report to file
		try
		{    
			File myfile = new File("log.txt");
			FileWriter writeReport = new FileWriter(myfile);
			writeReport.write(reportHolder);
			writeReport.close();
			StringBuffer writtenMessage = new StringBuffer();
			writtenMessage.append("Report written to ").append(myfile.getAbsolutePath());
			String wMess = writtenMessage.toString();
			JOptionPane.showMessageDialog(null, wMess);
			System.out.println(wMess);
		}
		catch(IOException ioexception)
		{
			ioexception.printStackTrace();
			System.exit(1);
		}
	}
}

