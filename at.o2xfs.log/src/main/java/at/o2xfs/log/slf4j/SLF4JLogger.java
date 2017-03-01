/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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