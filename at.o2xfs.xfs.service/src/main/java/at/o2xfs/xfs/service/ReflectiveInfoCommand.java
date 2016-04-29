package at.o2xfs.xfs.service;

import java.util.concurrent.Callable;

import at.o2xfs.win32.Struct;
import at.o2xfs.win32.Type;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class ReflectiveInfoCommand<S extends XfsService, C extends Enum<? extends XfsConstant>, R extends Struct> implements Callable<R> {

	protected final S service;

	private final C category;

	private final Type queryDetails;

	private final Class<R> resultType;

	public ReflectiveInfoCommand(S service, C category, Class<R> resultType) {
		this(service, category, null, resultType);
	}

	public ReflectiveInfoCommand(S service, C category, Type queryDetails, Class<R> resultType) {
		this.service = service;
		this.category = category;
		this.queryDetails = queryDetails;
		this.resultType = resultType;
	}

	protected XfsInfoCommand<C> createInfoCommand() {
		return new XfsInfoCommand.Builder<>(service, category).queryDetails(queryDetails).build();
	}

	@Override
	public R call() throws XfsException {
		XfsInfoCommand<C> command = createInfoCommand();
		WFSResult wfsResult = command.call();
		R result = null;
		try {
			result = ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), resultType);
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
		return result;
	}
}