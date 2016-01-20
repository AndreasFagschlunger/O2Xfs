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

public enum XfsServiceClass implements XfsConstant {

	/**
	 * Printers
	 */
	PTR(1L, "PTR"),

	/**
	 * Identification Card Units
	 */
	IDC(2L, "IDC"),

	/**
	 * Cash Dispensers
	 */
	CDM(3L, "CDM"),

	/**
	 * PIN pads
	 */
	PIN(4L, "PIN"),

	/**
	 * Check Readers and Scanners
	 */
	CHK(5L, "CHK"),

	/**
	 * Depository Units
	 */
	DEP(6L, "DEP"),

	/**
	 * Text Terminal Units
	 */
	TTU(7L, "TTU"),

	/**
	 * Sensors and Indicators Units
	 */
	SIU(8L, "SIU"),

	/**
	 * Vendor Dependent Mode
	 */
	VDM(9L, "VDM"),

	/**
	 * Cameras
	 */
	CAM(10L, "CAM");

	/**
	 * Class Identifier
	 */
	private final long value;

	/**
	 * Class Name
	 */
	private final String name;

	private XfsServiceClass(final long value, final String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public long getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public final static XfsServiceClass getForServiceClassName(final String className) {
		for (XfsServiceClass serviceClass : values()) {
			if (serviceClass.name.equals(className)) {
				return serviceClass;
			}
		}
		return null;
	}
}