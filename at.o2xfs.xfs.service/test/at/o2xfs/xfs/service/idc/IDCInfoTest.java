/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.idc;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.idc.IDCInfoCommand;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.idc.WFSIDCFORM;
import at.o2xfs.xfs.idc.WfsIDCStatus;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.idc.cmd.IDCCapabilitiesCommand;
import at.o2xfs.xfs.service.idc.cmd.IDCStatusCommand;

import java.util.List;

import org.junit.Test;

public class IDCInfoTest
		extends XfsCommandTest {

	private static final Logger LOG = LoggerFactory.getLogger(IDCInfoTest.class);

	private List<IDCService> idcServices = serviceManager.getServices(IDCService.class);

	@Test
	public void status() throws Exception {
		for (final IDCService idcService : idcServices) {
			executeIDCStatus(idcService);
		}
	}

	private void executeIDCStatus(final IDCService idcService) throws Exception {
		final IDCStatusCommand command = new IDCStatusCommand(idcService);
		final WfsIDCStatus idcStatus = command.call();
		LOG.info("executeIDCStatus(IDCService)", "idcStatus=" + idcStatus);
	}

	@Test
	public void capabilities() throws Exception {
		for (final IDCService idcService : idcServices) {
			executeIDCCapabilities(idcService);
		}
	}

	private void executeIDCCapabilities(final IDCService idcService) throws Exception {
		final IDCCapabilitiesCommand command = new IDCCapabilitiesCommand(idcService);
		final WFSIDCCAPS caps = command.call();
		LOG.info("executeIDCCapabilities(IDCService)", caps);
	}

	@Test
	public void listForms() throws Exception {
		for (final IDCService idcService : idcServices) {
			XfsCommand xfsCommand = new XfsInfoCommand<IDCInfoCommand>(idcService, IDCInfoCommand.FORM_LIST);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				LPZZSTR formList = new LPZZSTR(wfsResult.getResults());
				for (final String formName : formList.values()) {
					queryForm(formName);
				}
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}

	private void queryForm(final String formName) throws Exception {
		for (final IDCService idcService : idcServices) {
			ZSTR szFormName = new ZSTR(formName);
			XfsCommand xfsCommand = new XfsInfoCommand<IDCInfoCommand>(	idcService,
																		IDCInfoCommand.QUERY_FORM,
																		szFormName);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSIDCFORM form = new WFSIDCFORM(wfsResult.getResults());
				LOG.info("queryForm(String)", form);
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}
}