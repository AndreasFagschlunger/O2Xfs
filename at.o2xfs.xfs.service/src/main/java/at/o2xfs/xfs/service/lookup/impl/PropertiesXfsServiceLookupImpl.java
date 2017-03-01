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

package at.o2xfs.xfs.service.lookup.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.lookup.XfsServiceLookup;

public class PropertiesXfsServiceLookupImpl extends XfsServiceLookup {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesXfsServiceLookupImpl.class);

	private final Properties properties;

	private final List<ServiceEntry> serviceEntries;

	public PropertiesXfsServiceLookupImpl() {
		properties = new Properties();
		serviceEntries = new ArrayList<XfsServiceLookup.ServiceEntry>();
		lookup();
	}

	private void lookup() {
		loadProperties();
		parseEntries();
	}

	private void loadProperties() {
		File file = new File("service.properties");
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void parseEntries() {
		for (XfsServiceClass serviceClass : XfsServiceClass.values()) {
			String entry = properties.getProperty(serviceClass.getName());
			if (entry == null) {
				continue;
			}
			Class<? extends XfsService> xfsService = getServicClass(serviceClass);
			String[] serviceNames = entry.split(",");
			for (String serviceName : serviceNames) {
				serviceName = serviceName.trim();
				ServiceEntry serviceEntry = new ServiceEntry(serviceName,
						xfsService);
				serviceEntries.add(serviceEntry);
			}
		}
	}

	@Override
	public Iterator<ServiceEntry> iterator() {
		return serviceEntries.iterator();
	}

}