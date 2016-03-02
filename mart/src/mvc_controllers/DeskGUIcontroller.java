package mvc_controllers;


import huaabdelmartin_stage2.hireMoreWorkersException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import mvc_model.TheBrain;
import mvc_model.Worker;
import mvc_views.DeskGUI;


public class DeskGUIcontroller {

	//instance variables
	TheBrain brain;
	DeskGUI deskGUI;

	//constructor
	public DeskGUIcontroller(TheBrain brain, DeskGUI deskGUI){
		this.brain = brain;
		this.deskGUI = deskGUI;
		createThreads();	
		checkifthreadsdead();
	}

	/**
	 * Creates workers and threads for workers, starts threads running
	 * 
	 * Gets the number which the user input when application started, and uses this to 
	 * create the same number of Worker objects, and Threads for each worker. It starts these
	 * threads running, then the Worker objects take over the main functionality of the program
	 */
	public void createThreads(){
		try{
			int howManyWorkers = brain.getNumberOfWorkers();//later will set to be entered in GUI
			if( howManyWorkers < 1 ){
				throw new hireMoreWorkersException(howManyWorkers);
			}

			for(int x = 0; x < howManyWorkers; x++){//create worker threads
				Worker w = new Worker(brain);
				brain.addWorker(w);
			}

			for(Worker w : brain.getWorkerList()){//for each worker, create a thread and start running
				Thread t = new Thread(w);
				brain.addWorkerThread( t );
				t.start();
				try {
					t.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch(hireMoreWorkersException hmwe)
		{
			brain.getThelog().logEvent(hmwe.getMessage());
			System.out.println(hmwe.getMessage());
		}
	}

	/**
	 * This method is used to keep the program running until the Threads have finished
	 * 
	 * A while loop is used to keep the application running while threads are still alive, in 
	 * this way they get a chance to finish processing their current taxi and passenger group
	 * and for the results to show in the Views
	 */
	public void checkifthreadsdead(){

		int numberThreads = brain.getWorkerThreads().size();
		boolean deadOrAlive = true;

		while(deadOrAlive)
		{
			int threadcount = 0;
			//check all threads to see if alive or dead
			for(Thread t : brain.getWorkerThreads())
			{
				if(t.isAlive()){//if alive set boolean to true
					threadcount++;
				}
			}
			if( threadcount == 0 ){
				deadOrAlive = false;
			}
		}

		brain.getThelog().logEvent("*** Work completed, take the rest of the day off... ***");
		System.out.println("*** Work completed, take the rest of the day off... ***");
		brain.getThelog().writeLogToFile();
		System.exit(1);
	}
}
