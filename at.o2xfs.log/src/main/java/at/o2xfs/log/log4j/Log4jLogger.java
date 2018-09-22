package at.o2xfs.log.log4j;

import java.util.Objects;

import at.o2xfs.log.Logger;

public class Log4jLogger implements Logger {

	private final org.apache.logging.log4j.Logger logger;

	public Log4jLogger(org.apache.logging.log4j.Logger logger) {
		Objects.requireNonNull(logger);
		this.logger = logger;
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String message, Object... params) {
		logger.debug(message, params);
	}

	@Override
	public void debug(String method, Object message) {
		this.debug(method + ": " + message);
	}

	@Override
	public void debug(String method, Object message, Throwable t) {
		this.debug(method + ": " + message, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String message, Object... params) {
		logger.error(message, params);
	}

	@Override
	public void error(String method, Object message) {
		this.error(method + ": " + message);
	}

	@Override
	public void error(String method, Object message, Throwable t) {
		this.error(method + ": " + message, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public void info(String message, Object... params) {
		logger.info(message, params);
	}

	@Override
	public void info(String method, Object message) {
		this.info(method + ": " + message);
	}

	@Override
	public void info(String method, Object message, Throwable t) {
		this.info(method + ": " + message, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String message, Object... params) {
		logger.warn(message, params);
	}

	@Override
	public void warn(String method, Object message) {
		this.warn(method + ": " + message);
	}

	@Override
	public void warn(String method, Object message, Throwable t) {
		this.warn(method + ": " + message, t);
	}
}
