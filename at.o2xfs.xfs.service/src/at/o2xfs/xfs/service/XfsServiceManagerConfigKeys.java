package at.o2xfs.xfs.service;

import at.o2xfs.xfs.service.config.ConfigKey;
import at.o2xfs.xfs.service.lookup.impl.EmptyXfsServiceLookupImpl;

class XfsServiceManagerConfigKeys {

	public static final ConfigKey<Class<?>> LOOKUP_CLASS = new ConfigKey.Builder<Class<?>>()
			.key("XfsServiceLookup").key("Class")
			.defaultValue(EmptyXfsServiceLookupImpl.class).build();
}
