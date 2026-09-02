package Errors;

public class FileNotFound extends Exception{
	
	public FileNotFound(String filename) {
		super("Fajl sa imenom "+filename+" ne postoji!");
	}

}
