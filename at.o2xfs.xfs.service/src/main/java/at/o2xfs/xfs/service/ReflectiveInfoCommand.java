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