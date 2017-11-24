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

package at.o2xfs.xfs.v3_00.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.cim.PositionStatus;
import at.o2xfs.xfs.cim.Shutter;
import at.o2xfs.xfs.cim.Transport;
import at.o2xfs.xfs.cim.TransportStatus;
import at.o2xfs.xfs.win32.XfsWord;

public class Inpos3 extends Struct {

	protected final XfsWord<Position> position = new XfsWord<>(Position.class);
	protected final XfsWord<Shutter> shutter = new XfsWord<>(Shutter.class);
	protected final XfsWord<PositionStatus> positionStatus = new XfsWord<>(PositionStatus.class);
	protected final XfsWord<Transport> transport = new XfsWord<>(Transport.class);
	protected final XfsWord<TransportStatus> transportStatus = new XfsWord<>(TransportStatus.class);

	protected Inpos3() {
		add(position);
		add(shutter);
		add(positionStatus);
		add(transport);
		add(transportStatus);
	}

	public Inpos3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Inpos3(Inpos3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Inpos3 copy) {
		position.set(copy.getPosition());
		shutter.set(copy.getShutter());
		positionStatus.set(copy.getPositionStatus());
		transport.set(copy.getTransport());
		transportStatus.set(copy.getTransportStatus());
	}

	public Position getPosition() {
		return position.get();
	}

	public Shutter getShutter() {
		return shutter.get();
	}

	public PositionStatus getPositionStatus() {
		return positionStatus.get();
	}

	public Transport getTransport() {
		return transport.get();
	}

	public TransportStatus getTransportStatus() {
		return transportStatus.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPosition()).append(getShutter()).append(getPositionStatus()).append(getTransport()).append(getTransportStatus()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Inpos3) {
			Inpos3 inpos3 = (Inpos3) obj;
			return new EqualsBuilder().append(getPosition(), inpos3.getPosition()).append(getShutter(), inpos3.getShutter()).append(getPositionStatus(), inpos3.getPositionStatus())
					.append(getTransport(), inpos3.getTransport()).append(getTransportStatus(), inpos3.getTransportStatus()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("position", getPosition()).append("shutter", getShutter()).append("positionStatus", getPositionStatus())
				.append("transport", getTransport()).append("transportStatus", getTransportStatus()).toString();
	}
}
