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

package at.o2xfs.operator.task.xfs.ptr;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.ptr.PTRCapabilitiesCallable;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.v3_00.ptr.PtrCapabilities3;

public class PTRCapabilitiesTask extends PTRServiceTask {

	private static final Logger LOG = LoggerFactory.getLogger(PTRCapabilitiesTask.class);

	private Table table = null;

	public PTRCapabilitiesTask() {
		super();
	}

	public PTRCapabilitiesTask(PTRService service) {
		super(service);
	}

	@Override
	protected void execute() {
		final String method = "execute()";
		try {
			final PtrCapabilities3 caps = new PTRCapabilitiesCallable(service).call();
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "caps=" + caps);
			}
			table = new Table(getClass(), "Capability", "Value");
			addRow("ServiceClass", caps.getServiceClass());
			addRow("Type", caps.getTypes());
			addRow("Compound", caps.isCompound());
			addRow("Resolution", caps.getResolutions());
			addRow("ReadForm", caps.getReadForms());
			addRow("WriteForm", caps.getWriteForms());
			addRow("Extents", caps.getExtents());
			addRow("Control", caps.getControls());
			addRow("MaxMediaOnStacker", caps.getMaxMediaOnStacker());
			addRow("AcceptMedia", caps.isAcceptMedia());
			addRow("MultiPage", caps.isMultiPage());
			addRow("PaperSources", caps.getPaperSources());
			addRow("MediaTaken", caps.isMediaTaken());
			addRow("ImageType", caps.getImageTypes());
			addRow("FrontImageColorFormat", caps.getFrontImageColorFormats());
			addRow("BackImageColorFormat", caps.getBackImageColorFormats());
			addRow("CodelineFormat", caps.getCodelineFormats());
			addRow("ImageSource", caps.getImageSources());
			addRow("CharSupport", caps.getCharSupport());
			addRow("DispensePaper", caps.isDispensePaper());
			addRow("Extra", caps.getExtra());
			getContent().setUIElement(table);
		} catch (final XfsException e) {
			showException(e);
		}
	}

	private void addRow(final String label, final Object value) {
		table.addRow(new Label(getClass(), label), value);
	}
}