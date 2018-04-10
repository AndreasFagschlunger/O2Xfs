/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.v3_30.ptr;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.ptr.MediaControl;
import at.o2xfs.xfs.ptr.PtrExecuteCommand;
import at.o2xfs.xfs.v3_20.ptr.PtrCapabilities320;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class PtrCapabilities330 extends PtrCapabilities320 {

	protected final XfsDWordBitmask<MediaControl> controlEx = new XfsDWordBitmask<>(MediaControl.class);
	protected final BOOL blackMarkModeSupported = new BOOL();
	protected final Pointer synchronizableCommands = new Pointer();

	protected PtrCapabilities330() {
		add(controlEx);
		add(blackMarkModeSupported);
		add(synchronizableCommands);
	}

	public PtrCapabilities330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PtrCapabilities330(PtrCapabilities330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PtrCapabilities330 copy) {
		super.set(copy);
		controlEx.set(copy.getControlEx());
		blackMarkModeSupported.set(copy.isBlackMarkModeSupported());
		Optional<PtrExecuteCommand[]> synchronizableCommands = copy.getSynchronizableCommands();
		if (synchronizableCommands.isPresent()) {
			this.synchronizableCommands.pointTo(new SynchronizableCommands(synchronizableCommands.get()));
		}
	}

	public Set<MediaControl> getControlEx() {
		return controlEx.get();
	}

	public boolean isBlackMarkModeSupported() {
		return blackMarkModeSupported.get();
	}

	public Optional<PtrExecuteCommand[]> getSynchronizableCommands() {
		Optional<PtrExecuteCommand[]> result = Optional.empty();
		if (!Pointer.NULL.equals(synchronizableCommands)) {
			result = Optional.of(new SynchronizableCommands(synchronizableCommands).get());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getControlEx())
				.append(isBlackMarkModeSupported())
				.append(getSynchronizableCommands().orElse(null))
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtrCapabilities330) {
			PtrCapabilities330 ptrCapabilities330 = (PtrCapabilities330) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getControlEx(), ptrCapabilities330.getControlEx())
					.append(isBlackMarkModeSupported(), ptrCapabilities330.isBlackMarkModeSupported())
					.append(getSynchronizableCommands().orElse(null),
							ptrCapabilities330.getSynchronizableCommands().orElse(null))
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("controlEx", getControlEx())
				.append("blackMarkModeSupported", isBlackMarkModeSupported())
				.append("synchronizableCommands", getSynchronizableCommands())
				.toString();
	}
}
