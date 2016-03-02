package mvc_views;

import mvc_model.Worker;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import mvc_model.TheBrain;


public  class SpeedGUI extends JFrame implements Observer{

	//instance variables

	TheBrain brain; 
	JPanel speedControlPanel;
	JTextArea jTextAreaSpeedControl;
	JButton fasterSpeedButton;
	JButton normalSpeedButton;
	JButton slowerSpeedButton;
	JButton slowDownButton;
	JButton speedUpButton;

	public SpeedGUI(TheBrain brain){
		this.brain = brain;	
		brain.addObserver(this);//registers this frame with the model of the MVC pattern
		this.setSize(400,110);
		this.setLocation(20,5);
		this.setTitle("SpeedControl");
		this.setVisible(true);
		speedControlPanel = new JPanel(new FlowLayout());
		speedControlPanel.setSize(100,60);
		speedControlPanel.setBorder(new EmptyBorder(5,5,5,5));
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		populateFrame();
	}

	/**
	 * Sets up the frame setup
	 */
	private void populateFrame() {
		// TODO Auto-generated method stub
		speedControlPanel = new JPanel(new FlowLayout());
		speedControlPanel.setBorder(new EmptyBorder(5,5,5,5));
		speedControlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		jTextAreaSpeedControl = new JTextArea("Speed Control");
		//jTextAreaSpeedControl.setEditable(false);
		fasterSpeedButton = new JButton("Fast Speed");
		normalSpeedButton = new JButton("Normal Speed");
		slowerSpeedButton = new JButton("Slow Speed");
		slowDownButton  = new JButton("Slower");
		speedUpButton = new JButton("Faster");
		speedControlPanel.add(fasterSpeedButton );
		speedControlPanel.add(normalSpeedButton);
		speedControlPanel.add(slowerSpeedButton);
		speedControlPanel.add(slowDownButton);
		speedControlPanel.add(jTextAreaSpeedControl);
		speedControlPanel.add(speedUpButton);
		this.add(speedControlPanel);
		fasterSpeedButton .revalidate();
		normalSpeedButton.revalidate();
		slowerSpeedButton.revalidate();
		slowDownButton.revalidate();
		jTextAreaSpeedControl.revalidate();
		speedUpButton.revalidate();
		//return northPanel;	
	}

	//methods to add actionlisteners to buttons
	public void addFastSpeedButtonListener(ActionListener al) {
		fasterSpeedButton.addActionListener(al);
	}

	public void addNormalSpeedButtonListener(ActionListener al) {
		normalSpeedButton.addActionListener(al);
	}

	public void addSlowSpeedButtonListener(ActionListener al) {
		slowerSpeedButton.addActionListener(al);
	}

	public void addSlowDownButtonListener(ActionListener al) {
		slowDownButton.addActionListener(al);
	}

	public void addSpeedUpButtonListener(ActionListener al) {
		speedUpButton.addActionListener(al);
	}

	/**
	 * when a button is pressed, the actionlistener calls this method
	 * 
	 * it checks which button was pressed and sets variables and runs the appropriate methods from the model
	 * @param e is the event which was triggered, e.g. a button being pressed
	 */
	public void setSpeed(ActionEvent e){

			if(e.getSource() == fasterSpeedButton){
				brain.setWorkerSleeps(400, 1000);
				brain.setCurrentSpeed("Current speed: fast");
				brain.getThelog().logEvent("*** speed changed to fast***");
				System.out.println("*** speed changed to fast***");
			}

			else if (e.getSource() == normalSpeedButton){
				brain.setWorkerSleeps(1000, 2500);
				brain.setCurrentSpeed("Current speed: normal");
				brain.getThelog().logEvent("*** speed changed to normal***");
				System.out.println("*** speed changed to normal***");
			}

			else if (e.getSource() == slowerSpeedButton){
				brain.setWorkerSleeps(2500, 6125);
				brain.setCurrentSpeed("Current speed: slow");
				brain.getThelog().logEvent("*** speed changed to slow***");
				System.out.println("*** speed changed to slow***");

			}

			else if (e.getSource() == slowDownButton){
				brain.slower();
				brain.setCurrentSpeed("Current Speed: manually slower");
				brain.getThelog().logEvent("*** speed manually slowed***");
				System.out.println("*** speed manually slowed***");
			}

			else if (e.getSource() == speedUpButton){
				brain.faster();
				brain.setCurrentSpeed("Current Speed: manually faster");
				brain.getThelog().logEvent("*** speed manually quickened***");
				System.out.println("*** speed manually quickened***");
			}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// this method runs when the model calls notifyObservers(), it is used to get the latest data from the model and update the View with it
		jTextAreaSpeedControl.setText(brain.getCurrentSpeed());
	}
}
