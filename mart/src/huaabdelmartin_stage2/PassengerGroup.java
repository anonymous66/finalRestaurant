package huaabdelmartin_stage2;

public class PassengerGroup {
	
	private String desination;
	private String passengergroupID;
	private int numberPassengers;
	
	public PassengerGroup(String desination,String pgID, int numberPassengers) {
		this.desination = desination;
		this.passengergroupID = pgID;
		this.numberPassengers = numberPassengers;
	}

	public String getDesination() {
		return desination;
	}

	public void setDesination(String desination) {
		this.desination = desination;
	}

	public String getPassengergroupID() {
		return passengergroupID;
	}

	public void setPassengergroupID(String pgID) {
		this.passengergroupID = pgID;
	}

	public int getNumberPassengers() {
		return numberPassengers;
	}

	public void setNumberPassengers(int numberPassengers) {
		this.numberPassengers = numberPassengers;
	}
	
	public String toString(){
		return "Destination: "+desination+ ", PassengerGroupID: "+ passengergroupID +", Number passengers: "+numberPassengers;
	}

	

}
