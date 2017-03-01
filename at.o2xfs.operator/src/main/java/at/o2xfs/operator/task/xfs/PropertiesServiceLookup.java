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

package at.o2xfs.operator.task.xfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsServiceClass;

public class PropertiesServiceLookup {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesServiceLookup.class);

	private Properties serviceProperties = null;

	public PropertiesServiceLookup() {
		serviceProperties = new Properties();
	}

	private void loadProperties() {
		final String method = "loadProperties()";
		final File file = new File("services.properties");
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			serviceProperties.load(in);
		} catch (final Exception e) {
			LOG.error(method, "Error loading properties from file: " + file, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing stream", e);
					}
				}
			}
		}
	}

	public Map<String, XfsServiceClass> lookup() {
		final Map<String, XfsServiceClass> services = new HashMap<String, XfsServiceClass>();
		loadProperties();
		for (final XfsServiceClass serviceClass : XfsServiceClass.values()) {
			final List<String> serviceNames = getServiceNames(serviceClass);
			for (final String name : serviceNames) {
				services.put(name, serviceClass);
			}
		}
		return services;
	}

	private List<String> getServiceNames(final XfsServiceClass serviceClass) {
		final List<String> names = new ArrayList<String>();
		final String value = serviceProperties.getProperty(serviceClass.name());
		if (value != null) {
			for (final String name : value.split(",")) {
				names.add(name.trim());
			}
		}
		return names;
	}

}