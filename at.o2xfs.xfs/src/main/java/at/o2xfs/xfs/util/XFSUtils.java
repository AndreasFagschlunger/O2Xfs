/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.util;

/**
 * @author Andreas Fagschlunger
 */
public class XFSUtils {

	public static int getVersionsRequired(String lowestVersion, String highestVersion) {
		int versionsRequired = getVersion(lowestVersion);
		versionsRequired <<= 16;
		versionsRequired |= getVersion(highestVersion);
		return versionsRequired;
	}

	public static int getVersion(String version) {
		int result = 0;
		int endIndex = version.indexOf('.');
		if (endIndex >= 0) {
			String minorVersion = version.substring(endIndex + 1);
			if (minorVersion.length() > 0) {
				try {
					int minor = Integer.parseInt(minorVersion);
					if (minor < 0 || minor > 255) {
						throw new IllegalArgumentException("Minor version out of range. Value:\"" + minor + "\"");
					}
					minor <<= 8;
					result |= minor;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		} else {
			endIndex = version.length();
		}
		String majorVersion = version.substring(0, endIndex);
		if (majorVersion.length() > 0) {
			int major = Integer.parseInt(majorVersion);
			if (major < 0 || major > 255) {
				throw new IllegalArgumentException("Major version out of range. Value:\"" + majorVersion + "\"");
			}
			result |= (major & 0xFF);
		}
		return result;
	}

	public static String getVersionAsString(int i) {
		String v = Integer.toString(i & 0xFF);
		return v + "." + Integer.toString((i) >>> 8);
	}
}