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

package at.o2xfs.xfs.v3_00.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.PositionStatus;
import at.o2xfs.xfs.cdm.Shutter;
import at.o2xfs.xfs.cdm.Transport;
import at.o2xfs.xfs.cdm.TransportStatus;
import at.o2xfs.xfs.win32.XfsWord;

public class OutputPosition3 extends Struct {

	public static class Builder {

		private Position position = Position.NULL;
		private Shutter shutter = Shutter.CLOSED;
		private PositionStatus positionStatus = PositionStatus.EMPTY;
		private Transport transport = Transport.OK;
		private TransportStatus transportStatus = TransportStatus.EMPTY;

		public Builder position(Position position) {
			this.position = position;
			return this;
		}

		public Builder shutter(Shutter shutter) {
			this.shutter = shutter;
			return this;
		}

		public Builder positionStatus(PositionStatus positionStatus) {
			this.positionStatus = positionStatus;
			return this;
		}

		public Builder transport(Transport transport) {
			this.transport = transport;
			return this;
		}

		public Builder transportStatus(TransportStatus transportStatus) {
			this.transportStatus = transportStatus;
			return this;
		}

		public OutputPosition3 build() {
			return new OutputPosition3(this);
		}
	}

	private final XfsWord<Position> position = new XfsWord<>(Position.class);
	private final XfsWord<Shutter> shutter = new XfsWord<>(Shutter.class);
	private final XfsWord<PositionStatus> positionStatus = new XfsWord<>(PositionStatus.class);
	private final XfsWord<Transport> transport = new XfsWord<>(Transport.class);
	private final XfsWord<TransportStatus> transportStatus = new XfsWord<>(TransportStatus.class);

	protected OutputPosition3() {
		add(position);
		add(shutter);
		add(positionStatus);
		add(transport);
		add(transportStatus);
	}

	protected OutputPosition3(Builder builder) {
		this();
		allocate();
		position.set(builder.position);
		shutter.set(builder.shutter);
		positionStatus.set(builder.positionStatus);
		transport.set(builder.transport);
		transportStatus.set(builder.transportStatus);
	}

	public OutputPosition3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public OutputPosition3(OutputPosition3 copy) {
		this();
		allocate();
		position.set(copy.position);
		shutter.set(copy.shutter);
		positionStatus.set(copy.positionStatus);
		transport.set(copy.transport);
		transportStatus.set(copy.transportStatus);
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
		return new HashCodeBuilder()
				.append(getPosition())
				.append(getShutter())
				.append(getPositionStatus())
				.append(getTransport())
				.append(getTransportStatus())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OutputPosition3) {
			OutputPosition3 rhs = (OutputPosition3) obj;
			return new EqualsBuilder()
					.append(getPosition(), rhs.getPosition())
					.append(getShutter(), rhs.getShutter())
					.append(getPositionStatus(), rhs.getPositionStatus())
					.append(getTransport(), rhs.getTransport())
					.append(getTransportStatus(), rhs.getTransportStatus())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("position", getPosition())
				.append("shutter", getShutter())
				.append("positionStatus", getPositionStatus())
				.append("transport", getTransport())
				.append("transportStatus", getTransportStatus())
				.toString();
	}
}
