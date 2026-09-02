package Errors;

public class DuplicateNames extends Exception{
	
	public DuplicateNames(String name) {
		super("Aerodrom sa kodom "+name+" vec postoji u sistemu");
	}
}
