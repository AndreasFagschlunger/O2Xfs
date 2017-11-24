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

package at.o2xfs.xfs.v3_30.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.Shutter;
import at.o2xfs.xfs.win32.XfsWord;

public class ShutterStatusChanged330 extends Struct {

	protected final XfsWord<Position> position = new XfsWord<>(Position.class);
	protected final XfsWord<Shutter> shutter = new XfsWord<>(Shutter.class);

	protected ShutterStatusChanged330() {
		add(position);
		add(shutter);
	}

	public ShutterStatusChanged330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ShutterStatusChanged330(ShutterStatusChanged330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ShutterStatusChanged330 copy) {
		position.set(copy.getPosition());
		shutter.set(copy.getShutter());
	}

	public Position getPosition() {
		return position.get();
	}

	public Shutter getShutter() {
		return shutter.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPosition()).append(getShutter()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ShutterStatusChanged330) {
			ShutterStatusChanged330 shutterStatusChanged = (ShutterStatusChanged330) obj;
			return new EqualsBuilder().append(getPosition(), shutterStatusChanged.getPosition()).append(getShutter(), shutterStatusChanged.getShutter()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("position", getPosition()).append("shutter", getShutter()).toString();
	}
}
