package Errors;

public class InvalidCode extends Exception{
	
	public InvalidCode(String code) {
		super("Kod "+code+" se ne sastoji od 3 karaktera ispisanih velikim slovima!");
	}

}
