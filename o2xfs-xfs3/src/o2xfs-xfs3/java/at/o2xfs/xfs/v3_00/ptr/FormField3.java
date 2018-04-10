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
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.ptr.FieldType;
import at.o2xfs.xfs.ptr.FormAccess;
import at.o2xfs.xfs.ptr.FormClass;
import at.o2xfs.xfs.ptr.FormOverflow;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class FormField3 extends Struct {

	protected final LPSTR fieldName = new LPSTR();
	protected final WORD indexCount = new WORD();
	protected final XfsWord<FieldType> type = new XfsWord<>(FieldType.class);
	protected final XfsWord<FormClass> serviceClass = new XfsWord<>(FormClass.class);
	protected final XfsWordBitmask<FormAccess> access = new XfsWordBitmask<>(FormAccess.class);
	protected final XfsWord<FormOverflow> overflow = new XfsWord<>(FormOverflow.class);
	protected final LPSTR initialValue = new LPSTR();
	protected final LPWSTR unicodeInitialValue = new LPWSTR();
	protected final LPSTR format = new LPSTR();
	protected final LPWSTR unicodeFormat = new LPWSTR();

	protected FormField3() {
		add(fieldName);
		add(indexCount);
		add(type);
		add(serviceClass);
		add(access);
		add(overflow);
		add(initialValue);
		add(unicodeInitialValue);
		add(format);
		add(unicodeFormat);
	}

	public FormField3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public FormField3(FormField3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(FormField3 copy) {
		fieldName.set(copy.getFieldName());
		indexCount.set(copy.getIndexCount());
		type.set(copy.getType());
		serviceClass.set(copy.getServiceClass());
		access.set(copy.getAccess());
		overflow.set(copy.getOverflow());
		Optional<String> initialValue = copy.getInitialValue();
		if (initialValue.isPresent()) {
			this.initialValue.set(initialValue.get());
		}
		Optional<String> unicodeInitialValue = copy.getUnicodeInitialValue();
		if (unicodeInitialValue.isPresent()) {
			this.unicodeInitialValue.set(unicodeInitialValue.get());
		}
		Optional<String> format = copy.getFormat();
		if (format.isPresent()) {
			this.format.set(format.get());
		}
		Optional<String> unicodeFormat = copy.getUnicodeFormat();
		if (unicodeFormat.isPresent()) {
			this.unicodeFormat.set(unicodeFormat.get());
		}
	}

	public String getFieldName() {
		return fieldName.get();
	}

	public int getIndexCount() {
		return indexCount.get();
	}

	public FieldType getType() {
		return type.get();
	}

	public FormClass getServiceClass() {
		return serviceClass.get();
	}

	public Set<FormAccess> getAccess() {
		return access.get();
	}

	public FormOverflow getOverflow() {
		return overflow.get();
	}

	public Optional<String> getInitialValue() {
		return Optional.ofNullable(initialValue.get());
	}

	public Optional<String> getUnicodeInitialValue() {
		return Optional.ofNullable(unicodeInitialValue.get());
	}

	public Optional<String> getFormat() {
		return Optional.ofNullable(format.get());
	}

	public Optional<String> getUnicodeFormat() {
		return Optional.ofNullable(unicodeFormat.get());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFieldName())
				.append(getIndexCount())
				.append(getType())
				.append(getServiceClass())
				.append(getAccess())
				.append(getOverflow())
				.append(getInitialValue())
				.append(getUnicodeInitialValue())
				.append(getFormat())
				.append(getUnicodeFormat())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormField3) {
			FormField3 formField3 = (FormField3) obj;
			return new EqualsBuilder()
					.append(getFieldName(), formField3.getFieldName())
					.append(getIndexCount(), formField3.getIndexCount())
					.append(getType(), formField3.getType())
					.append(getServiceClass(), formField3.getServiceClass())
					.append(getAccess(), formField3.getAccess())
					.append(getOverflow(), formField3.getOverflow())
					.append(getInitialValue(), formField3.getInitialValue())
					.append(getUnicodeInitialValue(), formField3.getUnicodeInitialValue())
					.append(getFormat(), formField3.getFormat())
					.append(getUnicodeFormat(), formField3.getUnicodeFormat())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("fieldName", getFieldName())
				.append("indexCount", getIndexCount())
				.append("type", getType())
				.append("serviceClass", getServiceClass())
				.append("access", getAccess())
				.append("overflow", getOverflow())
				.append("initialValue", getInitialValue())
				.append("unicodeInitialValue", getUnicodeInitialValue())
				.append("format", getFormat())
				.append("unicodeFormat", getUnicodeFormat())
				.toString();
	}
}
