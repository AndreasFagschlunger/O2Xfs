package at.o2xfs.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Library {

	private static final String SEPARATOR;
	private static final String LIB_DIR;

	static {
		SEPARATOR = System.getProperty("file.separator");
		LIB_DIR = ".o2xfs" + SEPARATOR + "lib" + SEPARATOR + arch();
	}

	private static String arch() {
		String osArch = System.getProperty("os.arch");
		if (osArch.equals("i386") || osArch.equals("i686"))
			return "x86";
		if (osArch.equals("amd64"))
			return "x86_64";
		return osArch;
	}

	private Library() {
		throw new AssertionError();
	}

	public static final void loadLibrary(String name) {
		String fileName = name + ".dll";
		Path path = Paths.get(System.getProperty("user.home"), LIB_DIR, fileName);
		System.out.println(path);
		if (!Files.exists(path)) {
			try (InputStream inStream = Library.class.getResourceAsStream("/" + fileName)) {
				Files.createDirectories(path.getParent());
				try (OutputStream outStream = Files.newOutputStream(path)) {
					byte[] buffer = new byte[4096];
					int len = 0;
					while ((len = inStream.read(buffer)) > 0) {
						outStream.write(buffer, 0, len);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.load(path.toString());
	}
}
