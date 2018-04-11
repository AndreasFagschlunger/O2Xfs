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

package at.o2xfs.xfs.v3_00.ptr;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.ptr.Ink;
import at.o2xfs.xfs.ptr.Lamp;
import at.o2xfs.xfs.ptr.MediaPosition;
import at.o2xfs.xfs.ptr.PaperStatus;
import at.o2xfs.xfs.ptr.PtrConstant;
import at.o2xfs.xfs.ptr.PtrDeviceState;
import at.o2xfs.xfs.ptr.Toner;
import at.o2xfs.xfs.win32.XfsMultiByteMap;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordArray;

public class PtrStatus3 extends Struct {

	protected final XfsWord<PtrDeviceState> device = new XfsWord<>(PtrDeviceState.class);
	protected final XfsWord<MediaPosition> media = new XfsWord<>(MediaPosition.class);
	protected final XfsWordArray<PaperStatus> paper = new XfsWordArray<>(PaperStatus.class, PtrConstant.SUPPLYSIZE);
	protected final XfsWord<Toner> toner = new XfsWord<>(Toner.class);
	protected final XfsWord<Ink> ink = new XfsWord<>(Ink.class);
	protected final XfsWord<Lamp> lamp = new XfsWord<>(Lamp.class);
	protected final Pointer retractBins = new Pointer();
	protected final USHORT mediaOnStacker = new USHORT();
	protected final XfsMultiByteMap extra = new XfsMultiByteMap();

	protected PtrStatus3() {
		add(device);
		add(media);
		add(paper);
		add(toner);
		add(ink);
		add(lamp);
		add(retractBins);
		add(mediaOnStacker);
		add(extra);
	}

	public PtrStatus3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PtrStatus3(PtrStatus3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PtrStatus3 copy) {
		device.set(copy.getDevice());
		media.set(copy.getMedia());
		paper.set(copy.getPaper());
		toner.set(copy.getToner());
		ink.set(copy.getInk());
		lamp.set(copy.getLamp());
		Optional<RetractBin3[]> retractBins = copy.getRetractBins();
		if (retractBins.isPresent()) {
			this.retractBins.pointTo(new RetractBins3(retractBins.get()));
		}
		mediaOnStacker.set(copy.getMediaOnStacker());
		extra.set(copy.getExtra());
	}

	public PtrDeviceState getDevice() {
		return device.get();
	}

	public MediaPosition getMedia() {
		return media.get();
	}

	public PaperStatus[] getPaper() {
		return paper.get();
	}

	public Toner getToner() {
		return toner.get();
	}

	public Ink getInk() {
		return ink.get();
	}

	public Lamp getLamp() {
		return lamp.get();
	}

	public Optional<RetractBin3[]> getRetractBins() {
		Optional<RetractBin3[]> result = Optional.empty();
		if (!Pointer.NULL.equals(retractBins)) {
			result = Optional.of(new RetractBins3(retractBins).get());
		}
		return result;
	}

	public int getMediaOnStacker() {
		return mediaOnStacker.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getDevice())
				.append(getMedia())
				.append(getPaper())
				.append(getToner())
				.append(getInk())
				.append(getLamp())
				.append(getRetractBins().orElse(null))
				.append(getMediaOnStacker())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtrStatus3) {
			PtrStatus3 ptrStatus3 = (PtrStatus3) obj;
			return new EqualsBuilder()
					.append(getDevice(), ptrStatus3.getDevice())
					.append(getMedia(), ptrStatus3.getMedia())
					.append(getPaper(), ptrStatus3.getPaper())
					.append(getToner(), ptrStatus3.getToner())
					.append(getInk(), ptrStatus3.getInk())
					.append(getLamp(), ptrStatus3.getLamp())
					.append(getRetractBins().orElse(null), ptrStatus3.getRetractBins().orElse(null))
					.append(getMediaOnStacker(), ptrStatus3.getMediaOnStacker())
					.append(getExtra(), ptrStatus3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("device", getDevice())
				.append("media", getMedia())
				.append("paper", getPaper())
				.append("toner", getToner())
				.append("ink", getInk())
				.append("lamp", getLamp())
				.append("retractBins", getRetractBins())
				.append("mediaOnStacker", getMediaOnStacker())
				.append("extra", getExtra())
				.toString();
	}
}
