package slimevoid.lib.data;

public class LoggerEurysCore extends Logger {

	private static Logger instance;
	
	@Override
	protected String getLoggerName() {
		return "EurysCore";
	}
	
	public static Logger getInstance(String name) {
		if (instance == null)
			instance = new LoggerEurysCore();

		instance.setName(name);

		return instance;
	}

}
