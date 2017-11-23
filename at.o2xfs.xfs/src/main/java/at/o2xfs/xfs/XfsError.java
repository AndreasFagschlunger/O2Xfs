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

package at.o2xfs.xfs;

public enum XfsError implements XfsConstant {

	SUCCESS(0L),
	ALREADY_STARTED(-1L),
	API_VER_TOO_HIGH(-2L),
	API_VER_TOO_LOW(-3L),
	CANCELED(-4L),
	CFG_INVALID_HKEY(-5L),
	CFG_INVALID_NAME(-6L),
	CFG_INVALID_SUBKEY(-7L),
	CFG_INVALID_VALUE(-8L),
	CFG_KEY_NOT_EMPTY(-9L),
	CFG_NAME_TOO_LONG(-10L),
	CFG_NO_MORE_ITEMS(-11L),
	CFG_VALUE_TOO_LONG(-12L),
	DEV_NOT_READY(-13L),
	HARDWARE_ERROR(-14L),
	INTERNAL_ERROR(-15L),
	INVALID_ADDRESS(-16L),
	INVALID_APP_HANDLE(-17L),
	INVALID_BUFFER(-18L),
	INVALID_CATEGORY(-19L),
	INVALID_COMMAND(-20L),
	INVALID_EVENT_CLASS(-21L),
	INVALID_HSERVICE(-22L),
	INVALID_HPROVIDER(-23L),
	INVALID_HWND(-24L),
	INVALID_HWNDREG(-25L),
	INVALID_POINTER(-26L),
	INVALID_REQ_ID(-27L),
	INVALID_RESULT(-28L),
	INVALID_SERVPROV(-29L),
	INVALID_TIMER(-30L),
	INVALID_TRACELEVEL(-31L),
	LOCKED(-32L),
	NO_BLOCKING_CALL(-33L),
	NO_SERVPROV(-34L),
	NO_SUCH_THREAD(-35L),
	NO_TIMER(-36L),
	NOT_LOCKED(-37L),
	NOT_OK_TO_UNLOAD(-38L),
	NOT_STARTED(-39L),
	NOT_REGISTERED(-40L),
	OP_IN_PROGRESS(-41L),
	OUT_OF_MEMORY(-42L),
	SERVICE_NOT_FOUND(-43L),
	SPI_VER_TOO_HIGH(-44L),
	SPI_VER_TOO_LOW(-45L),
	SRVC_VER_TOO_HIGH(-46L),
	SRVC_VER_TOO_LOW(-47L),
	TIMEOUT(-48L),
	UNSUPP_CATEGORY(-49L),
	UNSUPP_COMMAND(-50L),
	VERSION_ERROR_IN_SRVC(-51L),
	INVALID_DATA(-52L),
	SOFTWARE_ERROR(-53L),
	CONNECTION_LOST(-54L),

	/**
	 * A user is preventing proper operation of the device.
	 *
	 * @since 3.00
	 */
	USER_ERROR(-55L),

	/**
	 * The data structure passed as an input parameter although valid for this
	 * service class, is not supported by this service provider or device.
	 *
	 * @since 3.00
	 */
	UNSUPP_DATA(-56L);

	private final long value;

	private XfsError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}