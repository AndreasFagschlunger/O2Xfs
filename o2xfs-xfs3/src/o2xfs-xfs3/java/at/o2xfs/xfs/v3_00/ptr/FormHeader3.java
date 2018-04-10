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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.ptr.CharSupport;
import at.o2xfs.xfs.ptr.FormAlignment;
import at.o2xfs.xfs.ptr.FormBaseUnit;
import at.o2xfs.xfs.ptr.FormOrientation;
import at.o2xfs.xfs.win32.XfsWord;

public class FormHeader3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final XfsWord<FormBaseUnit> base = new XfsWord<>(FormBaseUnit.class);
	protected final WORD unitX = new WORD();
	protected final WORD unitY = new WORD();
	protected final WORD width = new WORD();
	protected final WORD height = new WORD();
	protected final XfsWord<FormAlignment> alignment = new XfsWord<>(FormAlignment.class);
	protected final XfsWord<FormOrientation> orientation = new XfsWord<>(FormOrientation.class);
	protected final WORD offsetX = new WORD();
	protected final WORD offsetY = new WORD();
	protected final WORD versionMajor = new WORD();
	protected final WORD versionMinor = new WORD();
	protected final LPSTR userPrompt = new LPSTR();
	protected final XfsWord<CharSupport> charSupport = new XfsWord<>(CharSupport.class);
	protected final LPZZSTR fields = new LPZZSTR();

	protected FormHeader3() {
		add(formName);
		add(base);
		add(unitX);
		add(unitY);
		add(width);
		add(height);
		add(alignment);
		add(orientation);
		add(offsetX);
		add(offsetY);
		add(versionMajor);
		add(versionMinor);
		add(userPrompt);
		add(charSupport);
		add(fields);
	}

	public FormHeader3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public FormHeader3(FormHeader3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(FormHeader3 copy) {
		formName.set(copy.getFormName());
		base.set(copy.getBase());
		unitX.set(copy.getUnitX());
		unitY.set(copy.getUnitY());
		width.set(copy.getWidth());
		height.set(copy.getHeight());
		alignment.set(copy.getAlignment());
		orientation.set(copy.getOrientation());
		offsetX.set(copy.getOffsetX());
		offsetY.set(copy.getOffsetY());
		versionMajor.set(copy.getVersionMajor());
		versionMinor.set(copy.getVersionMinor());
		Optional<String> userPrompt = copy.getUserPrompt();
		if (userPrompt.isPresent()) {
			this.userPrompt.set(userPrompt.get());
		}
		charSupport.set(copy.getCharSupport());
		fields.set(copy.getFields());
	}

	public String getFormName() {
		return formName.get();
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

	public int getWidth() {
		return width.get();
	}

	public int getHeight() {
		return height.get();
	}

	public FormAlignment getAlignment() {
		return alignment.get();
	}

	public FormOrientation getOrientation() {
		return orientation.get();
	}

	public int getOffsetX() {
		return offsetX.get();
	}

	public int getOffsetY() {
		return offsetY.get();
	}

	public int getVersionMajor() {
		return versionMajor.get();
	}

	public int getVersionMinor() {
		return versionMinor.get();
	}

	public Optional<String> getUserPrompt() {
		return Optional.ofNullable(userPrompt.get());
	}

	public CharSupport getCharSupport() {
		return charSupport.get();
	}

	public String[] getFields() {
		return fields.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFormName())
				.append(getBase())
				.append(getUnitX())
				.append(getUnitY())
				.append(getWidth())
				.append(getHeight())
				.append(getAlignment())
				.append(getOrientation())
				.append(getOffsetX())
				.append(getOffsetY())
				.append(getVersionMajor())
				.append(getVersionMinor())
				.append(getUserPrompt())
				.append(getCharSupport())
				.append(getFields())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormHeader3) {
			FormHeader3 formHeader3 = (FormHeader3) obj;
			return new EqualsBuilder()
					.append(getFormName(), formHeader3.getFormName())
					.append(getBase(), formHeader3.getBase())
					.append(getUnitX(), formHeader3.getUnitX())
					.append(getUnitY(), formHeader3.getUnitY())
					.append(getWidth(), formHeader3.getWidth())
					.append(getHeight(), formHeader3.getHeight())
					.append(getAlignment(), formHeader3.getAlignment())
					.append(getOrientation(), formHeader3.getOrientation())
					.append(getOffsetX(), formHeader3.getOffsetX())
					.append(getOffsetY(), formHeader3.getOffsetY())
					.append(getVersionMajor(), formHeader3.getVersionMajor())
					.append(getVersionMinor(), formHeader3.getVersionMinor())
					.append(getUserPrompt(), formHeader3.getUserPrompt())
					.append(getCharSupport(), formHeader3.getCharSupport())
					.append(getFields(), formHeader3.getFields())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("formName", getFormName())
				.append("base", getBase())
				.append("unitX", getUnitX())
				.append("unitY", getUnitY())
				.append("width", getWidth())
				.append("height", getHeight())
				.append("alignment", getAlignment())
				.append("orientation", getOrientation())
				.append("offsetX", getOffsetX())
				.append("offsetY", getOffsetY())
				.append("versionMajor", getVersionMajor())
				.append("versionMinor", getVersionMinor())
				.append("userPrompt", getUserPrompt())
				.append("charSupport", getCharSupport())
				.append("fields", getFields())
				.toString();
	}
}
