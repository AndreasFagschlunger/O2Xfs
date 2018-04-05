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

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.ptr.FoldType;
import at.o2xfs.xfs.ptr.FormBaseUnit;
import at.o2xfs.xfs.ptr.MediaType;
import at.o2xfs.xfs.ptr.PaperSource;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class FormMedia3 extends Struct {

	protected final XfsWord<MediaType> mediaType = new XfsWord<>(MediaType.class);
	protected final XfsWord<FormBaseUnit> base = new XfsWord<>(FormBaseUnit.class);
	protected final WORD unitX = new WORD();
	protected final WORD unitY = new WORD();
	protected final WORD sizeWidth = new WORD();
	protected final WORD sizeHeight = new WORD();
	protected final WORD pageCount = new WORD();
	protected final WORD lineCount = new WORD();
	protected final WORD printAreaX = new WORD();
	protected final WORD printAreaY = new WORD();
	protected final WORD printAreaWidth = new WORD();
	protected final WORD printAreaHeight = new WORD();
	protected final WORD restrictedAreaX = new WORD();
	protected final WORD restrictedAreaY = new WORD();
	protected final WORD restrictedAreaWidth = new WORD();
	protected final WORD restrictedAreaHeight = new WORD();
	protected final WORD stagger = new WORD();
	protected final XfsWord<FoldType> foldType = new XfsWord<>(FoldType.class);
	protected final XfsWordBitmask<PaperSource> paperSources = new XfsWordBitmask<>(PaperSource.class);

	protected FormMedia3() {
		add(mediaType);
		add(base);
		add(unitX);
		add(unitY);
		add(sizeWidth);
		add(sizeHeight);
		add(pageCount);
		add(lineCount);
		add(printAreaX);
		add(printAreaY);
		add(printAreaWidth);
		add(printAreaHeight);
		add(restrictedAreaX);
		add(restrictedAreaY);
		add(restrictedAreaWidth);
		add(restrictedAreaHeight);
		add(stagger);
		add(foldType);
		add(paperSources);
	}

	public FormMedia3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public FormMedia3(FormMedia3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(FormMedia3 copy) {
		mediaType.set(copy.getMediaType());
		base.set(copy.getBase());
		unitX.set(copy.getUnitX());
		unitY.set(copy.getUnitY());
		sizeWidth.set(copy.getSizeWidth());
		sizeHeight.set(copy.getSizeHeight());
		pageCount.set(copy.getPageCount());
		lineCount.set(copy.getLineCount());
		printAreaX.set(copy.getPrintAreaX());
		printAreaY.set(copy.getPrintAreaY());
		printAreaWidth.set(copy.getPrintAreaWidth());
		printAreaHeight.set(copy.getPrintAreaHeight());
		restrictedAreaX.set(copy.getRestrictedAreaX());
		restrictedAreaY.set(copy.getRestrictedAreaY());
		restrictedAreaWidth.set(copy.getRestrictedAreaWidth());
		restrictedAreaHeight.set(copy.getRestrictedAreaHeight());
		stagger.set(copy.getStagger());
		foldType.set(copy.getFoldType());
		paperSources.set(copy.getPaperSources());
	}

	public MediaType getMediaType() {
		return mediaType.get();
	}

	public FormBaseUnit getBase() {
		return base.get();
	}

	public int getUnitX() {
		return unitX.get();
	}

	public int getUnitY() {
		return unitY.get();
	}

	public int getSizeWidth() {
		return sizeWidth.get();
	}

	public int getSizeHeight() {
		return sizeHeight.get();
	}

	public int getPageCount() {
		return pageCount.get();
	}

	public int getLineCount() {
		return lineCount.get();
	}

	public int getPrintAreaX() {
		return printAreaX.get();
	}

	public int getPrintAreaY() {
		return printAreaY.get();
	}

	public int getPrintAreaWidth() {
		return printAreaWidth.get();
	}

	public int getPrintAreaHeight() {
		return printAreaHeight.get();
	}

	public int getRestrictedAreaX() {
		return restrictedAreaX.get();
	}

	public int getRestrictedAreaY() {
		return restrictedAreaY.get();
	}

	public int getRestrictedAreaWidth() {
		return restrictedAreaWidth.get();
	}

	public int getRestrictedAreaHeight() {
		return restrictedAreaHeight.get();
	}

	public int getStagger() {
		return stagger.get();
	}

	public FoldType getFoldType() {
		return foldType.get();
	}

	public Set<PaperSource> getPaperSources() {
		return paperSources.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getMediaType())
				.append(getBase())
				.append(getUnitX())
				.append(getUnitY())
				.append(getSizeWidth())
				.append(getSizeHeight())
				.append(getPageCount())
				.append(getLineCount())
				.append(getPrintAreaX())
				.append(getPrintAreaY())
				.append(getPrintAreaWidth())
				.append(getPrintAreaHeight())
				.append(getRestrictedAreaX())
				.append(getRestrictedAreaY())
				.append(getRestrictedAreaWidth())
				.append(getRestrictedAreaHeight())
				.append(getStagger())
				.append(getFoldType())
				.append(getPaperSources())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormMedia3) {
			FormMedia3 formMedia3 = (FormMedia3) obj;
			return new EqualsBuilder()
					.append(getMediaType(), formMedia3.getMediaType())
					.append(getBase(), formMedia3.getBase())
					.append(getUnitX(), formMedia3.getUnitX())
					.append(getUnitY(), formMedia3.getUnitY())
					.append(getSizeWidth(), formMedia3.getSizeWidth())
					.append(getSizeHeight(), formMedia3.getSizeHeight())
					.append(getPageCount(), formMedia3.getPageCount())
					.append(getLineCount(), formMedia3.getLineCount())
					.append(getPrintAreaX(), formMedia3.getPrintAreaX())
					.append(getPrintAreaY(), formMedia3.getPrintAreaY())
					.append(getPrintAreaWidth(), formMedia3.getPrintAreaWidth())
					.append(getPrintAreaHeight(), formMedia3.getPrintAreaHeight())
					.append(getRestrictedAreaX(), formMedia3.getRestrictedAreaX())
					.append(getRestrictedAreaY(), formMedia3.getRestrictedAreaY())
					.append(getRestrictedAreaWidth(), formMedia3.getRestrictedAreaWidth())
					.append(getRestrictedAreaHeight(), formMedia3.getRestrictedAreaHeight())
					.append(getStagger(), formMedia3.getStagger())
					.append(getFoldType(), formMedia3.getFoldType())
					.append(getPaperSources(), formMedia3.getPaperSources())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("mediaType", getMediaType())
				.append("base", getBase())
				.append("unitX", getUnitX())
				.append("unitY", getUnitY())
				.append("sizeWidth", getSizeWidth())
				.append("sizeHeight", getSizeHeight())
				.append("pageCount", getPageCount())
				.append("lineCount", getLineCount())
				.append("printAreaX", getPrintAreaX())
				.append("printAreaY", getPrintAreaY())
				.append("printAreaWidth", getPrintAreaWidth())
				.append("printAreaHeight", getPrintAreaHeight())
				.append("restrictedAreaX", getRestrictedAreaX())
				.append("restrictedAreaY", getRestrictedAreaY())
				.append("restrictedAreaWidth", getRestrictedAreaWidth())
				.append("restrictedAreaHeight", getRestrictedAreaHeight())
				.append("stagger", getStagger())
				.append("foldType", getFoldType())
				.append("paperSources", getPaperSources())
				.toString();
	}
}
