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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUIndicatorsStatus {

	private final int[] indicators;

	public SIUIndicatorsStatus(final int[] indicators) {
		if (indicators == null) {
			throw new IllegalArgumentException("indicators must not be null");
		} else if (indicators.length != SIUConstant.INDICATORS_SIZE) {
			throw new IllegalArgumentException("indicators.length != " + SIUConstant.INDICATORS_SIZE);
		}
		this.indicators = indicators;
	}

	private <E extends Enum<E> & XfsConstant> E getState(final SIUIndicator indicator, final Class<E> type) {
		final int value = indicators[(int) indicator.getValue()];
		return XfsConstants.valueOf(value, type);
	}

	private <E extends Enum<E> & XfsConstant> Set<E> getStates(final SIUIndicator indicator, final Class<E> type) {
		final int value = indicators[(int) indicator.getValue()];
		return XfsConstants.of(value, type);
	}

	public SIUOpenClosedIndicatorState getOpenClosedIndicatorState() {
		return getState(SIUIndicator.OPENCLOSE, SIUOpenClosedIndicatorState.class);
	}

	public SIUFasciaLightState getFasciaLightState() {
		return getState(SIUIndicator.FASCIALIGHT, SIUFasciaLightState.class);
	}

	public Set<SIUAudioIndicatorState> getAudioIndicatorStates() {
		return getStates(SIUIndicator.AUDIO, SIUAudioIndicatorState.class);
	}

	public SIUInternalHeatingState getInternalHeatingState() {
		return getState(SIUIndicator.HEATING, SIUInternalHeatingState.class);
	}

	public SIUConsumerDisplayBacklightState getConsumerDisplayBacklightState() {
		return getState(SIUIndicator.CONSUMER_DISPLAY_BACKLIGHT, SIUConsumerDisplayBacklightState.class);
	}

	public SIUSignageDisplayState getSignageDisplayState() {
		return getState(SIUIndicator.SIGNAGEDISPLAY, SIUSignageDisplayState.class);
	}

	public Map<SIULamp, Boolean> getTransactionIndicatorsStates() {
		final Map<SIULamp, Boolean> states = new HashMap<SIULamp, Boolean>();
		final int value = indicators[(int) SIUIndicator.TRANSINDICATOR.getValue()];
		for (final SIULamp lamp : SIULamp.values()) {
			boolean turnedOn = false;
			if ((value & lamp.getValue()) == lamp.getValue()) {
				turnedOn = true;
			}
			states.put(lamp, Boolean.valueOf(turnedOn));
		}
		return states;
	}

	public Map<SIUGeneralPurposeOutputPort, Boolean> getGeneralPurposeOutputPortsStates() {
		final Map<SIUGeneralPurposeOutputPort, Boolean> portsStates = new HashMap<SIUGeneralPurposeOutputPort, Boolean>();
		final int value = indicators[(int) SIUIndicator.GENERALOUTPUTPORT.getValue()];
		for (final SIUGeneralPurposeOutputPort port : SIUGeneralPurposeOutputPort.values()) {
			boolean turnedOn = false;
			if ((value & port.getValue()) == port.getValue()) {
				turnedOn = true;
			}
			portsStates.put(port, Boolean.valueOf(turnedOn));
		}
		return portsStates;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("openClosedIndicatorState", getOpenClosedIndicatorState())
										.append("fasciaLightState", getFasciaLightState())
										.append("audioIndicatorStates", getAudioIndicatorStates())
										.append("internalHeatingState", getInternalHeatingState())
										.append("consumerDisplayBacklightState", getConsumerDisplayBacklightState())
										.append("signageDisplayState", getSignageDisplayState())
										.append("transactionIndicatorsStates", getTransactionIndicatorsStates())
										.append("generalPurposeOutputPortsStates", getGeneralPurposeOutputPortsStates())
										.toString();
	}
}