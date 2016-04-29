package at.o2xfs.xfs.service.cdm.xfs3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.CurrencyExp3;
import at.o2xfs.xfs.service.ReflectiveFactory;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class CurrencyExpCommand implements Callable<Collection<CurrencyExp3>> {

	private final CdmService service;

	public CurrencyExpCommand(CdmService service) {
		this.service = service;
	}

	@Override
	public Collection<CurrencyExp3> call() throws XfsException {
		List<CurrencyExp3> result;
		XfsInfoCommand<CdmInfoCommand> infoCommand = new XfsInfoCommand<CdmInfoCommand>(service, CdmInfoCommand.CURRENCY_EXP);
		WFSResult wfsResult = null;
		try {
			wfsResult = infoCommand.call();
			result = new ArrayList<>();
			ZList lppCurrencyExp = new ZList(wfsResult.getResults());
			for (Pointer p : lppCurrencyExp) {
				result.add(ReflectiveFactory.create(service.getXfsVersion(), p, CurrencyExp3.class));
			}
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
		return Collections.unmodifiableList(result);
	}
}