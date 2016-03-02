package huaabdelmartin_stage2;

public class Driver {
	// Instance variables

	private  String  firstName;
	private  String  lastName;
	private  String  driverID;

	//constructor
	public Driver(String firstName, String lastName, String driverID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.driverID = driverID;
	}

	//get and set
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public String getLastThenFirst(){
		return lastName + ", " + firstName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverID == null) ? 0 : driverID.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (driverID == null) {
			if (other.driverID != null)
				return false;
		} else if (!driverID.equals(other.driverID))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
	public String toString(){
		return "Driver ("+driverID+"): "+getLastThenFirst();
	}
}
