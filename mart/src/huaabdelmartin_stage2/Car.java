package huaabdelmartin_stage2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Car {
	//instance variables
	private String regNumber;
	private Driver driver; 
	private boolean availability; 
	private int passengerCount;
	//private String extraPassengerCount;

	//constructor
	public Car(String regNumber, Driver driver, boolean availability,
			int passengerCount) throws VehicleRegFormatException{

		String patternString = "[A-Z]{2}[0-9]{2}\\s{1}[A-Z]{3}";//e.g. LL12 LLL
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(regNumber);
		if( !matcher.matches() ){

			throw new VehicleRegFormatException(regNumber);
		}
		else{
			this.regNumber = regNumber;
		}


		this.driver = driver;
		this.availability = availability;
		this.passengerCount = passengerCount;
		//this.extraPassengerCount = extraPassengerCount;
	}

	public String getRegNumber() {
		return regNumber;
	}


	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}


	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	public boolean isAvailability() {
		return availability;
	}


	public void setAvailability(boolean availability) {
		this.availability = availability;
	}


	public int getPassengerCount() {
		return passengerCount;
	}


	public void setPassengerCount(int passengerCount) {
		this.passengerCount = passengerCount;
	}
	
	public String toString(){
		return "Taxi Reg: "+regNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (availability ? 1231 : 1237);
		result = prime * result
				+ ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + passengerCount;
		result = prime * result
				+ ((regNumber == null) ? 0 : regNumber.hashCode());
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
		Car other = (Car) obj;
		if (availability != other.availability)
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (passengerCount != other.passengerCount)
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		return true;
	}




}
