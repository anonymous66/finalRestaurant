package mvc_views;


import huaabdelmartin_stage2.Car;
import huaabdelmartin_stage2.PassengerGroup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import mvc_model.TheBrain;
import mvc_model.Worker;

public class DeskGUI extends JFrame implements Observer{
	
	//instance variables
	TheBrain brain;
	Integer numberOfWorkers = 2;
	ArrayList<JTextArea> jTextAreaList = new ArrayList<JTextArea>();
	JTextArea jTextArea;
	JPanel middle;
	JPanel bottom;
	JPanel jPanel;
	JButton slowButton;
	JButton mediumButton;
	JButton fastButton;
	
	private ArrayList<JTextArea> desks = new ArrayList<JTextArea>();
	
	//constructor
	public DeskGUI(TheBrain brain){
		this.brain = brain;
		this.numberOfWorkers = brain.getNumberOfWorkers();
	    brain.addObserver(this);//important for this object to get notified of updates
	    this.setSize(640,600);
	    this.setLocation(0,110);
	    this.setTitle("Taxis Booking system");
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        addAreasToFrame();
        createWorkerTextAreas();
	}

	/**
	 * Sets how the frame will be set out 
	 */
	private void addAreasToFrame() {
		// TODO Auto-generated method stub
		middle = new JPanel(new GridLayout(numberOfWorkers,1));
		this.add(middle,BorderLayout.CENTER);
		
		bottom = new JPanel(new FlowLayout());
		bottom.setSize(100,50);
		bottom.setBorder(new EmptyBorder(2,2,2,2));
		bottom.setBorder(BorderFactory.createLineBorder(Color.black));
		//thankyou https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
		this.add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * This creates Worker panels based on how many Worker objects there are
	 */
	private void createWorkerTextAreas() {
			
		for(int x = 0; x< numberOfWorkers; x++){
			jTextArea = new JTextArea();
			jTextArea.setText("worker" );
			jTextAreaList.add(jTextArea); //add JTextArea to arraylist
			jPanel = new JPanel();
			jPanel.setSize(100,100);
			jPanel.add(jTextArea); //add JTextArea to the frame so you can see it!!!
			jPanel.setBorder(new EmptyBorder(2,2,2,2));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			//thankyou https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
			middle.add(jPanel);
			jTextArea.revalidate();//make sure you can see it
		}
	}
	
	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub

		for (JTextArea j : jTextAreaList)//loop through each JTextArea
		{
			int index = jTextAreaList.indexOf(j);//get index of JTextArea
			Worker w = brain.getWorkerList().get(index);//access worker of same index
			try{
				
				String taxiString = "";
				Car currentTaxi = w.getCurrentTaxi();
				if(currentTaxi == null){//this is a cosmetic change to show the taxi was 'processed'
					taxiString = "";
				}
				else{
					taxiString = currentTaxi.toString();
				}
				
				String PGstring = "";
				PassengerGroup currentPG = w.getCurrentPG();
				if(currentPG == null){
					PGstring = "";//this is a cosmetic change to show the taxi was 'processed'
				}
				else{
					PGstring = currentPG.toString();
				}
			j.setText(taxiString + " " + PGstring);//change text of JTextArea
			j.revalidate();//ensure you can see the JTextArea
			}
			catch(NullPointerException npe)
			{System.out.println(npe.getMessage());
			}
			
		}
		this.showData();//just prints to console
	}

	public void showData(){
		for(Worker w: brain.getWorkerList()){
			if(w.hasData()){
				System.out.println("worker"+w.hashCode()+" has:"+w.getCurrentTaxi().toString()+" and " + w.getCurrentPG().toString());
			}
			else{}
		}

	}
}
