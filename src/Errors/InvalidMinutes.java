package Errors;

public class InvalidMinutes extends Exception{
	public InvalidMinutes() {
		super("Uneti minuti se ne nalaze u opsegu 0-24!");
	}
}
