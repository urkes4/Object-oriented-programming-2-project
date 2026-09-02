package Errors;

public class AirportNotFound extends Exception{
	
	public AirportNotFound(String code) {
		super("Aerodrom sa kodom "+code+" se ne nalazi u sistemu!");
	}

}
