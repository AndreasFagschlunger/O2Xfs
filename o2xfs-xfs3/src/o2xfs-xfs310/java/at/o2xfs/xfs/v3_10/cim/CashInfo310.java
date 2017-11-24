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

package at.o2xfs.xfs.cim.v3_10;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.v3_00.CashInfo3;

public class CashInfo3_10 extends CashInfo3 {

	public static class Builder {

		private final CashIn3_10[] cashIn;

		public Builder(CashIn3_10[] cashIn) {
			this.cashIn = cashIn;
		}

		public CashInfo3_10 build() {
			return new CashInfo3_10(this);
		}
	}

	protected CashInfo3_10(Builder builder) {
		super();
		allocate();
		count.set(builder.cashIn.length);
		cashIn.pointTo(new CashInArray3_10(builder.cashIn));

	}

	public CashInfo3_10(CashInfo3_10 copy) {
		super();
		allocate();
		set(copy);
	}

	public CashInfo3_10(Pointer p) {
		super(p);
	}

	protected void set(CashInfo3_10 copy) {
		count.set(copy.getCount());
		cashIn.pointTo(new CashInArray3_10(copy.getCashIn()));
	}

	@Override
	public CashIn3_10[] getCashIn() {
		return new CashInArray3_10(cashIn, getCount()).get();
	}
}