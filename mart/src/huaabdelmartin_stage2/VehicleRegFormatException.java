package huaabdelmartin_stage2;

public class VehicleRegFormatException extends Exception {

	public VehicleRegFormatException(String reg){
		super("This registration ("+reg+") is not in the format of LLNN LLL, where L is letter, N is number");
	}
}
