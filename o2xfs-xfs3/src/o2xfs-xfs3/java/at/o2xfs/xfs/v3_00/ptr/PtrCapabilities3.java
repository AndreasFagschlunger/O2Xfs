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
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.UShortArray;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.ptr.BackImageColorFormat;
import at.o2xfs.xfs.ptr.BackImageFormat;
import at.o2xfs.xfs.ptr.CharSupport;
import at.o2xfs.xfs.ptr.CodelineFormat;
import at.o2xfs.xfs.ptr.MediaControl;
import at.o2xfs.xfs.ptr.Extents;
import at.o2xfs.xfs.ptr.ImageSource;
import at.o2xfs.xfs.ptr.PaperSource;
import at.o2xfs.xfs.ptr.ReadForm;
import at.o2xfs.xfs.ptr.Resolution;
import at.o2xfs.xfs.ptr.Type;
import at.o2xfs.xfs.ptr.WriteForm;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class PtrCapabilities3 extends Struct {

	protected final WORD serviceClass = new WORD();
	protected final XfsWord<Type> type = new XfsWord<>(Type.class);
	protected final BOOL compound = new BOOL();
	protected final XfsWord<Resolution> resolution = new XfsWord<>(Resolution.class);
	protected final XfsWord<ReadForm> readForm = new XfsWord<>(ReadForm.class);
	protected final XfsWord<WriteForm> writeForm = new XfsWord<>(WriteForm.class);
	protected final XfsWordBitmask<Extents> extents = new XfsWordBitmask<>(Extents.class);
	protected final XfsWord<MediaControl> control = new XfsWord<>(MediaControl.class);
	protected final USHORT maxMediaOnStacker = new USHORT();
	protected final BOOL acceptMedia = new BOOL();
	protected final BOOL multiPage = new BOOL();
	protected final XfsWordBitmask<PaperSource> paperSources = new XfsWordBitmask<>(PaperSource.class);
	protected final BOOL mediaTaken = new BOOL();
	protected final USHORT retractBins = new USHORT();
	protected final Pointer maxRetract = new Pointer();
	protected final XfsWord<BackImageFormat> imageType = new XfsWord<>(BackImageFormat.class);
	protected final XfsWord<BackImageColorFormat> frontImageColorFormat = new XfsWord<>(BackImageColorFormat.class);
	protected final XfsWord<BackImageColorFormat> backImageColorFormat = new XfsWord<>(BackImageColorFormat.class);
	protected final XfsWord<CodelineFormat> codelineFormat = new XfsWord<>(CodelineFormat.class);
	protected final XfsWord<ImageSource> imageSource = new XfsWord<>(ImageSource.class);
	protected final XfsWord<CharSupport> charSupport = new XfsWord<>(CharSupport.class);
	protected final BOOL dispensePaper = new BOOL();
	protected final XfsExtra extra = new XfsExtra();

	protected PtrCapabilities3() {
		add(serviceClass);
		add(type);
		add(compound);
		add(resolution);
		add(readForm);
		add(writeForm);
		add(extents);
		add(control);
		add(maxMediaOnStacker);
		add(acceptMedia);
		add(multiPage);
		add(paperSources);
		add(mediaTaken);
		add(retractBins);
		add(maxRetract);
		add(imageType);
		add(frontImageColorFormat);
		add(backImageColorFormat);
		add(codelineFormat);
		add(imageSource);
		add(charSupport);
		add(dispensePaper);
		add(extra);
	}

	public PtrCapabilities3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PtrCapabilities3(PtrCapabilities3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PtrCapabilities3 copy) {
		serviceClass.set(copy.getServiceClass());
		type.set(copy.getType());
		compound.set(copy.isCompound());
		resolution.set(copy.getResolution());
		readForm.set(copy.getReadForm());
		writeForm.set(copy.getWriteForm());
		extents.set(copy.getExtents());
		control.set(copy.getControl());
		maxMediaOnStacker.set(copy.getMaxMediaOnStacker());
		acceptMedia.set(copy.isAcceptMedia());
		multiPage.set(copy.isMultiPage());
		paperSources.set(copy.getPaperSources());
		mediaTaken.set(copy.isMediaTaken());
		retractBins.set(copy.getRetractBins());
		maxRetract.pointTo(new UShortArray(copy.getMaxRetract()));
		imageType.set(copy.getImageType());
		frontImageColorFormat.set(copy.getFrontImageColorFormat());
		backImageColorFormat.set(copy.getBackImageColorFormat());
		codelineFormat.set(copy.getCodelineFormat());
		imageSource.set(copy.getImageSource());
		charSupport.set(copy.getCharSupport());
		dispensePaper.set(copy.isDispensePaper());
		extra.set(copy.getExtra());
	}

	public int getServiceClass() {
		return serviceClass.get();
	}

	public Type getType() {
		return type.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public Resolution getResolution() {
		return resolution.get();
	}

	public ReadForm getReadForm() {
		return readForm.get();
	}

	public WriteForm getWriteForm() {
		return writeForm.get();
	}

	public Set<Extents> getExtents() {
		return extents.get();
	}

	public MediaControl getControl() {
		return control.get();
	}

	public int getMaxMediaOnStacker() {
		return maxMediaOnStacker.get();
	}

	public boolean isAcceptMedia() {
		return acceptMedia.get();
	}

	public boolean isMultiPage() {
		return multiPage.get();
	}

	public Set<PaperSource> getPaperSources() {
		return paperSources.get();
	}

	public boolean isMediaTaken() {
		return mediaTaken.get();
	}

	public int getRetractBins() {
		return retractBins.get();
	}

	public int[] getMaxRetract() {
		return new UShortArray(maxRetract, getRetractBins()).get();
	}

	public BackImageFormat getImageType() {
		return imageType.get();
	}

	public BackImageColorFormat getFrontImageColorFormat() {
		return frontImageColorFormat.get();
	}

	public BackImageColorFormat getBackImageColorFormat() {
		return backImageColorFormat.get();
	}

	public CodelineFormat getCodelineFormat() {
		return codelineFormat.get();
	}

	public ImageSource getImageSource() {
		return imageSource.get();
	}

	public CharSupport getCharSupport() {
		return charSupport.get();
	}

	public boolean isDispensePaper() {
		return dispensePaper.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getServiceClass())
				.append(getType())
				.append(isCompound())
				.append(getResolution())
				.append(getReadForm())
				.append(getWriteForm())
				.append(getExtents())
				.append(getControl())
				.append(getMaxMediaOnStacker())
				.append(isAcceptMedia())
				.append(isMultiPage())
				.append(getPaperSources())
				.append(isMediaTaken())
				.append(getRetractBins())
				.append(getMaxRetract())
				.append(getImageType())
				.append(getFrontImageColorFormat())
				.append(getBackImageColorFormat())
				.append(getCodelineFormat())
				.append(getImageSource())
				.append(getCharSupport())
				.append(isDispensePaper())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtrCapabilities3) {
			PtrCapabilities3 ptrCapabilities3 = (PtrCapabilities3) obj;
			return new EqualsBuilder()
					.append(getServiceClass(), ptrCapabilities3.getServiceClass())
					.append(getType(), ptrCapabilities3.getType())
					.append(isCompound(), ptrCapabilities3.isCompound())
					.append(getResolution(), ptrCapabilities3.getResolution())
					.append(getReadForm(), ptrCapabilities3.getReadForm())
					.append(getWriteForm(), ptrCapabilities3.getWriteForm())
					.append(getExtents(), ptrCapabilities3.getExtents())
					.append(getControl(), ptrCapabilities3.getControl())
					.append(getMaxMediaOnStacker(), ptrCapabilities3.getMaxMediaOnStacker())
					.append(isAcceptMedia(), ptrCapabilities3.isAcceptMedia())
					.append(isMultiPage(), ptrCapabilities3.isMultiPage())
					.append(getPaperSources(), ptrCapabilities3.getPaperSources())
					.append(isMediaTaken(), ptrCapabilities3.isMediaTaken())
					.append(getRetractBins(), ptrCapabilities3.getRetractBins())
					.append(getMaxRetract(), ptrCapabilities3.getMaxRetract())
					.append(getImageType(), ptrCapabilities3.getImageType())
					.append(getFrontImageColorFormat(), ptrCapabilities3.getFrontImageColorFormat())
					.append(getBackImageColorFormat(), ptrCapabilities3.getBackImageColorFormat())
					.append(getCodelineFormat(), ptrCapabilities3.getCodelineFormat())
					.append(getImageSource(), ptrCapabilities3.getImageSource())
					.append(getCharSupport(), ptrCapabilities3.getCharSupport())
					.append(isDispensePaper(), ptrCapabilities3.isDispensePaper())
					.append(getExtra(), ptrCapabilities3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("serviceClass", getServiceClass())
				.append("type", getType())
				.append("compound", isCompound())
				.append("resolution", getResolution())
				.append("readForm", getReadForm())
				.append("writeForm", getWriteForm())
				.append("extents", getExtents())
				.append("control", getControl())
				.append("maxMediaOnStacker", getMaxMediaOnStacker())
				.append("acceptMedia", isAcceptMedia())
				.append("multiPage", isMultiPage())
				.append("paperSources", getPaperSources())
				.append("mediaTaken", isMediaTaken())
				.append("retractBins", getRetractBins())
				.append("maxRetract", getMaxRetract())
				.append("imageType", getImageType())
				.append("frontImageColorFormat", getFrontImageColorFormat())
				.append("backImageColorFormat", getBackImageColorFormat())
				.append("codelineFormat", getCodelineFormat())
				.append("imageSource", getImageSource())
				.append("charSupport", getCharSupport())
				.append("dispensePaper", isDispensePaper())
				.append("extra", getExtra())
				.toString();
	}
}
