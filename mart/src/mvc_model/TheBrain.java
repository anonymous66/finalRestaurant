package mvc_model;

import java.util.*;

import huaabdelmartin_stage2.*;

public class TheBrain extends Observable {//this class will be a 'Subject'

	//instance variables
	private LinkedList<Car> taxisCollection = new LinkedList<Car>();//holds Car objects
	private LinkedList<PassengerGroup> passengerGroupCollection= new LinkedList<PassengerGroup>();//holds PassengerGroup objects
	private ArrayList<Worker> workerList = new ArrayList<Worker>();//holds Worker objects
	private ArrayList<Thread> workerThreads = new ArrayList<Thread>();//holds Threads
	private Integer numberOfWorkers = 2;//the number the user inputs at the start of the application
	private Log thelog;//the singleton instance of the Log class
	private String currentSpeed = "Current speed: normal";
	
	//constructor
	public TheBrain(Log thelog, Integer numberOfWorkers, LinkedList<Car> taxisCollection, LinkedList<PassengerGroup> passengerGroupCollection) {
		
		this.numberOfWorkers = numberOfWorkers;
		this.taxisCollection = taxisCollection;
		this.passengerGroupCollection = passengerGroupCollection;
		this.thelog = thelog;//the singleton instance of the Log class
	}
	
	//getters and setters
	
	public LinkedList<Car> getTaxisCollection() {
		return taxisCollection;
	}
	public String getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(String currentSpeed) {
		this.currentSpeed = currentSpeed;
		this.updateObservers();
	}

	public Log getThelog() {
		return thelog;
	}

	public void setThelog(Log thelog) {
		this.thelog = thelog;
	}

	public Integer getNumberOfWorkers() {
		return numberOfWorkers;
	}

	public void setNumberOfWorkers(Integer numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

	public synchronized void setTaxisCollection(LinkedList<Car> taxisCollection) {
		this.taxisCollection = taxisCollection;
	}
	public LinkedList<PassengerGroup> getPassengerGroupCollection() {
		return passengerGroupCollection;
	}
	public synchronized void setPassengerGroupCollection(
			LinkedList<PassengerGroup> passengerGroupCollection) {
		this.passengerGroupCollection = passengerGroupCollection;
		
	}
	
	/**
	 * This method is crucial for the MVC pattern... it causes observers of this class to
	 * run their update() method to get the latest data from this and other model classes
	 */
	public void updateObservers(){
		setChanged();//only including in this method, so it doesn't get called getNextTaxi() too....
		notifyObservers();
    	clearChanged();
	}
	
	//DeskGUIcontroller methods
	
	public void addWorker( Worker w ){
		workerList.add(w);
	}
			 
	public void addWorkerThread(Thread t){
		workerThreads.add(t);
	}
	
	public ArrayList<Worker> getWorkerList() {
		return workerList;
	}

	public void setWorkerList(ArrayList<Worker> workerList) {
		this.workerList = workerList;
	}

	public ArrayList<Thread> getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(ArrayList<Thread> workerThreads) {
		this.workerThreads = workerThreads;
	}
	
	//Worker methods
	
	/**
	 * This method checks if there are any more taxis to go
	 * @return returns true if there are more taxis, otherwise returns false
	 */
	public boolean moreTaxis(){
		if( taxisCollection.isEmpty() ){
			return false;
		}
		return true;
	}

	/**
	 * This method checks if there are any more passenger groups to go
	 * @return returns true if there are more passenger groups, otherwise returns false
	 */
	public boolean morePGs(){
		if( passengerGroupCollection.isEmpty() ){
			return false;
		}
		return true;
	}

	/**
	 * Gets the next Car in the taxis LinkedList, also removes it from the list
	 * @return Returns the Car it took from the start of the LinkedList
	 * @throws NoSuchElementException
	 */
	public synchronized Car getNextTaxi() throws NoSuchElementException {
		Car currentTaxi = taxisCollection.getFirst();
		taxisCollection.removeFirst();
		return currentTaxi;
	}
	
	/**
	 * Gets the next PassengerGroup in the PG LinkedList, also removes it from the list
	 * @return Returns the PassengerGroup it took from the start of the LinkedList
	 * @throws NoSuchElementException
	 */
	public synchronized PassengerGroup getNextPG() throws NoSuchElementException{
		PassengerGroup currentPG = passengerGroupCollection.getFirst();
		passengerGroupCollection.removeFirst();
		return currentPG;
	}
	
	/**
	 * Puts a Car into the taxi LinkedList (at the end of the list)
	 * 
	 * This method is used by Worker objects when they find the taxi is too small for
	 * the PassengerGroup number of passengers
	 * 
	 * @param c is the Car to put into the list
	 */
	public synchronized void putTaxiBackInList(Car c){
		taxisCollection.add(c);
	}
	
	/**
	 * Used by Worker objects as a while loop test while checking each taxi for a suitable
	 * size for the passenger group
	 * @return returns the number of Car left in the taxis linked list
	 */
	public synchronized int getSizeOfTaxiList(){
		return taxisCollection.size();
	}

	//Speed control methods
	
	/**
	 * Changes the Worker Thread sleep times in each Worker object, in order to make the application
	 * run slower or faster
	 * @param smallSleep is used to provide small delays between each part of processing taxis and passenger groups
	 * @param bigSleep is used to keep the data in the view for a short time for the benefit of the user
	 */
	public synchronized void setWorkerSleeps(Integer smallSleep, Integer bigSleep){
		for(Worker w : this.getWorkerList()){
			w.setSmallSleep(smallSleep);
    		w.setBigSleep(bigSleep);
		}
	}
	
	/**
	 * Decrements Worker Thread sleep times by 20% of their current sleep times
	 * 
	 * This is used to allow manual increasing of the application speed
	 */
	public synchronized void faster(){
		for(Worker w : this.getWorkerList()){
			int currentSmallSleep = w.getSmallSleep();
			int currentBigSleep = w.getBigSleep();
			int smallDecrementer = (int) (currentSmallSleep * 0.2);
			int bigDecrementer = (int) (currentBigSleep * 0.2);
			w.setSmallSleep(currentSmallSleep - smallDecrementer);
    		w.setBigSleep(currentBigSleep - bigDecrementer);
		}
	}
	
	/**
	 * Increments Worker Thread sleep times by 20% of their current sleep times
	 * 
	 * This is used to allow manual decreasing of the application speed
	 */
	public synchronized void slower(){
		for(Worker w : this.getWorkerList()){
			int currentSmallSleep = w.getSmallSleep();
			int currentBigSleep = w.getBigSleep();
			int smallDecrementer = (int) (currentSmallSleep * 0.2);
			int bigDecrementer = (int) (currentBigSleep * 0.2);
			w.setSmallSleep(currentSmallSleep + smallDecrementer);
    		w.setBigSleep(currentBigSleep + bigDecrementer);
		}
	}

	/**
	 * This method stops the application gently. 
	 * 
	 * It replaces the taxi and passenger group collections with empty collections, 
	 * allow the Worker Threads to finish processing their current Taxi/passenger group,
	 * and then causing them to finish because there are no more Cars/PassengerGroups to
	 * process
	 */
	public void stopApplication(){
		LinkedList<Car> taxisCollection = new LinkedList<Car>();
		LinkedList<PassengerGroup> passengerGroupCollection= new LinkedList<PassengerGroup>();
		this.setTaxisCollection(taxisCollection);//empty collection
		this.setPassengerGroupCollection(passengerGroupCollection);//empty collection
	}
}
