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

public enum XfsError implements XfsConstant {
	WFS_SUCCESS(0L),
	WFS_ERR_ALREADY_STARTED(-1L),
	WFS_ERR_API_VER_TOO_HIGH(-2L),
	WFS_ERR_API_VER_TOO_LOW(-3L),
	WFS_ERR_CANCELED(-4L),
	WFS_ERR_CFG_INVALID_HKEY(-5L),
	WFS_ERR_CFG_INVALID_NAME(-6L),
	WFS_ERR_CFG_INVALID_SUBKEY(-7L),
	WFS_ERR_CFG_INVALID_VALUE(-8L),
	WFS_ERR_CFG_KEY_NOT_EMPTY(-9L),
	WFS_ERR_CFG_NAME_TOO_LONG(-10L),
	WFS_ERR_CFG_NO_MORE_ITEMS(-11L),
	WFS_ERR_CFG_VALUE_TOO_LONG(-12L),
	WFS_ERR_DEV_NOT_READY(-13L),
	WFS_ERR_HARDWARE_ERROR(-14L),
	WFS_ERR_INTERNAL_ERROR(-15L),
	WFS_ERR_INVALID_ADDRESS(-16L),
	WFS_ERR_INVALID_APP_HANDLE(-17L),
	WFS_ERR_INVALID_BUFFER(-18L),
	WFS_ERR_INVALID_CATEGORY(-19L),
	WFS_ERR_INVALID_COMMAND(-20L),
	WFS_ERR_INVALID_EVENT_CLASS(-21L),
	WFS_ERR_INVALID_HSERVICE(-22L),
	WFS_ERR_INVALID_HPROVIDER(-23L),
	WFS_ERR_INVALID_HWND(-24L),
	WFS_ERR_INVALID_HWNDREG(-25L),
	WFS_ERR_INVALID_POINTER(-26L),
	WFS_ERR_INVALID_REQ_ID(-27L),
	WFS_ERR_INVALID_RESULT(-28L),
	WFS_ERR_INVALID_SERVPROV(-29L),
	WFS_ERR_INVALID_TIMER(-30L),
	WFS_ERR_INVALID_TRACELEVEL(-31L),
	WFS_ERR_LOCKED(-32L),
	WFS_ERR_NO_BLOCKING_CALL(-33L),
	WFS_ERR_NO_SERVPROV(-34L),
	WFS_ERR_NO_SUCH_THREAD(-35L),
	WFS_ERR_NO_TIMER(-36L),
	WFS_ERR_NOT_LOCKED(-37L),
	WFS_ERR_NOT_OK_TO_UNLOAD(-38L),
	WFS_ERR_NOT_STARTED(-39L),
	WFS_ERR_NOT_REGISTERED(-40L),
	WFS_ERR_OP_IN_PROGRESS(-41L),
	WFS_ERR_OUT_OF_MEMORY(-42L),
	WFS_ERR_SERVICE_NOT_FOUND(-43L),
	WFS_ERR_SPI_VER_TOO_HIGH(-44L),
	WFS_ERR_SPI_VER_TOO_LOW(-45L),
	WFS_ERR_SRVC_VER_TOO_HIGH(-46L),
	WFS_ERR_SRVC_VER_TOO_LOW(-47L),
	WFS_ERR_TIMEOUT(-48L),
	WFS_ERR_UNSUPP_CATEGORY(-49L),
	WFS_ERR_UNSUPP_COMMAND(-50L),
	WFS_ERR_VERSION_ERROR_IN_SRVC(-51L),
	WFS_ERR_INVALID_DATA(-52L),
	WFS_ERR_SOFTWARE_ERROR(-53L),
	WFS_ERR_CONNECTION_LOST(-54L),

	/**
	 * A user is preventing proper operation of the device.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_USER_ERROR(-55L),

	/**
	 * The data structure passed as an input parameter although valid for this
	 * service class, is not supported by this service provider or device.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_UNSUPP_DATA(-56L);

	private final long value;

	private XfsError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}