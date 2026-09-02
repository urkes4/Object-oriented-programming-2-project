package Errors;

public class InvalidX extends Exception{
	
	public InvalidX() {
		super("X koordinata se ne nalazi u zadatom opsegu [-90,90]");
	}

}

