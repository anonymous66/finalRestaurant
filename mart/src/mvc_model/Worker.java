package mvc_model;

import huaabdelmartin_stage2.*;

import java.util.NoSuchElementException;

public class Worker implements Runnable{
	//instance variables
	private TheBrain brain;
	private Car currentTaxi;
	private PassengerGroup currentPG;
	private int smallSleep = 1000;//default smallSleep - used as a time buffer between different parts of processing Cars and PassengerGroups
	private int bigSleep = 2500;//default bigSleep - used to keep data in the view long enough for the user to see
	
	//constructor
	public Worker(TheBrain brain) {
		this.brain = brain;
	}
	
	//getters and setters

	public int getSmallSleep() {
		return smallSleep;
	}

	public void setSmallSleep(int smallSleep) {
		this.smallSleep = smallSleep;
	}

	public int getBigSleep() {
		return bigSleep;
	}

	public void setBigSleep(int bigSleep) {
		this.bigSleep = bigSleep;
	}

	public TheBrain getBrain() {
		return brain;
	}

	public void setBrain(TheBrain brain) {
		this.brain = brain;
	}
	
	//other application-specific methods

	public boolean hasData(){
		if(currentTaxi != null && currentPG != null){
			return true;
		}
		return false;
	}

	/**
	 * Used to pick the next taxi from the list held in the master-model. It also calls
	 * the updateObservers() method, which causes observers to run their update method
	 */
	public void getNextTaxi(){
		this.currentTaxi = brain.getNextTaxi();
		brain.updateObservers();//let observers (views) know that a change has happened
	}

	/**
	 * Used to pick the next passenger group from the list held in the master-model. It also calls
	 * the updateObservers() method, which causes observers to run their update method
	 */
	public void getNextPassengerGroup(){
		this.currentPG = brain.getNextPG();	
		brain.updateObservers();//let observers (views) know that a change has happened
	}

	/**
	 * Used to get the taxi currently held by this Worker
	 * @return Returns the Car currently held by this Worker
	 */
	public Car getCurrentTaxi() {
		return currentTaxi;
	}

	/**
	 * Used to set the Car held by this Worker to null, a null Car is used by DeskGUI which uses it to clear the data showing in the frame
	 * @param currentTaxi
	 */
	public void setCurrentTaxi(Car currentTaxi) {
		this.currentTaxi = currentTaxi;
		brain.updateObservers();//let observers (views) know that a change has happened
	}

	/**
	 * Used to get the passenger group currently held by this Worker
	 * @return Returns the PassengerGroup currently held by this Worker
	 */
	public PassengerGroup getCurrentPG() {
		return currentPG;
	}

	/**
	 * Used to set the PassengerGroup held by this Worker to null, a null PassengerGroup is used by DeskGUI which uses it to clear the data showing in the frame
	 * @param currentPG
	 */
	public void setCurrentPG(PassengerGroup currentPG) {
		this.currentPG = currentPG;
		brain.updateObservers();//let observers (views) know that a change has happened
	}

	@Override
	public void run() {
		/*this method is run when the container Thread starts. It keeps the Worker
		 * processing taxis and passengergroups while there are more.
		 */
		while( brain.moreTaxis() && brain.morePGs()){

			//for(Worker w : brain.getWorkerList() ){//go through each worker thread and call getNextTaxi/PG methods
			//	Thread t = brain.getWorkerThreads().get( brain.getWorkerList().indexOf(w) );

			try {
				Thread.currentThread();
				Thread.sleep(smallSleep);//begin by sleeping to create a buffer between previous processing of taxi/passengergroup and this one, this is for the benefit of the user watching the Views
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(brain.moreTaxis() && brain.morePGs())  {//if there are more taxis and passenger groups
	
				getNextTaxi();//get next taxi from taxi list
				getNextPassengerGroup();//get next passenger group from PG list

				boolean taxiBigEnough = false;

				int howManyTaxisLeft = (brain.getSizeOfTaxiList()) +1;

				while( !taxiBigEnough && howManyTaxisLeft > 0)
				{
					if(this.currentTaxi.getPassengerCount() < this.currentPG.getNumberPassengers())
					{//if taxi is too small, get next taxi

						brain.getThelog().logEvent("Worker "+ this.toString() + " could not carry  " + currentPG.getNumberPassengers() +" passengers in PassengerGroup "+ currentPG.getPassengergroupID() +" for Taxi " + currentTaxi.getRegNumber() + " with seat capacity of " + currentTaxi.getPassengerCount() +".");
						System.out.println("Worker "+ this.toString() + " could not carry  " + currentPG.getNumberPassengers()  + " passengers in PassengerGroup "+ currentPG.getPassengergroupID() +" passengers for Taxi " + currentTaxi.getRegNumber() + " with seat capacity of " + currentTaxi.getPassengerCount()+".");
						brain.putTaxiBackInList(this.currentTaxi);//put taxi back into LinkedList of Cars

						getNextTaxi();//get next taxi from taxi Linked List
						
						try {
							Thread.currentThread();
							Thread.sleep(smallSleep);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					

						howManyTaxisLeft--;
					}

					else{//if taxi is big enough, break out of while loop
						taxiBigEnough = true;
					}
				}

				if(taxiBigEnough == true)//if this is true, everything worked out well and PG got processed
				{
					brain.getThelog().logEvent("Worker "+this.toString()+ " processed taxi "+currentTaxi.getRegNumber()
							+ " for "+currentPG.getNumberPassengers() + " people in PassengerGroup "+ currentPG.getPassengergroupID() +", they are going to "+currentPG.getDesination()+".");
					System.out.println("Worker "+this.toString()+ " processed taxi "+currentTaxi.getRegNumber()
							+ " for "+currentPG.getNumberPassengers() + " people in PassengerGroup "+ currentPG.getPassengergroupID() +", they are going to "+currentPG.getDesination()+".");
				}
				else{//if taxiBigEnough not true, PG were disappointed and did not find a taxi...
					brain.getThelog().logEvent("Worker "+this.toString()+ " didn't find a taxi to fit "+ currentPG.toString() + ".");
					System.out.println("Worker "+this.toString()+ " didn't find a taxi to fit "+ currentPG.toString() + ".");
				}
			}

			
			try {
				Thread.currentThread().sleep(bigSleep);//this is to keep data in the View for benefit of the user
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setCurrentTaxi(null);//this is because the View empties data if it sees the currentTaxi is null
			setCurrentPG(null);//the View empties data if it sees currentPG is null, this is important for the user to see that the taxi and PG were 'processed'
			
			try {
				Thread.currentThread().sleep(smallSleep);//a final buffer at the end of the processing of current taxi/PG, to look nice to the user
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		//when no more taxis or passenger groups, log and print, then this Thread will finish
		brain.getThelog().logEvent( this.toString() + " has finished.");
		System.out.println(this.toString() + " has finished.");
	}
}






