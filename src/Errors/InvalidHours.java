package Errors;

public class InvalidHours extends Exception{
	public InvalidHours() {
		super("Uneti sati se ne nalaze u opsegu 0-24!");
	}
}
