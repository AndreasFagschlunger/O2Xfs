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

package at.o2xfs.xfs.service.cmd.pin;

import java.util.EnumSet;
import java.util.Set;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.PINInfoCommand;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.IXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.util.Bitmask;

/**
 * This command returns information about the names of the Function Keys
 * supported by the device. Location information is also returned for the
 * supported FDKs (Function Descriptor Keys) or Touch Screen Pads if this XFS
 * interface is used for Touch Screen input.
 */
public class PINFunctionKeysCommand implements IXfsCommand<WFSPINFUNCKEYDETAIL> {

	private final static Logger LOG = LoggerFactory
			.getLogger(PINFunctionKeysCommand.class);

	private PINService pinService = null;

	private Set<PINFDK> fdks = null;

	public PINFunctionKeysCommand(final PINService pinService) {
		this(pinService, EnumSet.allOf(PINFDK.class));
	}

	public PINFunctionKeysCommand(final PINService pinService,
			final Set<PINFDK> fdks) {
		this.pinService = pinService;
		this.fdks = fdks;
	}

	@Override
	public WFSPINFUNCKEYDETAIL execute() throws InterruptedException,
			XfsException {
		final String method = "execute()";
		final DWORD fdkMask = new DWORD(Bitmask.of(fdks));
		final XfsCommand xfsCommand = new XfsInfoCommand(pinService,
				PINInfoCommand.WFS_INF_PIN_FUNCKEY_DETAIL, fdkMask);
		final WFSResult wfsResult = xfsCommand.call();
		try {
			final WFSPINFUNCKEYDETAIL funcKeyDetail = new WFSPINFUNCKEYDETAIL(
					wfsResult.getBuffer());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "funcKeyDetail=" + funcKeyDetail);
			}
			return new WFSPINFUNCKEYDETAIL(funcKeyDetail);
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}
}
