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

package at.o2xfs.xfs.service.cmd;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsCancelledException;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.service.cmd.event.CompleteEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.events.XfsEventNotification;

public abstract class AbstractAsyncXfsCommand<T extends CommandListener<C>, C extends CompleteEvent>
		implements XfsEventNotification {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractAsyncXfsCommand.class);

	private Thread thread = null;

	protected final List<T> listeners;

	private XfsCommand xfsCommand = null;

	public AbstractAsyncXfsCommand() {
		this.listeners = new CopyOnWriteArrayList<T>();
	}

	public void addCommandListener(T l) {
		listeners.add(l);
	}

	public void removeCommandListener(T l) {
		listeners.remove(l);
	}

	public final void execute() {
		synchronized (this) {
			if (thread != null) {
				return;
			}
			thread = new Thread() {

				@Override
				public void run() {
					try {
						xfsCommand = createCommand();
						doExecute();
					} catch (XfsException e) {
						fireErrorEvent(new ErrorEvent(e));
					}
				}
			};
			thread.start();
		}
	}

	protected void doExecute() throws XfsException {
		xfsCommand.execute(this);
	}

	abstract protected XfsCommand createCommand();

	private void fireCancelEvent(CancelEvent event) {
		for (T l : listeners) {
			l.onCancel(event);
		}
	}

	protected void fireErrorEvent(ErrorEvent event) {
		for (T l : listeners) {
			l.onError(event);
		}
	}

	private void fireCompleteEvent(C event) {
		for (T l : listeners) {
			l.onComplete(event);
		}
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "wfsResult=" + wfsResult);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	@Override
	public void fireOperationCompleteEvent(WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			try {
				XfsException.throwFor(wfsResult.getResult());
				fireCompleteEvent(createCompleteEvent(wfsResult.getResults()));
			} catch (XfsCancelledException e) {
				fireCancelEvent(new CancelEvent());
			} catch (XfsException e) {
				fireErrorEvent(new ErrorEvent(e));
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	protected abstract C createCompleteEvent(Pointer results);

	public void cancel() {
		final String method = "cancel()";
		synchronized (this) {
			if (xfsCommand == null) {
				return;
			}
			try {
				xfsCommand.cancel();
			} catch (XfsException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error cancelling XfsCommand: "
							+ xfsCommand, e);
				}
			}
		}
	}
}