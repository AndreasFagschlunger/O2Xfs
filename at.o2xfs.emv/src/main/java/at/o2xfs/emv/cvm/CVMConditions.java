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

package at.o2xfs.emv.cvm;

import java.util.HashMap;
import java.util.Map;

import at.o2xfs.common.Hex;
import at.o2xfs.emv.cvm.impl.AlwaysCVMCondition;
import at.o2xfs.emv.cvm.impl.IfTerminalSupportsCondition;
import at.o2xfs.emv.cvm.impl.ManualCashCondition;
import at.o2xfs.emv.cvm.impl.NotUnattendedCashAndNotManualCashAndNotPurchaseWithCashbackCondition;
import at.o2xfs.emv.cvm.impl.PurchaseWithCashbackCondition;
import at.o2xfs.emv.cvm.impl.UnattendedCashCondition;

public final class CVMConditions {

	private static final Map<Integer, CVMCondition> CONDITIONS;

	static {
		CONDITIONS = new HashMap<Integer, CVMCondition>();
		addCondition(0x00, new AlwaysCVMCondition());
		addCondition(0x01, new UnattendedCashCondition());
		addCondition(
				0x02,
				new NotUnattendedCashAndNotManualCashAndNotPurchaseWithCashbackCondition());
		addCondition(0x03, new IfTerminalSupportsCondition());
		addCondition(0x04, new ManualCashCondition());
		addCondition(0x05, new PurchaseWithCashbackCondition());
	}

	private CVMConditions() {
		throw new AssertionError();
	}

	public static final void addCondition(int conditionCode,
			CVMCondition condition) throws IllegalStateException {
		if (condition == null) {
			throw new NullPointerException("condition must not be null");
		}
		Integer key = Integer.valueOf(conditionCode);
		if (CONDITIONS.containsKey(key)) {
			throw new IllegalStateException("Condition Code "
					+ Hex.encode(conditionCode) + " already assigned");
		}
		CONDITIONS.put(key, condition);
	}

	public static final CVMCondition getCondition(int conditionCode) {
		CVMCondition cvmCondition = CONDITIONS.get(Integer
				.valueOf(conditionCode));
		return cvmCondition;
	}
}