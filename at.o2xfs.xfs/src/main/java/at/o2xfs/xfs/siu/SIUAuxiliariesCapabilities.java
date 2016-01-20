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
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUAuxiliariesCapabilities {

	private final int[] auxiliaries;

	public SIUAuxiliariesCapabilities(final int[] auxiliaries) {
		this.auxiliaries = auxiliaries;
	}

	private boolean isAvailable(final SIUAuxiliary auxiliary) {
		final int value = auxiliaries[(int) auxiliary.getValue()];
		return value == SIUConstant.AVAILABLE;
	}

	private <E extends Enum<E> & XfsConstant> Set<E> getCapabilities(final SIUAuxiliary auxiliary, final Class<E> type) {
		final int value = auxiliaries[(int) auxiliary.getValue()];
		return XfsConstants.of(value, type);
	}

	public int getVolumeControl() {
		return auxiliaries[(int) SIUAuxiliary.VOLUME.getValue()];
	}

	public boolean isUPSAvailable() {
		final int value = auxiliaries[(int) SIUAuxiliary.UPS.getValue()];
		return (value & SIUConstant.AVAILABLE) == SIUConstant.AVAILABLE;
	}

	public Set<SIUUPSCapability> getUPSCapabilities() {
		return getCapabilities(SIUAuxiliary.UPS, SIUUPSCapability.class);
	}

	public boolean isRemoteStatusMonitor() {
		return isAvailable(SIUAuxiliary.REMOTE_STATUS_MONITOR);
	}

	public boolean isAudibleAlarmDevice() {
		return isAvailable(SIUAuxiliary.AUDIBLE_ALARM);
	}

	public Set<SIUEnhancedAudioControl> getEnhancedAudioControl() {
		return getCapabilities(SIUAuxiliary.ENHANCEDAUDIOCONTROL, SIUEnhancedAudioControl.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("volumeControl", getVolumeControl())
										.append("upsAvailable", isUPSAvailable())
										.append("upsCapabilities", getUPSCapabilities())
										.append("remoteStatusMonitor", isRemoteStatusMonitor())
										.append("audibleAlarmDevice", isAudibleAlarmDevice())
										.append("enhancedAudioControl", getEnhancedAudioControl())
										.toString();
	}
}