package game;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException(String configFilename, String problem) {
		super("Bad config file format in file: " + configFilename + " problem was: "+ problem);
	}
}
