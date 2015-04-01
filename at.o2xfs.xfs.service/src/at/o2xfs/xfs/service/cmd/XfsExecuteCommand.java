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

package at.o2xfs.xfs.service.cmd;

import at.o2xfs.common.Assert;
import at.o2xfs.win32.Type;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.service.XfsService;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 *
 */
public class XfsExecuteCommand<T extends Enum<? extends XfsConstant>>
		extends XfsCommand {

	private final T command;

	private final Type cmdData;

	public XfsExecuteCommand(XfsService xfsService, T command) {
		this(xfsService, command, null);
	}

	public XfsExecuteCommand(XfsService xfsService, T command, Type cmdData) {
		this(xfsService, command, cmdData, null);
	}

	public XfsExecuteCommand(XfsService xfsService, T command, Type cmdData, Long timeOut) {
		super(xfsService, timeOut);
		Assert.notNull(command);
		this.command = command;
		this.cmdData = cmdData;
	}

	/**
	 * @return the command
	 */
	public T getCommand() {
		return command;
	}

	/**
	 * @return the cmdData
	 */
	public Type getCmdData() {
		return cmdData;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("command", command)
										.append("cmdData", cmdData)
										.toString();
	}
}