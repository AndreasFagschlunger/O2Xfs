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

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;

public class SIUEnableEvents
		extends Struct {

	public class SIUEnable {

		private static final long NO_CHANGE = 0x0000L;
		private static final long ENABLE_EVENT = 0x0001L;
		private static final long DISABLE_EVENT = 0x0002L;

		private final WORD value;

		private SIUEnable(final WORD value) {
			if (value == null) {
				throw new IllegalArgumentException("value must not be null");
			}
			this.value = value;
		}

		public void noChange() {
			value.set((int) NO_CHANGE);
		}

		public void enableEvent() {
			value.set((int) ENABLE_EVENT);
		}

		public void disableEvent() {
			value.set((int) DISABLE_EVENT);
		}
	}

	private final WORDArray sensors = new WORDArray(SIUConstant.SENSORS_SIZE);
	private final WORDArray doors = new WORDArray(SIUConstant.DOORS_SIZE);
	private final WORDArray indicators = new WORDArray(SIUConstant.INDICATORS_SIZE);
	private final WORDArray auxiliaries = new WORDArray(SIUConstant.AUXILIARIES_SIZE);
	private final WORDArray guidLights = new WORDArray(SIUConstant.GUIDLIGHTS_SIZE);
	private final LPZZSTR extra = new LPZZSTR();

	public SIUEnableEvents() {
		add(sensors);
		add(doors);
		add(indicators);
		add(auxiliaries);
		add(guidLights);
		add(extra);
	}

	public SIUEnable set(final SIUSensor sensor) {
		return new SIUEnable(sensors.get(sensor.intValue()));
	}

	public SIUEnable set(final SIUDoor door) {
		return new SIUEnable(doors.get(door.intValue()));
	}

	public SIUEnable set(final SIUIndicator indicator) {
		return new SIUEnable(indicators.get(indicator.intValue()));
	}

	public SIUEnable set(final SIUAuxiliary auxiliary) {
		return new SIUEnable(auxiliaries.get(auxiliary.intValue()));
	}

	public SIUEnable set(final SIUGuidLight guidLight) {
		return new SIUEnable(guidLights.get(guidLight.intValue()));
	}
}