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

package at.o2xfs.xfs.v3_10.cim;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.v3_00.cim.CashInfo3;

public class CashInfo310 extends CashInfo3 {

	public static class Builder {

		private final CashIn310[] cashIn;

		public Builder(CashIn310[] cashIn) {
			this.cashIn = cashIn;
		}

		public CashInfo310 build() {
			return new CashInfo310(this);
		}
	}

	protected CashInfo310(Builder builder) {
		super();
		allocate();
		count.set(builder.cashIn.length);
		cashIn.pointTo(new CashInArray310(builder.cashIn));

	}

	public CashInfo310(CashInfo310 copy) {
		super();
		allocate();
		set(copy);
	}

	public CashInfo310(Pointer p) {
		super(p);
	}

	protected void set(CashInfo310 copy) {
		count.set(copy.getCount());
		cashIn.pointTo(new CashInArray310(copy.getCashIn()));
	}

	@Override
	public CashIn310[] getCashIn() {
		return new CashInArray310(cashIn, getCount()).get();
	}
}
