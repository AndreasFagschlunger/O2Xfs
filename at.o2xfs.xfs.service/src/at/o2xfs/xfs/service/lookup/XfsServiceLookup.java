package at.o2xfs.xfs.service.lookup;

import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.lookup.XfsServiceLookup.ServiceEntry;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.ptr.PTRService;

public abstract class XfsServiceLookup implements Iterable<ServiceEntry> {

	public static class ServiceEntry {

		private final String logicalName;

		private final Class<? extends XfsService> servicClass;

		public ServiceEntry(String logicalName,
				Class<? extends XfsService> servicClass) {
			this.logicalName = logicalName;
			this.servicClass = servicClass;
		}

		public String getLogicalName() {
			return logicalName;
		}

		public Class<? extends XfsService> getServicClass() {
			return servicClass;
		}
	}

	protected Class<? extends XfsService> getServicClass(
			XfsServiceClass serviceClass) {
		switch (serviceClass) {
			case PTR:
				return PTRService.class;
			case IDC:
				return IDCService.class;
			case PIN:
				return PINService.class;
			default:
				throw new IllegalArgumentException(
						"Unsupported XfsServiceClass: " + serviceClass);

		}
	}
}
