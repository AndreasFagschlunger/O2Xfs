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

public enum XfsSystemEvent implements XfsConstant {

	/**
	 * Undeliverable Messages
	 */
	WFS_SYSE_UNDELIVERABLE_MSG(1L),

	/**
	 * Hardware and Software Errors
	 */
	WFS_SYSE_HARDWARE_ERROR(2L),

	/**
	 * Version Negotiation Failures
	 */
	WFS_SYSE_VERSION_ERROR(3L),

	/**
	 * Device Status Changes
	 */
	WFS_SYSE_DEVICE_STATUS(4L),

	/**
	 * Application Disconnect
	 */
	WFS_SYSE_APP_DISCONNECT(5L),

	/**
	 * The error is a software error
	 * 
	 * @since 3.00
	 */
	WFS_SYSE_SOFTWARE_ERROR(6L),

	/**
	 * The error is a user error
	 * 
	 * @since 3.00
	 */
	WFS_SYSE_USER_ERROR(7L),

	/**
	 * Lock Requested
	 * 
	 * @since 3.00
	 */
	WFS_SYSE_LOCK_REQUESTED(8L);

	private final long value;

	private XfsSystemEvent(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}