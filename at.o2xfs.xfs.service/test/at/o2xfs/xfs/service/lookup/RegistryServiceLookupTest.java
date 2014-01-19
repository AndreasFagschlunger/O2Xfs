package at.o2xfs.xfs.service.lookup;

import java.util.Map;

import org.junit.Test;

import at.o2xfs.xfs.XfsServiceClass;

public class RegistryServiceLookupTest {

	@Test
	public void test() {
		final Map<String, XfsServiceClass> services = new RegistryServiceLookup()
				.lookup();
		for (Map.Entry<String, XfsServiceClass> service : services.entrySet()) {
			System.out.println(service.getKey() + ": " + service.getValue());
		}

	}
}
