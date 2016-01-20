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

public class SIUAuxiliariesStatus {

	private final int[] auxiliaries;

	public SIUAuxiliariesStatus(final int[] auxiliaries) {
		if (auxiliaries == null) {
			throw new IllegalArgumentException("auxiliaries must not be null");
		} else if (auxiliaries.length != SIUConstant.AUXILIARIES_SIZE) {
			throw new IllegalArgumentException("auxiliaries.length != " + SIUConstant.AUXILIARIES_SIZE);
		}
		this.auxiliaries = auxiliaries;
	}

	private <E extends Enum<E> & XfsConstant> E getState(final SIUAuxiliary auxiliary, final Class<E> type) {
		final int value = auxiliaries[(int) auxiliary.getValue()];
		return XfsConstants.valueOf(value, type);
	}

	private <E extends Enum<E> & XfsConstant> Set<E> getStates(final SIUAuxiliary auxiliary, final Class<E> type) {
		final int values = auxiliaries[(int) auxiliary.getValue()];
		return XfsConstants.of(values, type);
	}

	public int getVolumeControlValue() {
		return auxiliaries[(int) SIUAuxiliary.VOLUME.getValue()];
	}

	public Set<SIUUPSState> getUPSStates() {
		return getStates(SIUAuxiliary.UPS, SIUUPSState.class);
	}

	public Set<SIURemoteStatusMonitorState> getRemoteStatusMonitorStates() {
		return getStates(SIUAuxiliary.REMOTE_STATUS_MONITOR, SIURemoteStatusMonitorState.class);
	}

	public SIUAudibleAlarmState getAudibleAlarmState() {
		return getState(SIUAuxiliary.AUDIBLE_ALARM, SIUAudibleAlarmState.class);
	}

	public SIUEnhancedAudioControllerState getEnhancedAudioControllerState() {
		return getState(SIUAuxiliary.ENHANCEDAUDIOCONTROL, SIUEnhancedAudioControllerState.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("volumeControlValue", getVolumeControlValue())
										.append("upsStates", getUPSStates())
										.append("remoteStatusMonitorStates", getRemoteStatusMonitorStates())
										.append("audibleAlarmState", getAudibleAlarmState())
										.append("enhancedAudioControllerState", getEnhancedAudioControllerState())
										.toString();
	}
}