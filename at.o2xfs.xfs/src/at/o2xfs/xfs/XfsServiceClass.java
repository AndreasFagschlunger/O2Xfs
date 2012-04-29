/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs;

public enum XfsServiceClass {

	/**
	 * Printers
	 */
	WFS_SERVICE_CLASS_PTR(1, "PTR"),

	/**
	 * Identification Card Units
	 */
	WFS_SERVICE_CLASS_IDC(2, "IDC"),

	/**
	 * Cash Dispensers
	 */
	WFS_SERVICE_CLASS_CDM(3, "CDM"),

	/**
	 * PIN pads
	 */
	WFS_SERVICE_CLASS_PIN(4, "PIN"),

	/**
	 * Check Readers and Scanners
	 */
	WFS_SERVICE_CLASS_CHK(5, "CHK"),

	/**
	 * Depository Units
	 */
	WFS_SERVICE_CLASS_DEP(6, "DEP"),

	/**
	 * Text Terminal Units
	 */
	WFS_SERVICE_CLASS_TTU(7, "TTU"),

	/**
	 * Sensors and Indicators Units
	 */
	WFS_SERVICE_CLASS_SIU(8, "SIU"),

	/**
	 * Vendor Dependent Mode
	 */
	WFS_SERVICE_CLASS_VDM(9, "VDM"),

	/**
	 * Cameras
	 */
	WFS_SERVICE_CLASS_CAM(10, "CAM");

	/**
	 * Class Identifier
	 */
	private final long identifier;

	/**
	 * Class Name
	 */
	private final String name;

	private XfsServiceClass(final long identifier, final String name) {
		this.identifier = identifier;
		this.name = name;
	}

	/**
	 * @return the service class identifier
	 */
	public long getIdentifier() {
		return identifier;
	}

	public final static XfsServiceClass getForServiceClassName(
			final String className) {
		for (XfsServiceClass serviceClass : values()) {
			if (serviceClass.name.equals(className)) {
				return serviceClass;
			}
		}
		return null;
	}
}
