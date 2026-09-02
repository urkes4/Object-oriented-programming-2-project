package Errors;

public class InvalidY extends Exception{
	
	public InvalidY() {
		super("Y koordinata se ne nalazi u zadatom opsegu [-90,90]");
	}
}

