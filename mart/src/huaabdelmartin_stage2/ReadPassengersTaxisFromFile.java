package huaabdelmartin_stage2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class ReadPassengersTaxisFromFile {

	//instance variables
	private LinkedList<Car> taxisCollection = new LinkedList<Car>();
	private LinkedList<PassengerGroup> passengerGroupCollection= new LinkedList<PassengerGroup>();
	private Integer passengerCount;
	
	public ReadPassengersTaxisFromFile(){
		
		try {
			readAndStoreTaxisCollection();
			readAndStorePassengerGroupsCollection();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (DuplicateDriverIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void readAndStoreTaxisCollection() throws IOException, DuplicateDriverIdException {

		
			//getClass().getResourceAsStream("src/stage2TextFiles/taxis.txt");
			//FileReader fr = new FileReader(getClass().getResourceAsStream("src/stage2TextFiles/taxis.txt"));

			//Scanner gives you useful methods like .next(), readLine()
			Scanner sc = new Scanner(this.getClass().getResourceAsStream("/stage2TextFiles/taxis.txt")); 
			String line = "";
			String vehicleReg = "";
			String  driverFirstName= "";
			String  driverSecondName= "";
			String passengerCount = "";
			Integer passengerNumber = 0;
			Integer IDincrementer = 0;
			

			while ( sc.hasNext() ){ //while there are more lines to be read in
				line = sc.nextLine(); //read line and put into line String
				Scanner fielder = new Scanner(line).useDelimiter(",");//in text file, tokens are separated by commas

				if(fielder.hasNext()){//safer to check that there is another token to be read
					vehicleReg = fielder.next(); //get first field
					vehicleReg = vehicleReg.trim(); //delete white spaces
				}

				if(fielder.hasNext()){
					driverFirstName = fielder.next(); 
					driverFirstName = driverFirstName.trim(); 
				}

				if(fielder.hasNext()){
					driverSecondName = fielder.next(); 
					driverSecondName = driverSecondName.trim(); 
				}
				
				if(fielder.hasNext()){
				   passengerCount = fielder.next();
				   passengerCount = passengerCount.trim();
				   passengerNumber = Integer.parseInt(passengerCount);
				}
				
				//Create Driver object using tokens from file and also unique ID, use this Driver to create Car object and add to taxisCollection
				try {
                     
					IDincrementer++;//increment to get new ID
					String driverID = Integer.toString(IDincrementer);


					for(Car c : taxisCollection){
						if( c.getDriver().getDriverID().equals(driverID) ){
							throw new DuplicateDriverIdException(driverID);
						}
					}

					Driver driver = new Driver(driverFirstName,driverSecondName, driverID);
					Car car;
					
					car = new Car(vehicleReg,driver,true,passengerNumber);
					//passengerCount = null;
					taxisCollection.add(car); 

				} 
				catch (VehicleRegFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//fr.close();//free resources

			System.out.println("Taxi Collection: "+taxisCollection);
		
	}
	public void readAndStorePassengerGroupsCollection() throws IOException{
		
			//FileReader fr = new FileReader("src/stage2TextFiles/passengerGroups.txt"); 
			//Scanner gives you useful methods like .next(), readLine()
			Scanner sc = new Scanner(getClass().getResourceAsStream("/stage2TextFiles/passengerGroups.txt")); 
			String line = "";
			String destinationName = "";
			String passengergroupID = "";
			String numPassengers = "";

			while ( sc.hasNext() ){ 
				line = sc.nextLine(); 
				Scanner fielder = new Scanner(line).useDelimiter(",");
				destinationName = fielder.next(); //get first field
				destinationName = destinationName.trim(); //delete white spaces
				passengergroupID = fielder.next();
				passengergroupID =passengergroupID.trim();
				numPassengers = fielder.next(); //get second field
				numPassengers = numPassengers.trim(); //delete white spaces

				PassengerGroup d = new PassengerGroup(destinationName, passengergroupID, Integer.parseInt(numPassengers) );

				passengerGroupCollection.add(d); //put fields into Map
			}

			sc.close();
			//fr.close();

			System.out.println("PassengerGroups collection: "+passengerGroupCollection);
		
	}
	
	public LinkedList<PassengerGroup> returnListOfPassengerGroups(){
		
		return passengerGroupCollection;
		
	}

	public LinkedList<Car> returnListOfTaxis(){
		return taxisCollection;
	}

}
