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

public enum XfsSystemEvent implements XfsConstant {

	/**
	 * Undeliverable Messages
	 */
	UNDELIVERABLE_MSG(1L),

	/**
	 * Hardware and Software Errors
	 */
	HARDWARE_ERROR(2L),

	/**
	 * Version Negotiation Failures
	 */
	VERSION_ERROR(3L),

	/**
	 * Device Status Changes
	 */
	DEVICE_STATUS(4L),

	/**
	 * Application Disconnect
	 */
	APP_DISCONNECT(5L),

	/**
	 * The error is a software error
	 *
	 * @since 3.00
	 */
	SOFTWARE_ERROR(6L),

	/**
	 * The error is a user error
	 *
	 * @since 3.00
	 */
	USER_ERROR(7L),

	/**
	 * Lock Requested
	 *
	 * @since 3.00
	 */
	LOCK_REQUESTED(8L),

	FRAUD_ATTEMPT(9L);

	private final long value;

	private XfsSystemEvent(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}