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

package at.o2xfs.xfs.v3_10.cim;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.cim.AdditionalBunches;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.win32.OptionalXfsWord;
import at.o2xfs.xfs.win32.XfsWord;

public class PositionInfo310 extends XfsStruct {

	public static class Builder {

		private final Position position;
		private Optional<AdditionalBunches> additionalBunches;
		private int bunchesRemaining;

		public Builder(Position position) {
			this.position = position;
			additionalBunches = Optional.empty();
		}

		public Builder additionalBunches(AdditionalBunches additionalBunches) {
			this.additionalBunches = Optional.ofNullable(additionalBunches);
			return this;
		}

		public Builder bunchesRemaining(int bunchesRemaining) {
			this.bunchesRemaining = bunchesRemaining;
			return this;
		}

		public PositionInfo310 build() {
			return new PositionInfo310(this);
		}
	}

	protected final XfsWord<Position> position = new XfsWord<>(Position.class);
	protected final OptionalXfsWord<AdditionalBunches> additionalBunches = new OptionalXfsWord<>(
			AdditionalBunches.class);
	protected final USHORT bunchesRemaining = new USHORT();

	protected PositionInfo310() {
		add(position);
		add(additionalBunches);
		add(bunchesRemaining);
	}

	public PositionInfo310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PositionInfo310(PositionInfo310 copy) {
		this();
		allocate();
		set(copy);
	}

	private PositionInfo310(Builder builder) {
		this();
		allocate();
		position.set(builder.position);
		additionalBunches.set(builder.additionalBunches);
		bunchesRemaining.set(builder.bunchesRemaining);
	}

	protected void set(PositionInfo310 copy) {
		position.set(copy.getPosition());
		additionalBunches.set(copy.getAdditionalBunches());
		bunchesRemaining.set(copy.getBunchesRemaining());
	}

	public Position getPosition() {
		return position.get();
	}

	public Optional<AdditionalBunches> getAdditionalBunches() {
		return additionalBunches.get();
	}

	public int getBunchesRemaining() {
		return bunchesRemaining.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPosition())
				.append(getAdditionalBunches())
				.append(getBunchesRemaining())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PositionInfo310) {
			PositionInfo310 positionInfo310 = (PositionInfo310) obj;
			return new EqualsBuilder()
					.append(getPosition(), positionInfo310.getPosition())
					.append(getAdditionalBunches(), positionInfo310.getAdditionalBunches())
					.append(getBunchesRemaining(), positionInfo310.getBunchesRemaining())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("position", getPosition())
				.append("additionalBunches", getAdditionalBunches())
				.append("bunchesRemaining", getBunchesRemaining())
				.toString();
	}
}
