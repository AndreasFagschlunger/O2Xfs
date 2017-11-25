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

#include "cim/at_o2xfs_xfs_v3_10_cim_P6CompareSignature310Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMP6COMPARESIGNATURE P6CompareSignature;
WFSCIMP6SIGNATURE P6Signatures[2];
LPWFSCIMP6SIGNATURE lpP6ReferenceSignatures[2];
LPWFSCIMP6SIGNATURE lpP6Signatures[2];
char *lpSignature[] = { "ABC", "DEF" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cim_P6CompareSignature310Test_buildP6CompareSignature310(JNIEnv *env, jobject obj) {
	P6Signatures[0].usNoteId = 1;
	P6Signatures[0].ulLength = strlen(lpSignature[0]);
	P6Signatures[0].dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signatures[0].lpSignature = lpSignature[0];
	lpP6ReferenceSignatures[0] = &P6Signatures[0];
	lpP6ReferenceSignatures[1] = NULL;
	P6CompareSignature.lppP6ReferenceSignatures = lpP6ReferenceSignatures;

	P6Signatures[1].usNoteId = 1;
	P6Signatures[1].ulLength = strlen(lpSignature[1]);
	P6Signatures[1].dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signatures[1].lpSignature = lpSignature[1];
	lpP6Signatures[0] = &P6Signatures[1];
	lpP6Signatures[1] = NULL;
	P6CompareSignature.lppP6Signatures = lpP6Signatures;
	return NewBuffer(env, &P6CompareSignature, sizeof(WFSCIMP6COMPARESIGNATURE));
}
