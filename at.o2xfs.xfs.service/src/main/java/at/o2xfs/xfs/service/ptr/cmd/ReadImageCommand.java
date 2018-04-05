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

package at.o2xfs.xfs.service.ptr.cmd;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.ptr.PtrExecuteCommand;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.service.ptr.PtrFactory;
import at.o2xfs.xfs.v3_00.ptr.Image3;
import at.o2xfs.xfs.v3_00.ptr.ImageRequest3;

public class ReadImageCommand extends AbstractAsyncXfsCommand<ReadImageCommandListener, ReadImageCompleteEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(ReadImageCommand.class);

	private final PTRService ptrService;

	private final ImageRequest3 imageRequest;

	public ReadImageCommand(PTRService ptrService) {
		this(ptrService, null);
	}

	public ReadImageCommand(PTRService ptrService, ImageRequest3 imageRequest) {
		if (ptrService == null) {
			throw new IllegalArgumentException("ptrService must not be null");
		}
		this.ptrService = ptrService;
		this.imageRequest = imageRequest;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<>(ptrService, PtrExecuteCommand.READ_IMAGE, imageRequest);
	}

	@Override
	protected ReadImageCompleteEvent createCompleteEvent(Pointer results) {
		final String method = "createCompleteEvent(Pointer)";
		List<Image3> images = new ArrayList<Image3>();
		ZList list = new ZList(results);
		for (Pointer p : list) {
			Image3 image = PtrFactory.INSTANCE.create(ptrService.getXfsVersion(), p, Image3.class);
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "image=" + image);
			}
			images.add(image);
		}
		return new ReadImageCompleteEvent(images);
	}
}