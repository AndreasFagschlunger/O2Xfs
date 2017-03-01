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

import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.service.siu.SIUService;
import at.o2xfs.xfs.service.ttu.TTUService;

public class XfsServiceFactory {

	private XfsServiceFactory() {
		return;
	}

	public static final <E extends XfsService> E create(final String logicalName, final Class<E> serviceClass) {
		if (CdmService.class.equals(serviceClass)) {
			return serviceClass.cast(new CdmService(logicalName));
		} else if (IDCService.class.equals(serviceClass)) {
			return serviceClass.cast(new IDCService(logicalName));
		} else if (PINService.class.equals(serviceClass)) {
			return serviceClass.cast(new PINService(logicalName));
		} else if (PTRService.class.equals(serviceClass)) {
			return serviceClass.cast(new PTRService(logicalName));
		} else if (SIUService.class.equals(serviceClass)) {
			return serviceClass.cast(new SIUService(logicalName));
		} else if (TTUService.class.equals(serviceClass)) {
			return serviceClass.cast(new TTUService(logicalName));
		}
		throw new IllegalArgumentException("Unknown service class: " + serviceClass);
	}
}