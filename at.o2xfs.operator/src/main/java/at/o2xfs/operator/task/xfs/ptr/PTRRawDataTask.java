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

import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.ptr.PTRRawDataCallable;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.v3_00.ptr.RawData3;

public class PTRRawDataTask extends PTRServiceTask {

	public PTRRawDataTask() {
		super();
	}

	public PTRRawDataTask(PTRService service) {
		super(service);
	}

	@Override
	protected void execute() {
		final RawData3 rawData = new RawData3.Builder(new byte[] { 27, 64, 'A' }).build();
		WFSResult wfsResult = null;
		try {
			wfsResult = new PTRRawDataCallable(service, rawData).call();
			System.out.println(wfsResult);
		} catch (final XfsException e) {
			showException(e);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}