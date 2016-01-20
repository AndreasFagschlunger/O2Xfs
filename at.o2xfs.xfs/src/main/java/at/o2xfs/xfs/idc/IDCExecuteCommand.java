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

public enum IDCExecuteCommand implements XfsConstant {

	/**
	 * For motor driven card readers, the ID card unit checks whether a card has
	 * been inserted. If so, the tracks are read immediately as described in the
	 * form specified by the lpFormsName parameter. If no card has been
	 * inserted, and for all other categories of card readers, the ID card unit
	 * waits for the period of time specified in the WFSExecute call for a card
	 * to be either inserted or pulled through. Again the next step is reading
	 * the tracks specified in the form (see Section 6, Form Definition, for a
	 * more detailed description of the forms mechanism). In addition to that,
	 * the results of a security check via a security module (i.e., MM, CIM86)
	 * are specified and added to the track data.
	 */
	READ_TRACK(201L),

	/**
	 * For motor-driven card readers, the ID card unit checks whether a card has
	 * been inserted. If so, the data is written to the track as described in
	 * the form specified by the lpstrFormName parameter, and the other
	 * parameters. If no card has been inserted, and for all other categories of
	 * devices, the ID card unit waits for the period of time specified in the
	 * WFSExecute call for a card to be either inserted or pulled through. The
	 * next step is writing the data defined by the form and the parameters to
	 * the respective track (see Section 6, Form Definition, for a more detailed
	 * description of the forms mechanism). This procedure is followed by data
	 * verification.
	 */
	WRITE_TRACK(202L),

	/**
	 * The card is driven to the exit slot from where the user can remove it;
	 * applicable only to motor driven card readers. After successful completion
	 * of this command, a service event message is generated to inform the
	 * application when the card is taken. The card remains in position for
	 * withdrawal until either it is taken or the application sends a
	 * WFS_CMD_IDC_RETAIN command to retain the card internally.
	 */
	EJECT_CARD(203L),

	/**
	 * The card is removed from its present position (card inserted into device,
	 * card entering, unknown position) and stored in the retain bin; applicable
	 * to motor-driven card readers only. The ID card unit sends an event, if
	 * the storage capacity of the retain bin is reached. If the storage
	 * capacity has already been reached, and the command cannot be executed, an
	 * error is returned and the card remains in its present position. If the
	 * execution of this command is performed without errors, the total number
	 * of cards retained includes the current card. If, however, an error occurs
	 * during the execution, the total number of cards retained does not include
	 * the current card.
	 */
	RETAIN_CARD(204L),

	/**
	 * This function resets the present value for number of cards retained to
	 * zero. The function is possible for motor-driven card readers only. The
	 * number of cards retained is controlled by the service and can be
	 * requested before resetting via the WFS_INF_IDC_STATUS.
	 */
	RESET_COUNT(205L),

	/**
	 * This command is used for setting the DES key that is necessary for
	 * operating a CIM86 module. The command must be executed before the first
	 * read command is issued to the card reader.
	 */
	SETKEY(206L),

	/**
	 * For motor driven card readers, the ID card unit checks whether a card has
	 * been inserted. If so, all specified tracks are read immediately. If
	 * reading the chip is requested, the chip will be contacted and reset and
	 * the ATR (Answer To Reset) data will be read. When this command completes
	 * the chip will be in contacted position. This command can also be used for
	 * an explicit reset of a previously contacted chip. If no card has been
	 * inserted, and for all other categories of card readers, the ID card unit
	 * waits for the period of time specified in the WFSExecute call for a card
	 * to be either inserted or pulled through. The next step is trying to read
	 * all tracks specified. Magnetic stripe track data is converted from its 5
	 * or 7 bit character form to 8 bit ASCII form. The parity bit from each 5
	 * or 7 bit magnetic stripe character is discarded. Start and end sentinel
	 * characters are not returned to the application. Field separator
	 * characters are returned to the application, and are also converted to 8
	 * bit ASCII form. In addition to that, a security check via a security
	 * module (i.e., MM, CIM86) can be requested.
	 * 
	 * @see WFSIDCCARDDATA
	 */
	READ_RAW_DATA(207L),

	/**
	 * For motor-driven card readers, the ID card unit checks whether a card has
	 * been inserted. If so, the data is written to the tracks. If no card has
	 * been inserted, and for all other categories of devices, the ID card unit
	 * waits for the period of time specified in the WFSExecute call for a card
	 * to be either inserted or pulled through. The next step is writing the
	 * data to the respective tracks. The application must pass the magnetic
	 * stripe data in ASCII without any sentinels. The data will be converted by
	 * the service provider. This procedure is followed by data verification.
	 * 
	 * @see WFSIDCCARDDATA
	 */
	WRITE_RAW_DATA(208L),

	/**
	 * This command is used to communicate with the chip. Transparent data is
	 * sent from the application to the chip and the response of the chip is
	 * returned transparently to the application. The ATR of the chip must be
	 * obtained before issuing this command by issuing a Read Command.
	 * 
	 * @see WFSIDCCHIPIO
	 */
	CHIP_IO(209L),

	/**
	 * This command is used by the application to perform a hardware reset which
	 * will attempt to return the IDC device to a known good state. This command
	 * does not over-ride a lock obtained by another application or service
	 * handle.
	 * 
	 * The device will attempt to either retain, eject or will perform no action
	 * on any cards found in the IDC as specified in the lpwResetIn parameter.
	 * It may not always be possible to retain or eject the items as specified
	 * because of hardware problems. If a card is found inside the device the
	 * WFS_SRVE_IDC_MEDIADETECTED event will inform the application where card
	 * was actually moved to. If no action is specified the card will not be
	 * moved even if this means that the IDC cannot be recovered.
	 * 
	 * @since 3.00
	 */
	RESET(210L),

	/**
	 * This command handles the power actions that can be done on the chip. This
	 * command is only used after the chip has been contacted for the first time
	 * using the WFS_CMD_IDC_READ_RAW_DATA command.
	 * 
	 * @since 3.00
	 */
	CHIP_POWER(211L),

	/**
	 * This command takes form name and the output of a successful
	 * WFS_CMD_IDC_READ_RAW_DATA command and returns the parsed string.
	 * 
	 * @since 3.00
	 */
	PARSE_DATA(212L),

	/**
	 * @since 3.10
	 */
	SET_GUIDANCE_LIGHT(213L),

	/**
	 * @since 3.10
	 */
	POWER_SAVE_CONTROL(214L);

	private final long value;

	/**
	 * @param value
	 */
	private IDCExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}