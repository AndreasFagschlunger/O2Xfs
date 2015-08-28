/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.service.lookup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsServiceClass;

public class SystemPropertiesServiceLookup extends XfsServiceLookup {

	private static final Logger LOG = LoggerFactory
			.getLogger(SystemPropertiesServiceLookup.class);

	private List<ServiceEntry> lookup() {
		final String method = "lookup()";
		final List<ServiceEntry> services = new ArrayList<ServiceEntry>();
		for (Map.Entry<Object, Object> entry : System.getProperties()
				.entrySet()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, entry.getKey() + "=" + entry.getValue());
			}
			XfsServiceClass serviceClass = XfsServiceClass
					.getForServiceClassName(String.valueOf(entry.getKey()));
			if (serviceClass != null) {
				String logicalName = String.valueOf(entry.getValue());
				if (LOG.isInfoEnabled()) {
					LOG.info(method, serviceClass.name() + ": " + logicalName);
				}
				services.add(new ServiceEntry(logicalName,
						getServicClass(serviceClass)));
			}
		}
		return services;
	}

	@Override
	public Iterator<ServiceEntry> iterator() {
		return lookup().iterator();
	}

}