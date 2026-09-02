package Errors;

public class InvalidTimeFormat extends Exception{
	
	public InvalidTimeFormat(String format) {
		super("Format tipa "+format+" nije dozvoljen! (Format je hh:mm)!");
	}

}
