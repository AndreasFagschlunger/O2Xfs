package at.o2xfs.win32;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

public class SystemtimeTest {

	@Test
	public void test() {
		LocalDateTime expected = LocalDateTime.now();
		SYSTEMTIME systemtime = new SYSTEMTIME();
		systemtime.allocate();
		systemtime.set(expected);
		assertEquals(expected, systemtime.get());
	}
}
