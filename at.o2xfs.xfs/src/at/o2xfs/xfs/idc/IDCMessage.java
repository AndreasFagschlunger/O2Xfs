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

package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IDCMessage implements XfsConstant {

	/**
	 * This execute event specifies that a track contained invalid or no data.
	 */
	WFS_EXEE_IDC_INVALIDTRACKDATA(201L),

	/**
	 * This execute event specifies that a card was inserted into the device.
	 */
	WFS_EXEE_IDC_MEDIAINSERTED(203L),

	/**
	 * This service event specifies that the inserted card was manually removed
	 * by the user during the processing of a read/write command or after an
	 * eject operation.
	 */
	WFS_SRVE_IDC_MEDIAREMOVED(204L),

	/**
	 * This service event specifies that a card has been retained or ejected by
	 * either the automatic power on or power off action of the device.
	 */
	WFS_SRVE_IDC_CARDACTION(205L),

	/**
	 * This user event specifies that the retain bin holding the retained cards
	 * is near full (according to the threshold value in the registry),
	 * requiring operator intervention soon.
	 */
	WFS_USRE_IDC_RETAINBINTHRESHOLD(206L),

	/**
	 * This execute event specifies that the media the user is attempting to
	 * insert is not a valid card or it is a card but it is in the wrong
	 * orientation.
	 */
	WFS_EXEE_IDC_INVALIDMEDIA(207L),

	/**
	 * This service event specifies that the card was retained.
	 * 
	 * @since 3.00
	 */
	WFS_EXEE_IDC_MEDIARETAINED(208L),

	/**
	 * This service event is generated if media is detected during a reset
	 * (WFS_CMD_IDC_RESET). The parameter on the event informs the application
	 * of the position of the card on the completion of the reset.
	 * 
	 * @since 3.00
	 */
	WFS_SRVE_IDC_MEDIADETECTED(209L),

	/**
	 * This event specifies that the retain bin has been inserted.
	 * 
	 * @since 3.10
	 */
	WFS_SRVE_IDC_RETAINBININSERTED(210L),

	/**
	 * This event specifies that the retain bin has been removed.
	 * 
	 * @since 3.10
	 */
	WFS_SRVE_IDC_RETAINBINREMOVED(211L),

	/**
	 * This mandatory event notifies the application when the device is ready
	 * for the user to insert a card.
	 * 
	 * @since 3.10
	 */
	WFS_EXEE_IDC_INSERTCARD(212L),

	/**
	 * This service event reports that the device has changed its position
	 * status.
	 * 
	 * @since 3.10
	 */
	WFS_SRVE_IDC_DEVICEPOSITION(213L),

	/**
	 * This service event specifies that the power save recovery time has
	 * changed.
	 * 
	 * @since 3.10
	 */
	WFS_SRVE_IDC_POWER_SAVE_CHANGE(214L);

	private final long value;

	private IDCMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}