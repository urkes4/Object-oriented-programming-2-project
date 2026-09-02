package Errors;

public class UnknownName extends Exception{

	public UnknownName(String n) {
		super("Ne postoji aerodrom sa kodom "+n+" u sistemu");
	}
}
