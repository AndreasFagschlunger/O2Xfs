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

public enum XfsErrorAction implements XfsConstant {

	/**
	 * No action required. Error was autorecovered.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_NOACTION(0x0000L),

	/**
	 * Reset device to attempt recovery.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_RESET(0x0001L),

	/**
	 * A software error occurred. Contact software vendor.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_SWERROR(0x0002L),

	/**
	 * A configuration error occurred. Check configuration.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_CONFIG(0x0004L),

	/**
	 * Recovery is not possible. A manual intervention for clearing the device
	 * is required. This value is only used for hardware errors.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_HWCLEAR(0x0008L),

	/**
	 * Recovery is not possible. A technical maintenance intervention is
	 * required. This value is only used for hardware errors.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_HWMAINT(0x0010L),

	/**
	 * Device will attempt auto recovery and will advise any further action
	 * required via a Device Status Event.
	 * 
	 * @since 3.03
	 */
	WFS_ERR_ACT_SUSPEND(0x0020L);

	private final long value;

	private XfsErrorAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}