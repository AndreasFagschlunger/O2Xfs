package at.o2xfs.log.slf4j;

import at.o2xfs.log.Logger;

public class SLF4JLogger implements Logger {

	private org.slf4j.Logger logger = null;

	public SLF4JLogger(org.slf4j.Logger logger) {
		if (logger == null) {
			throw new IllegalArgumentException("logger cannot be null");
		}
		this.logger = logger;
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String method, Object message) {
		logger.debug(method + ": " + String.valueOf(message));
	}

	@Override
	public void debug(String method, Object message, Throwable t) {
		logger.debug(method + ": " + String.valueOf(message), t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public void info(String method, Object message) {
		logger.info(method + ": " + String.valueOf(message));
	}

	@Override
	public void info(String method, Object message, Throwable t) {
		logger.info(method + ": " + String.valueOf(message), t);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String method, Object message) {
		logger.warn(method + ": " + String.valueOf(message));
	}

	@Override
	public void warn(String method, Object message, Throwable t) {
		logger.warn(method + ": " + String.valueOf(message), t);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String method, Object message) {
		logger.error(method + ": " + String.valueOf(message));
	}

	@Override
	public void error(String method, Object message, Throwable t) {
		logger.error(method + ": " + String.valueOf(message), t);
	}

}
