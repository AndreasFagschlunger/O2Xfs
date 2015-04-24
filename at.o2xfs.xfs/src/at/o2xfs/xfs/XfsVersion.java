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

package at.o2xfs.xfs;

import at.o2xfs.xfs.util.XFSUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class XfsVersion
		implements Comparable<XfsVersion> {

	/**
	 * Release 2.00
	 */
	public final static XfsVersion V2_00 = new XfsVersion("2.00");

	/**
	 * Release 3.00
	 */
	public final static XfsVersion V3_00 = new XfsVersion("3.00");

	/**
	 * Release 3.10
	 */
	public final static XfsVersion V3_10 = new XfsVersion("3.10");

	/**
	 * Release 3.20
	 */
	public final static XfsVersion V3_20 = new XfsVersion("3.20");

	private int majorVersion = 0;

	private int minorVersion = 0;

	public XfsVersion(final String version) {
		parseVersion(version);
	}

	public XfsVersion(final int version) {
		setVersion(version);
	}

	private void parseVersion(final String version) {
		setVersion(XFSUtils.getVersion(version));

	}

	private void setVersion(final int version) {
		majorVersion = version & 0xFF;
		minorVersion = (version >>> 8 & 0xFF);
	}

	public int intValue() {
		int v = 0;
		v |= minorVersion;
		v <<= 8;
		v |= majorVersion;
		return v;
	}

	/**
	 * Greater than or equal to
	 *
	 * @param version
	 * @return true if {@link #compareTo(XfsVersion)} returns >= 0, false
	 *         otherwise
	 */
	public boolean isGE(final XfsVersion version) {
		return compareTo(version) >= 0;
	}

	/**
	 * Greater than
	 *
	 * @param version
	 * @return
	 */
	public boolean isGT(final XfsVersion version) {
		return compareTo(version) > 0;
	}

	/**
	 * @param version
	 *            the version to be compared.
	 * @return true as this version is less than the specified version.
	 */
	public boolean isLT(final XfsVersion version) {
		return compareTo(version) < 0;
	}

	/**
	 * @param version
	 *            the version to be compared.
	 * @return true as this version is less than or equal to the specified
	 *         version.
	 */
	public boolean isLE(final XfsVersion version) {
		return compareTo(version) <= 0;
	}

	@Override
	public int compareTo(final XfsVersion version) {
		if (version == null || majorVersion > version.majorVersion) {
			return 1;
		} else if (majorVersion < version.majorVersion) {
			return -1;
		} else {
			if (minorVersion > version.minorVersion) {
				return 1;
			} else if (minorVersion < version.minorVersion) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(majorVersion).append(minorVersion).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof XfsVersion) {
			final XfsVersion v = (XfsVersion) obj;
			return new EqualsBuilder().append(majorVersion, v.majorVersion)
										.append(minorVersion, v.minorVersion)
										.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%d.%02d", Integer.valueOf(majorVersion), Integer.valueOf(minorVersion));
	}
}