package huaabdelmartin_stage2;

public class Destination {
	private  String  nameOfDestination;
	private double distance;
	
	public Destination(String nameOfDestination, double distance) {

		this.nameOfDestination = nameOfDestination;
		this.distance = distance;
	}
	public String getNameOfDestination() {
		return nameOfDestination;
	}
	public void setNameOfDestination(String nameOfDestination) {
		this.nameOfDestination = nameOfDestination;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime
				* result
				+ ((nameOfDestination == null) ? 0 : nameOfDestination
						.hashCode());
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
		Destination other = (Destination) obj;
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		if (nameOfDestination == null) {
			if (other.nameOfDestination != null)
				return false;
		} else if (!nameOfDestination.equals(other.nameOfDestination))
			return false;
		return true;
	}

	public String toString(){
		return nameOfDestination;
	}
}
