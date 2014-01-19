package at.o2xfs.xfs.service.lookup.impl;

import java.util.Collections;
import java.util.Iterator;

import at.o2xfs.xfs.service.lookup.XfsServiceLookup;

public class EmptyXfsServiceLookupImpl extends XfsServiceLookup {

	@Override
	public Iterator<ServiceEntry> iterator() {
		return Collections.<ServiceEntry> emptyList().iterator();
	}
}
