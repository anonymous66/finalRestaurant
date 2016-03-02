package mvc_views;

import huaabdelmartin_stage2.*;
import mvc_model.Worker;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import mvc_model.TheBrain;

	public class QueueGUI extends JFrame implements Observer{
		
		//instance variables
		TheBrain brain;
		JPanel pgPanel;
		JPanel taxiPanel;
		JTextArea jTextAreaPassengerGroups;
		JTextArea jTextAreaTaxis;
		JScrollPane PGscrollPane;
		JScrollPane TAXIscrollPane;
		JButton stopButton;
		public static final String NL = System.getProperty("line.separator");
		
		
		public QueueGUI(TheBrain brain){
			this.brain = brain;
		    brain.addObserver(this);//registers this View with the Model
		    this.setSize(650,700);
		    this.setLocation(640,10);
		    this.setTitle("Queues");
	        this.setVisible(true);
	        this.setLayout(new BorderLayout());
	        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
	        createAreas();
		}

		/**
		 * Sets out the 3 parts of this frame.... list of PGs, list of Cars, Stop button
		 */
		private void createAreas() {
				
			    pgPanel = new JPanel();
			    pgPanel.setBorder(new EmptyBorder(2,2,2,2));
			    pgPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				jTextAreaPassengerGroups = new JTextArea();
				jTextAreaPassengerGroups.setText("Passenger Groups" );

				pgPanel.add(jTextAreaPassengerGroups);
				this.add(pgPanel, BorderLayout.CENTER);
				jTextAreaPassengerGroups.revalidate();

				taxiPanel = new JPanel();
				taxiPanel.setBorder(new EmptyBorder(2,2,2,2));
				taxiPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				jTextAreaTaxis = new JTextArea();
				jTextAreaTaxis.setText("TAXI" );

				taxiPanel.add(jTextAreaTaxis); //add JTextArea to the frame so you can see it!!!
				this.add(taxiPanel, BorderLayout.EAST);
				
				stopButton = new JButton("Stop Application");
				stopButton.setForeground(Color.red);
				this.add(stopButton, BorderLayout.SOUTH);
				
				jTextAreaTaxis.revalidate();
				stopButton.revalidate();
		}
		
		/**
		 * This is used by the controller to add an actionlistener to a button
		 * @param al
		 */
		public void addStopButtonListener(ActionListener al) {
			stopButton.addActionListener(al);
	    }
		
		/**
		 * When the stop button is pressed, the application ends gracefull. 
		 * 
		 * This method is called by the actionlistener for the Stop button. It in turn calls a method
		 * in the master model which gracefully ends the application. 
		 * @param e
		 */
		public void stop(ActionEvent e){
		
				if(e.getSource() == stopButton){
					brain.getThelog().logEvent("Application Stopping...");
					System.out.println("Application Stopping...");
					brain.stopApplication();
				}
				else{
					
				}
		}
		
		@Override
		public void update(Observable o, Object arg1) {//this is called when model objects call their notifyObservers() method. 
			
			/*
			 * This method creates  strings of details of all remaining passenger groups and taxis, and sets the text of JTextAreas so the user can see them
			 */
			String PGString = "";
			String carString = "";
			
			for(PassengerGroup pg : brain.getPassengerGroupCollection()){
				PGString += "Destination: " + pg.getDesination() +", PassengergroupID: " + pg.getPassengergroupID()
						+ ", Number Passengers: " + pg.getNumberPassengers() + NL + NL;
			}
			
			for(Car car : brain.getTaxisCollection() ){
				carString += car.toString() + NL;
			}
			
			jTextAreaPassengerGroups.setText(
					"PASSENGER GROUPS:"+NL+NL
					+ PGString
					);
			jTextAreaTaxis.setText(
					"TAXIS:"+NL+NL
					+ carString
					);
			this.showData();//just prints to console, maybe can use for the Log
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



