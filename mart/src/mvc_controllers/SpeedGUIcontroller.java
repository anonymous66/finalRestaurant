package mvc_controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc_model.TheBrain;
import mvc_views.SpeedGUI;

public class SpeedGUIcontroller {
	//instance variables
	TheBrain brain;
	private	SpeedGUI speedGUI;

	//constructor
	public SpeedGUIcontroller (TheBrain brain, SpeedGUI speedGUI){
		this.brain = brain;
		this.speedGUI = speedGUI;
		
		//basic three speed buttons are given ActionListeners
		speedGUI.addFastSpeedButtonListener(new SpeedBrainController());
		speedGUI.addNormalSpeedButtonListener(new SpeedBrainController());
		speedGUI.addSlowSpeedButtonListener(new SpeedBrainController());
		
		//manual speed buttons
		speedGUI.addSlowDownButtonListener(new SpeedBrainController());
		speedGUI.addSpeedUpButtonListener(new SpeedBrainController());
	}

	//this class is used for adding an ActionListener to Views
	class SpeedBrainController implements ActionListener {

		@Override
		public void actionPerformed (ActionEvent e) {

			speedGUI.setSpeed(e);
		} 
	}	
}
