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

package at.o2xfs.xfs.service;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsEventClass;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsOpenCommand;
import at.o2xfs.xfs.service.cmd.XfsRegisterCommand;

public class XfsServiceStartUp {

	private static final Logger LOG = LoggerFactory
			.getLogger(XfsServiceStartUp.class);

	private XfsService xfsService = null;

	public XfsServiceStartUp(final XfsService xfsService) {
		this.xfsService = xfsService;
	}

	private void open() throws XfsException {
		final XfsCommand openCommand = new XfsOpenCommand(xfsService);
		WFSResult result = null;
		try {
			result = openCommand.call();
		} finally {
			if (result != null) {
				XfsServiceManager.getInstance().free(result);
			}
		}

	}

	private void register() throws XfsException {
		final XfsCommand registerCommand = new XfsRegisterCommand(xfsService,
				XfsEventClass.values());
		WFSResult result = null;
		try {
			result = registerCommand.call();
		} finally {
			if (result != null) {
				XfsServiceManager.getInstance().free(result);
			}
		}

	}

	public void startUp() throws XfsException {
		final String method = "startUp()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Starting up " + xfsService.getLogicalName()
					+ " ...");
		}
		xfsService.getSrvcVersionsRequired().setLowVersion(XfsVersion.V2_00);
		xfsService.getSrvcVersionsRequired().setHighVersion(XfsVersion.V3_20);
		open();
		register();
	}
}