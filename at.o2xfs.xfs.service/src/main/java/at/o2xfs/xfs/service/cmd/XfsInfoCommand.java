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

package at.o2xfs.xfs.service.cmd;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Assert;
import at.o2xfs.win32.Type;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.service.XfsService;

/**
 * @author Andreas Fagschlunger
 *
 */
public class XfsInfoCommand<T extends Enum<? extends XfsConstant>> extends XfsCommand {

	public static class Builder<T extends Enum<? extends XfsConstant>> {

		private final XfsService xfsService;

		private final T category;

		private Type queryDetails;

		public Builder(XfsService xfsService, T category) {
			this.xfsService = xfsService;
			this.category = category;
		}

		public Builder<T> queryDetails(Type queryDetails) {
			this.queryDetails = queryDetails;
			return this;
		}

		public XfsInfoCommand<T> build() {
			return new XfsInfoCommand<T>(this);
		}

	}

	private final T category;

	private final Type queryDetails;

	private XfsInfoCommand(Builder<T> builder) {
		this(builder.xfsService, builder.category, builder.queryDetails);
	}

	public XfsInfoCommand(XfsService xfsService, T category) {
		this(xfsService, category, null);
	}

	public XfsInfoCommand(XfsService xfsService, T category, Type queryDetails) {
		this(xfsService, category, queryDetails, null);
	}

	public XfsInfoCommand(XfsService xfsService, T category, Type queryDetails, Long timeOut) {
		super(xfsService, timeOut);
		Assert.notNull(category);
		this.category = category;
		this.queryDetails = queryDetails;
	}

	/**
	 * @return the category
	 */
	public T getCategory() {
		return category;
	}

	/**
	 * @return the queryDetails
	 */
	public Type getQueryDetails() {
		return queryDetails;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("category", category).append("queryDetails", queryDetails).toString();
	}
}