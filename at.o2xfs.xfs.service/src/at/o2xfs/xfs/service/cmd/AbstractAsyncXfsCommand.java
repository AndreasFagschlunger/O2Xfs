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