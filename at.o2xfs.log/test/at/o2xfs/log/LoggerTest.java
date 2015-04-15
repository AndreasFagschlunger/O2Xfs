package at.o2xfs.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

	@Test
	public void test() {
		String message = "This is a test message";
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PrintStream tmpOut = System.out;
		try {
			System.setOut(new PrintStream(outStream));
			Logger LOG = LoggerFactory.getLogger(LoggerTest.class);
			String method = "test()";
			LOG.info(method, message);
		} finally {
			System.setOut(tmpOut);
		}
		String log = new String(outStream.toByteArray());
		Assert.assertTrue(log.contains(message));
	}
}