package huaabdelmartin_stage2;

public class hireMoreWorkersException extends Exception {

	public hireMoreWorkersException(int numWorkers){
		super("Hire more workers, this many turned up today: "+numWorkers);
	}
}