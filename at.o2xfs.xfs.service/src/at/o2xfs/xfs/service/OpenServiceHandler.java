package at.o2xfs.xfs.service;

import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.config.ConfigKey;
import at.o2xfs.xfs.service.config.XfsServiceConfig;
import at.o2xfs.xfs.service.lookup.XfsServiceLookup;
import at.o2xfs.xfs.service.lookup.impl.EmptyXfsServiceLookupImpl;

class OpenServiceHandler {

	private static final ConfigKey<String> LOOKUP_CLASS = new ConfigKey.Builder<String>()
			.key("XfsServiceLookup").key("Class")
			.defaultValue(EmptyXfsServiceLookupImpl.class.getCanonicalName())
			.build();

	OpenServiceHandler() {
		openServices();
	}

	private void openServices() {
		XfsServiceLookup serviceLookup = XfsServiceConfig.getInstance()
				.newInstance(LOOKUP_CLASS, XfsServiceLookup.class);
		for (XfsServiceLookup.ServiceEntry entry : serviceLookup) {
			try {
				XfsServiceManager.getInstance().openAndRegister(
						entry.getLogicalName(), entry.getServicClass());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (XfsException e) {
				e.printStackTrace();
			}
		}
	}

}
