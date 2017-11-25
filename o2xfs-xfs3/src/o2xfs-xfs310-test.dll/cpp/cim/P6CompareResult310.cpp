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

#include "cim/at_o2xfs_xfs_v3_10_cim_P6CompareResult310Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMP6COMPARERESULT P6CompareResult;
WFSCIMP6SIGNATURESINDEX P6SignaturesIndex[1];
LPWFSCIMP6SIGNATURESINDEX lpP6SignaturesIndex[2];
char* lpComparisonData = "ABC";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cim_P6CompareResult310Test_buildP6CompareResult310(JNIEnv *env, jobject obj) {
	P6CompareResult.usCount = 1;
	P6CompareResult.lppP6SignaturesIndex = lpP6SignaturesIndex;
	P6SignaturesIndex[0].usIndex = 1;
	P6SignaturesIndex[0].usConfidenceLevel = 90;
	P6SignaturesIndex[0].ulLength = strlen(lpComparisonData);
	P6SignaturesIndex[0].lpComparisonData = lpComparisonData;
	lpP6SignaturesIndex[0] = &P6SignaturesIndex[0];
	lpP6SignaturesIndex[1] = NULL;
	return NewBuffer(env, &P6CompareResult, sizeof(WFSCIMP6COMPARERESULT));
}
