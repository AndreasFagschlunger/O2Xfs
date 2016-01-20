package at.o2xfs.xfs.service;

import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

import java.util.concurrent.Callable;

public abstract class AbstractInfoCommand<S, C extends Enum<? extends XfsConstant>, R>
		implements Callable<R> {

	protected final S service;

	public AbstractInfoCommand(S service) {
		this.service = service;
	}

	protected abstract XfsInfoCommand<C> createInfoCommand();

	protected abstract R createResult(WFSResult wfsResult);

	@Override
	public R call() throws XfsException {
		XfsInfoCommand<C> command = createInfoCommand();
		WFSResult wfsResult = command.call();
		try {
			return createResult(wfsResult);
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}
}