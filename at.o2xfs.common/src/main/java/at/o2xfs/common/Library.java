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

package at.o2xfs.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Library {

	private static final boolean CLEAN = Boolean.getBoolean("clean");

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
		try {
			if (!Files.exists(path) || (CLEAN && Files.deleteIfExists(path))) {
				try (InputStream inStream = Library.class.getResourceAsStream("/" + arch() + "/" + fileName)) {
					if (inStream == null) {
						throw new UnsatisfiedLinkError("Library not found: " + arch() + "/" + fileName);
					}
					Files.createDirectories(path.getParent());
					try (OutputStream outStream = Files.newOutputStream(path)) {
						byte[] buffer = new byte[4096];
						int len = 0;
						while ((len = inStream.read(buffer)) > 0) {
							outStream.write(buffer, 0, len);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Extracting " + fileName + " failed: " + e.getMessage());
		}
		System.load(path.toString());
	}
}