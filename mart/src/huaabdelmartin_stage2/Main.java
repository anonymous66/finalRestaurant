
package huaabdelmartin_stage2;

import javax.swing.JOptionPane;

import mvc_controllers.*;
import mvc_model.*;
import mvc_views.*;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Log thelog = Log.getInstance();//get the singleton instance of this class
		//append to the String holding the log report
		thelog.logEvent("*** Day started to process Taxis and Passenger Groups ***");

		//this GUI input is for a user to enter how many workers there should be
		String numberWorkers = JOptionPane.showInputDialog("Enter number of workers");
		Integer numWorkers;
		try{
			numWorkers = Integer.parseInt( numberWorkers );
		}
		catch(NumberFormatException nfe){//catch number format exception
			System.out.println(nfe.getMessage());
			numWorkers = 2;//set to default number of 2 workers, if not able to parse input String into an Integer
			JOptionPane.showMessageDialog(null,"Number of workers entered wasn't an integer, defaulting to 2 workers");
		}
		
		//this object reads taxis and passenger groups from text files and stores them
		ReadPassengersTaxisFromFile rtff = new ReadPassengersTaxisFromFile();
		
		//this object is the 'master model' in the MVC pattern
		TheBrain brain = new TheBrain(thelog, numWorkers, rtff.returnListOfTaxis(), rtff.returnListOfPassengerGroups());
		
		//these are the views in the MVC pattern
		DeskGUI deskGUI = new DeskGUI( brain );
		QueueGUI queueGUI = new QueueGUI( brain );
		SpeedGUI speedGUI = new SpeedGUI(brain);
		
		//these are the controllers in the MVC pattern
		SpeedGUIcontroller speedGUIcontroller = new SpeedGUIcontroller(brain, speedGUI);
		QueueGUIcontroller queueGUIcontroller = new QueueGUIcontroller(brain,queueGUI);
		DeskGUIcontroller deskGUIcontroller = new DeskGUIcontroller(brain, deskGUI);
		
	}
}
