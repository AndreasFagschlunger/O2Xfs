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
