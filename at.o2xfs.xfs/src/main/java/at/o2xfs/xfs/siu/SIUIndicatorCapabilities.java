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

public class SIUIndicatorCapabilities {

	private final int[] indicators;

	public SIUIndicatorCapabilities(final int[] indicators) {
		this.indicators = indicators;
	}

	private boolean isAvailable(final SIUIndicator indicator) {
		final int value = indicators[(int) indicator.getValue()];
		return value == SIUConstant.AVAILABLE;
	}

	private <E extends Enum<E> & XfsConstant> Set<E> fromBitmap(final SIUIndicator indicator, final Class<E> type) {
		final int value = indicators[(int) indicator.getValue()];
		return XfsConstants.of(value, type);
	}

	public boolean isOpenClosedIndicator() {
		return isAvailable(SIUIndicator.OPENCLOSE);
	}

	public boolean isFasciaLight() {
		return isAvailable(SIUIndicator.FASCIALIGHT);
	}

	public boolean isAudioIndicator() {
		return isAvailable(SIUIndicator.AUDIO);
	}

	public boolean isHeatingDevice() {
		return isAvailable(SIUIndicator.HEATING);
	}

	public boolean isConsumerDisplayBacklight() {
		return isAvailable(SIUIndicator.CONSUMER_DISPLAY_BACKLIGHT);
	}

	public boolean isSignageDisplay() {
		return isAvailable(SIUIndicator.SIGNAGEDISPLAY);
	}

	public Set<SIULamp> getTransactionIndicators() {
		return fromBitmap(SIUIndicator.TRANSINDICATOR, SIULamp.class);
	}

	public Set<SIUGeneralPurposeOutputPort> getGeneralPurposeOutputPorts() {
		return fromBitmap(SIUIndicator.GENERALOUTPUTPORT, SIUGeneralPurposeOutputPort.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("openClosedIndicator", isOpenClosedIndicator())
										.append("fasciaLight", isFasciaLight())
										.append("audioIndicator", isAudioIndicator())
										.append("heatingDevice", isHeatingDevice())
										.append("consumerDisplayBacklight", isConsumerDisplayBacklight())
										.append("signageDisplay", isSignageDisplay())
										.append("transactionIndicators", getTransactionIndicators())
										.append("generalPurposeOutputPorts", getGeneralPurposeOutputPorts())
										.toString();
	}

}