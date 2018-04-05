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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.ptr.FormAccess;
import at.o2xfs.xfs.ptr.FormClass;
import at.o2xfs.xfs.ptr.FormOverflow;
import at.o2xfs.xfs.ptr.FieldType;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class FormField3 extends Struct {

	protected final LPSTR fieldName = new LPSTR();
	protected final WORD indexCount = new WORD();
	protected final XfsWord<FieldType> type = new XfsWord<>(FieldType.class);
	protected final XfsWordBitmask<FormClass> serviceClass = new XfsWordBitmask<>(FormClass.class);
	protected final XfsWordBitmask<FormAccess> access = new XfsWordBitmask<>(FormAccess.class);
	protected final XfsWord<FormOverflow> overflow = new XfsWord<>(FormOverflow.class);
	protected final LPSTR initialValue = new LPSTR();
	protected final LPSTR uNICODEInitialValue = new LPSTR();
	protected final LPSTR format = new LPSTR();
	protected final LPSTR uNICODEFormat = new LPSTR();

	protected FormField3() {
		add(fieldName);
		add(indexCount);
		add(type);
		add(serviceClass);
		add(access);
		add(overflow);
		add(initialValue);
		add(uNICODEInitialValue);
		add(format);
		add(uNICODEFormat);
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
		initialValue.set(copy.getInitialValue());
		uNICODEInitialValue.set(copy.getUNICODEInitialValue());
		format.set(copy.getFormat());
		uNICODEFormat.set(copy.getUNICODEFormat());
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

	public Set<FormClass> getServiceClass() {
		return serviceClass.get();
	}

	public Set<FormAccess> getAccess() {
		return access.get();
	}

	public FormOverflow getOverflow() {
		return overflow.get();
	}

	public String getInitialValue() {
		return initialValue.get();
	}

	public String getUNICODEInitialValue() {
		return uNICODEInitialValue.get();
	}

	public String getFormat() {
		return format.get();
	}

	public String getUNICODEFormat() {
		return uNICODEFormat.get();
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
				.append(getUNICODEInitialValue())
				.append(getFormat())
				.append(getUNICODEFormat())
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
					.append(getUNICODEInitialValue(), formField3.getUNICODEInitialValue())
					.append(getFormat(), formField3.getFormat())
					.append(getUNICODEFormat(), formField3.getUNICODEFormat())
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
				.append("uNICODEInitialValue", getUNICODEInitialValue())
				.append("format", getFormat())
				.append("uNICODEFormat", getUNICODEFormat())
				.toString();
	}
}
