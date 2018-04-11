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
import at.o2xfs.xfs.ptr.CharSupport;
import at.o2xfs.xfs.ptr.CodelineFormat;
import at.o2xfs.xfs.ptr.Extents;
import at.o2xfs.xfs.ptr.ImageColorFormat;
import at.o2xfs.xfs.ptr.ImageFormat;
import at.o2xfs.xfs.ptr.ImageSource;
import at.o2xfs.xfs.ptr.MediaControl;
import at.o2xfs.xfs.ptr.PaperSource;
import at.o2xfs.xfs.ptr.ReadForm;
import at.o2xfs.xfs.ptr.Resolution;
import at.o2xfs.xfs.ptr.Type;
import at.o2xfs.xfs.ptr.WriteForm;
import at.o2xfs.xfs.win32.XfsMultiByteMap;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class PtrCapabilities3 extends Struct {

	protected final WORD serviceClass = new WORD();
	protected final XfsWordBitmask<Type> types = new XfsWordBitmask<>(Type.class);
	protected final BOOL compound = new BOOL();
	protected final XfsWordBitmask<Resolution> resolutions = new XfsWordBitmask<>(Resolution.class);
	protected final XfsWordBitmask<ReadForm> readForms = new XfsWordBitmask<>(ReadForm.class);
	protected final XfsWordBitmask<WriteForm> writeForms = new XfsWordBitmask<>(WriteForm.class);
	protected final XfsWordBitmask<Extents> extents = new XfsWordBitmask<>(Extents.class);
	protected final XfsWordBitmask<MediaControl> controls = new XfsWordBitmask<>(MediaControl.class);
	protected final USHORT maxMediaOnStacker = new USHORT();
	protected final BOOL acceptMedia = new BOOL();
	protected final BOOL multiPage = new BOOL();
	protected final XfsWordBitmask<PaperSource> paperSources = new XfsWordBitmask<>(PaperSource.class);
	protected final BOOL mediaTaken = new BOOL();
	protected final USHORT retractBins = new USHORT();
	protected final Pointer maxRetract = new Pointer();
	protected final XfsWordBitmask<ImageFormat> imageTypes = new XfsWordBitmask<>(ImageFormat.class);
	protected final XfsWordBitmask<ImageColorFormat> frontImageColorFormats = new XfsWordBitmask<>(
			ImageColorFormat.class);
	protected final XfsWordBitmask<ImageColorFormat> backImageColorFormats = new XfsWordBitmask<>(
			ImageColorFormat.class);
	protected final XfsWordBitmask<CodelineFormat> codelineFormats = new XfsWordBitmask<>(CodelineFormat.class);
	protected final XfsWordBitmask<ImageSource> imageSources = new XfsWordBitmask<>(ImageSource.class);
	protected final XfsWordBitmask<CharSupport> charSupport = new XfsWordBitmask<>(CharSupport.class);
	protected final BOOL dispensePaper = new BOOL();
	protected final XfsMultiByteMap extra = new XfsMultiByteMap();

	protected PtrCapabilities3() {
		add(serviceClass);
		add(types);
		add(compound);
		add(resolutions);
		add(readForms);
		add(writeForms);
		add(extents);
		add(controls);
		add(maxMediaOnStacker);
		add(acceptMedia);
		add(multiPage);
		add(paperSources);
		add(mediaTaken);
		add(retractBins);
		add(maxRetract);
		add(imageTypes);
		add(frontImageColorFormats);
		add(backImageColorFormats);
		add(codelineFormats);
		add(imageSources);
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
		types.set(copy.getTypes());
		compound.set(copy.isCompound());
		resolutions.set(copy.getResolutions());
		readForms.set(copy.getReadForms());
		writeForms.set(copy.getWriteForms());
		extents.set(copy.getExtents());
		controls.set(copy.getControls());
		maxMediaOnStacker.set(copy.getMaxMediaOnStacker());
		acceptMedia.set(copy.isAcceptMedia());
		multiPage.set(copy.isMultiPage());
		paperSources.set(copy.getPaperSources());
		mediaTaken.set(copy.isMediaTaken());
		retractBins.set(copy.getRetractBins());
		maxRetract.pointTo(new UShortArray(copy.getMaxRetract()));
		imageTypes.set(copy.getImageTypes());
		frontImageColorFormats.set(copy.getFrontImageColorFormats());
		backImageColorFormats.set(copy.getBackImageColorFormats());
		codelineFormats.set(copy.getCodelineFormats());
		imageSources.set(copy.getImageSources());
		charSupport.set(copy.getCharSupport());
		dispensePaper.set(copy.isDispensePaper());
		extra.set(copy.getExtra());
	}

	public int getServiceClass() {
		return serviceClass.get();
	}

	public Set<Type> getTypes() {
		return types.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public Set<Resolution> getResolutions() {
		return resolutions.get();
	}

	public Set<ReadForm> getReadForms() {
		return readForms.get();
	}

	public Set<WriteForm> getWriteForms() {
		return writeForms.get();
	}

	public Set<Extents> getExtents() {
		return extents.get();
	}

	public Set<MediaControl> getControls() {
		return controls.get();
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

	public Set<ImageFormat> getImageTypes() {
		return imageTypes.get();
	}

	public Set<ImageColorFormat> getFrontImageColorFormats() {
		return frontImageColorFormats.get();
	}

	public Set<ImageColorFormat> getBackImageColorFormats() {
		return backImageColorFormats.get();
	}

	public Set<CodelineFormat> getCodelineFormats() {
		return codelineFormats.get();
	}

	public Set<ImageSource> getImageSources() {
		return imageSources.get();
	}

	public Set<CharSupport> getCharSupport() {
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
				.append(getTypes())
				.append(isCompound())
				.append(getResolutions())
				.append(getReadForms())
				.append(getWriteForms())
				.append(getExtents())
				.append(getControls())
				.append(getMaxMediaOnStacker())
				.append(isAcceptMedia())
				.append(isMultiPage())
				.append(getPaperSources())
				.append(isMediaTaken())
				.append(getRetractBins())
				.append(getMaxRetract())
				.append(getImageTypes())
				.append(getFrontImageColorFormats())
				.append(getBackImageColorFormats())
				.append(getCodelineFormats())
				.append(getImageSources())
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
					.append(getTypes(), ptrCapabilities3.getTypes())
					.append(isCompound(), ptrCapabilities3.isCompound())
					.append(getResolutions(), ptrCapabilities3.getResolutions())
					.append(getReadForms(), ptrCapabilities3.getReadForms())
					.append(getWriteForms(), ptrCapabilities3.getWriteForms())
					.append(getExtents(), ptrCapabilities3.getExtents())
					.append(getControls(), ptrCapabilities3.getControls())
					.append(getMaxMediaOnStacker(), ptrCapabilities3.getMaxMediaOnStacker())
					.append(isAcceptMedia(), ptrCapabilities3.isAcceptMedia())
					.append(isMultiPage(), ptrCapabilities3.isMultiPage())
					.append(getPaperSources(), ptrCapabilities3.getPaperSources())
					.append(isMediaTaken(), ptrCapabilities3.isMediaTaken())
					.append(getRetractBins(), ptrCapabilities3.getRetractBins())
					.append(getMaxRetract(), ptrCapabilities3.getMaxRetract())
					.append(getImageTypes(), ptrCapabilities3.getImageTypes())
					.append(getFrontImageColorFormats(), ptrCapabilities3.getFrontImageColorFormats())
					.append(getBackImageColorFormats(), ptrCapabilities3.getBackImageColorFormats())
					.append(getCodelineFormats(), ptrCapabilities3.getCodelineFormats())
					.append(getImageSources(), ptrCapabilities3.getImageSources())
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
				.append("types", getTypes())
				.append("compound", isCompound())
				.append("resolutions", getResolutions())
				.append("readForms", getReadForms())
				.append("writeForms", getWriteForms())
				.append("extents", getExtents())
				.append("controls", getControls())
				.append("maxMediaOnStacker", getMaxMediaOnStacker())
				.append("acceptMedia", isAcceptMedia())
				.append("multiPage", isMultiPage())
				.append("paperSources", getPaperSources())
				.append("mediaTaken", isMediaTaken())
				.append("retractBins", getRetractBins())
				.append("maxRetract", getMaxRetract())
				.append("imageTypes", getImageTypes())
				.append("frontImageColorFormats", getFrontImageColorFormats())
				.append("backImageColorFormats", getBackImageColorFormats())
				.append("codelineFormats", getCodelineFormats())
				.append("imageSources", getImageSources())
				.append("charSupport", getCharSupport())
				.append("dispensePaper", isDispensePaper())
				.append("extra", getExtra())
				.toString();
	}
}
