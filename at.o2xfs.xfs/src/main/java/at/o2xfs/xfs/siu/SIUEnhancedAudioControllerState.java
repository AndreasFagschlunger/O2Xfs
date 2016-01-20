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

package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUEnhancedAudioControllerState implements XfsConstant {

	/**
	 * 
	 */
	NOT_AVAILABLE(0x0000L),

	/**
	 * The Enhanced Audio Controller is in manual mode and is in the public
	 * state (i.e. audio will be played through speakers). Activating a Privacy
	 * Device (headset connected/handset off-hook) will have no impact, i.e.
	 * Output will remain through the speakers & no audio will be directed to
	 * the Privacy Device.
	 */
	PUBLICAUDIO_MANUAL(0x0001L),

	/**
	 * The Enhanced Audio Controller is in auto mode and is in the public state
	 * (i.e. audio will be played through speakers). When a Privacy Device is
	 * activated, the device will go to the private state.
	 */
	PUBLICAUDIO_AUTO(0x0002L),

	/**
	 * The Enhanced Audio Controller is in semiauto mode and is in the public
	 * state (i.e. audio will be played through speakers). When a Privacy Device
	 * is activated, the device will go to the private state.
	 */
	PUBLICAUDIO_SEMI_AUTO(0x0004L),

	/**
	 * The Enhanced Audio Controller is in manual mode and is in the private
	 * state (i.e. audio will be played only through a connected Privacy
	 * Device). In private mode, no audio is transmitted through the speakers.
	 */
	PRIVATEAUDIO_MANUAL(0x0008L),

	/**
	 * The Enhanced Audio Controller is in auto mode and is in the private state
	 * (i.e. audio will be played only through a connected Privacy Device). In
	 * private mode, no audio is transmitted through the speakers. When a
	 * Privacy Device is deactivated (headset disconnected/handset on-hook), the
	 * device will go to the public state.
	 */
	PRIVATEAUDIO_AUTO(0x0010L),

	/**
	 * The Enhanced Audio Controller is in semiauto mode and is in the private
	 * state (i.e. audio will be played only through a connected Privacy
	 * Device). In private mode, no audio is transmitted through the speakers.
	 * When a Privacy Device is deactivated, the device will remain in the
	 * private state.
	 */
	PRIVATEAUDIO_SEMI_AUTO(0x0020L);

	private final long value;

	private SIUEnhancedAudioControllerState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}