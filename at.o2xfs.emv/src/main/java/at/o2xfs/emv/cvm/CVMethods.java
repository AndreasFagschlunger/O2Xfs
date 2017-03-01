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
import at.o2xfs.emv.cvm.impl.AndSignatureCVMethod;
import at.o2xfs.emv.cvm.impl.EncipheredPINByICC;
import at.o2xfs.emv.cvm.impl.FailCVMethod;
import at.o2xfs.emv.cvm.impl.NoCVMRequiredCVMethod;
import at.o2xfs.emv.cvm.impl.OnlinePINCVMethod;
import at.o2xfs.emv.cvm.impl.PlaintextPINAndSignatureCVMethod;
import at.o2xfs.emv.cvm.impl.PlaintextPINCVMethod;
import at.o2xfs.emv.cvm.impl.SignatureCVMethod;

public class CVMethods {

	private static final Map<Integer, CVMethod> METHODS;

	static {
		METHODS = new HashMap<Integer, CVMethod>();
		addCVMethod(CVMCode.FAIL_CVM_PROCESSING, new FailCVMethod());
		addCVMethod(CVMCode.PLAINTEXT_PIN_BY_ICC, new PlaintextPINCVMethod());
		addCVMethod(CVMCode.ENCIPHERED_PIN_VERIFIED_ONLINE,
				new OnlinePINCVMethod());
		addCVMethod(CVMCode.PLAINTEXT_PIN_BY_ICC_AND_SIGNATURE,
				new PlaintextPINAndSignatureCVMethod());
		addCVMethod(CVMCode.ENCIPHERED_PIN_BY_ICC, new EncipheredPINByICC());
		addCVMethod(CVMCode.ENCIPHERED_PIN_BY_ICC_AND_SIGNATURE,
				new AndSignatureCVMethod(new EncipheredPINByICC()));
		addCVMethod(CVMCode.SIGNATURE, new SignatureCVMethod());
		addCVMethod(CVMCode.NO_CVM_REQUIRED, new NoCVMRequiredCVMethod());
	}

	private CVMethods() {
		throw new AssertionError();
	}

	public static void addCVMethod(int cvmCode, CVMethod cvMethod)
			throws IllegalStateException {
		if (cvMethod == null) {
			throw new NullPointerException("cvMethod must not be null");
		}
		if (METHODS.containsKey(Integer.valueOf(cvmCode))) {
			throw new IllegalStateException("CVMCode " + Hex.encode(cvmCode)
					+ " already assigned");
		}
		METHODS.put(Integer.valueOf(cvmCode), cvMethod);
	}

	public static CVMethod getCVMethod(int cvmCode)
			throws NoSuchCVMethodException {
		CVMethod cvMethod = METHODS.get(Integer.valueOf(cvmCode));
		if (cvMethod == null) {
			throw new NoSuchCVMethodException(cvmCode);
		}
		return cvMethod;
	}

}