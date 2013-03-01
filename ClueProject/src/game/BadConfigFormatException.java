package game;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {
	
	// Creates a new exception, and passes the error message to the Exception constructor
	public BadConfigFormatException(String configFilename, String problem) {
		super("Bad config file format in file: " + configFilename + " problem was: "+ problem);
	}
	
}
