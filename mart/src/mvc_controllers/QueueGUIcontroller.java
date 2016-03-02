package mvc_controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc_model.TheBrain;
import mvc_views.QueueGUI;

public class QueueGUIcontroller {
	
		//instance variables
		TheBrain brain;
		QueueGUI queueGUI;

		//constructor
		public QueueGUIcontroller(TheBrain brain, QueueGUI queueGUI){
			this.brain = brain;
			this.queueGUI = queueGUI;
			//this adds an action lister to the View's 'stop button'
			queueGUI.addStopButtonListener(new Stop());

		}
		
		//this class is for actioning events triggered in the GUI, e.g. pressing buttons
		class Stop  implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{ 
				queueGUI.stop(e);
			}
		}

}
