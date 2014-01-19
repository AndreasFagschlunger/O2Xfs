package at.o2xfs.xfs.service.idc;

import java.util.List;

import org.junit.Test;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.idc.IDCInfoCommand;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.idc.WFSIDCFORM;
import at.o2xfs.xfs.idc.WFSIDCSTATUS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.cmd.idc.IDCCapabilitiesCommand;
import at.o2xfs.xfs.service.cmd.idc.IDCStatusCommand;

public class IDCInfoTest extends XfsCommandTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(IDCInfoTest.class);

	private List<IDCService> idcServices = xfsServiceManager
			.getServices(IDCService.class);

	@Test
	public void status() throws Exception {
		for (final IDCService idcService : idcServices) {
			executeIDCStatus(idcService);
		}
	}

	private void executeIDCStatus(final IDCService idcService) throws Exception {
		final IDCStatusCommand command = new IDCStatusCommand(idcService);
		final WFSIDCSTATUS idcStatus = command.execute();
		LOG.info("executeIDCStatus(IDCService)", "idcStatus=" + idcStatus);
	}

	@Test
	public void capabilities() throws Exception {
		for (final IDCService idcService : idcServices) {
			executeIDCCapabilities(idcService);
		}
	}

	private void executeIDCCapabilities(final IDCService idcService)
			throws Exception {
		final IDCCapabilitiesCommand command = new IDCCapabilitiesCommand(
				idcService);
		final WFSIDCCAPS caps = command.execute();
		LOG.info("executeIDCCapabilities(IDCService)", caps);
	}

	@Test
	public void listForms() throws Exception {
		for (final IDCService idcService : idcServices) {
			XfsCommand xfsCommand = new XfsInfoCommand(idcService,
					IDCInfoCommand.WFS_INF_IDC_FORM_LIST);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				LPZZSTR formList = new LPZZSTR(wfsResult.getResults());
				for (final String formName : formList.values()) {
					queryForm(formName);
				}
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}

	private void queryForm(final String formName) throws Exception {
		for (final IDCService idcService : idcServices) {
			ZSTR szFormName = new ZSTR(formName);
			XfsCommand xfsCommand = new XfsInfoCommand(idcService,
					IDCInfoCommand.WFS_INF_IDC_QUERY_FORM, szFormName);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSIDCFORM form = new WFSIDCFORM(wfsResult.getResults());
				LOG.info("queryForm(String)", form);
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}
}
