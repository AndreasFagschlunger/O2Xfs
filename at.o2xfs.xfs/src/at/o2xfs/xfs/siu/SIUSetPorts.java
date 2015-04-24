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

import static at.o2xfs.xfs.siu.SIUConstant.AUXILIARIES_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.DOORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.GUIDLIGHTS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.INDICATORS_SIZE;

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORDArray;

public class SIUSetPorts
		extends Struct {

	private final WORDArray doors = new WORDArray(DOORS_SIZE);
	private final WORDArray indicators = new WORDArray(INDICATORS_SIZE);
	private final WORDArray auxiliaries = new WORDArray(AUXILIARIES_SIZE);
	private final WORDArray guidLights = new WORDArray(GUIDLIGHTS_SIZE);
	private final LPZZSTR extra = new LPZZSTR();

	private SIUSetPorts() {
		add(doors);
		add(indicators);
		add(auxiliaries);
		add(guidLights);
		add(extra);
	}

	public DoorPortsBuilder setDoors() {
		return new DoorPortsBuilder(doors);
	}

	public IndicatorPortsBuilder setIndicators() {
		return new IndicatorPortsBuilder(indicators);
	}

	public AuxiliariesPortsBuilder setAuxiliaries() {
		return new AuxiliariesPortsBuilder(auxiliaries);
	}

	public GuidLightsPortsBuilder setGuidLights() {
		return new GuidLightsPortsBuilder(guidLights);
	}
}