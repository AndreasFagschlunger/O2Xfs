/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.util;

import java.nio.ByteBuffer;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.HWND;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsMessage;

/**
 * @author Andreas Fagschlunger
 */
public final class MessageHandler {

	private final static Logger LOG = LoggerFactory
			.getLogger(MessageHandler.class);

	private Thread thread = null;

	private IXfsCallback xfsCallback = null;

	private HWND hWnd = null;

	private boolean windowCreated = false;

	public MessageHandler(final IXfsCallback xfsCallback) {
		if (xfsCallback == null) {
			throw new IllegalArgumentException("xfsCallback must not be null");
		}
		hWnd = new HWND();
		hWnd.allocate();
		this.xfsCallback = xfsCallback;
	}

	public void start() {
		final String method = "start()";
		synchronized (this) {
			if (thread != null) {
				throw new IllegalStateException("Already started");
			}
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					runMessageLoop();
				}
			}, getClass().getSimpleName());
			thread.start();
			try {
				while (!windowCreated) {
					wait();
				}
			} catch (final InterruptedException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Interrupted", e);
				}
			}
		}
	}

	private void runMessageLoop() {
		run0(hWnd.buffer());
	}

	private native void run0(ByteBuffer hWnd);

	private void hWnd() {
		synchronized (this) {
			final String method = "hWnd()";
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "hWnd=" + hWnd);
			}
			windowCreated = true;
			notifyAll();
		}
	}

	private void callback(final int msg, final ByteBuffer buffer) {
		final String method = "callback(int, ByteBuffer)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "msg=" + msg + ",buffer=" + buffer);
		}
		final XfsMessage xfsMessage = XfsConstants.valueOf(msg,
				XfsMessage.class);
		if (xfsMessage == null) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Unknown message: " + msg);
			}
			return;
		}
		final WFSResult wfsResult = new WFSResult(buffer);
		xfsCallback.callback(xfsMessage, wfsResult);
	}

	public void close() {
		final String method = "close()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hWnd=" + hWnd);
		}
		if (hWnd != null) {
			close(hWnd.buffer());
		}
	}

	private native void close(ByteBuffer hWnd);

	/**
	 * @return the hWnd
	 */
	public HWND getHWnd() {
		return hWnd;
	}
}
