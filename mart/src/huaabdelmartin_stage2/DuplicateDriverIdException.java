package huaabdelmartin_stage2;

public class DuplicateDriverIdException extends Exception {
	
	public DuplicateDriverIdException(String id){
		super("This id ("+id+") is already being used by another driver so can't create this driver, sorry");
	}

}
