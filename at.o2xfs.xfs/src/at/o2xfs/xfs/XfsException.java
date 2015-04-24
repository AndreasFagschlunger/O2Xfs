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

package at.o2xfs.xfs;

import at.o2xfs.common.Assert;
import at.o2xfs.xfs.cam.CamExceptionFactory;
import at.o2xfs.xfs.idc.IDCServiceExceptionFactory;
import at.o2xfs.xfs.pin.PINServiceExceptionFactory;
import at.o2xfs.xfs.siu.SIUServiceExceptionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @see AbstractXfsExceptionFactory
 *
 * @author Andreas Fagschlunger
 */
public abstract class XfsException
		extends Exception {

	private static List<AbstractXfsExceptionFactory> exceptionFactories = null;

	static {
		exceptionFactories = new ArrayList<AbstractXfsExceptionFactory>();
		exceptionFactories.add(new XfsServiceExceptionFactory());
		exceptionFactories.add(new IDCServiceExceptionFactory());
		exceptionFactories.add(new PINServiceExceptionFactory());
		exceptionFactories.add(new SIUServiceExceptionFactory());
		exceptionFactories.add(new CamExceptionFactory());
	}

	private final Enum<? extends XfsConstant> error;

	protected XfsException(final Enum<? extends XfsConstant> error) {
		Assert.notNull(error);
		this.error = error;
	}

	public Enum<? extends XfsConstant> getError() {
		return error;
	}

	@Override
	public String getMessage() {
		return error.name() + "(" + getErrorCode() + ")";
	}

	protected <E extends Enum<E>> E getError(final Class<E> enumType) {
		return enumType.cast(error);
	}

	public static void throwFor(final long errorCode) throws XfsException {
		if (XfsError.WFS_SUCCESS.getValue() == errorCode) {
			return;
		}
		final int serviceOffset = Math.abs((int) errorCode / 100);
		for (AbstractXfsExceptionFactory factory : exceptionFactories) {
			if (factory.getServiceOffset() == serviceOffset) {
				factory.throwException(errorCode);
			}
		}
		throw new IllegalArgumentException("Unknown error code: " + errorCode);
	}

	public long getErrorCode() {
		return ((XfsConstant) error).getValue();
	}
}