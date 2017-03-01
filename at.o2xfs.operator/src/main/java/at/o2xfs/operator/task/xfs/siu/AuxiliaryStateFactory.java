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

package at.o2xfs.operator.task.xfs.siu;

import at.o2xfs.xfs.siu.SIUAudibleAlarmState;
import at.o2xfs.xfs.siu.SIUAuxiliary;
import at.o2xfs.xfs.siu.SIUEnhancedAudioControllerState;
import at.o2xfs.xfs.siu.SIURemoteStatusMonitorState;
import at.o2xfs.xfs.siu.SIUUPSState;
import at.o2xfs.xfs.util.XfsConstants;

class AuxiliaryStateFactory implements StateFactory<SIUAuxiliary> {

	@Override
	public Object createState(final SIUAuxiliary auxiliary, final int portStatus) {
		switch (auxiliary) {
			case VOLUME:
				return Integer.valueOf(portStatus);
			case UPS:
				return XfsConstants.of(portStatus, SIUUPSState.class);
			case REMOTE_STATUS_MONITOR:
				return XfsConstants.of(portStatus,
						SIURemoteStatusMonitorState.class);
			case AUDIBLE_ALARM:
				return XfsConstants.valueOf(portStatus,
						SIUAudibleAlarmState.class);
			case ENHANCEDAUDIOCONTROL:
				return XfsConstants.valueOf(portStatus,
						SIUEnhancedAudioControllerState.class);

		}
		throw new IllegalArgumentException("Illegal SIUAuxiliary: " + auxiliary);
	}

}