package cz.instance.gtt.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingUtils {

	public static void setLogging() {

		Logger logger = Logger.getLogger("com.google.api.client");
		logger.setLevel(Level.CONFIG);
		logger.addHandler(new ConsoleHandler() {

			@Override
			public void close() throws SecurityException {
			}

			@Override
			public void flush() {
			}

			@Override
			public void publish(LogRecord record) {
				if (record.getLevel().intValue() < Level.INFO.intValue()) {
					System.out.println(record.getMessage());
				}
			}
		});

	}
}
