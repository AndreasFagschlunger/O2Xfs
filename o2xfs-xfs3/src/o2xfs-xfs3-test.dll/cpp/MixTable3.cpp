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

#include "at_o2xfs_xfs_v3_00_cdm_MixTable3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMMIXTABLE mixTable;
static LPSTR szName = "Test";
static ULONG ulMixHeader[3] = {10, 20, 50};
static WFSCDMMIXROW mixRows[1];
static LPWFSCDMMIXROW lpMixRows[1];
static USHORT usMixture[3] = {1, 2, 1};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cdm_MixTable3Test_buildMixTable3(JNIEnv *env, jobject obj) {
	mixTable.usMixNumber = 1;
	mixTable.lpszName = szName;
	mixTable.usRows = 1;
	mixTable.usCols = 3;
	mixTable.lpulMixHeader = ulMixHeader;
	mixTable.lppMixRows = lpMixRows;

	mixRows[0].ulAmount = 100;
	mixRows[0].lpusMixture = usMixture;
	lpMixRows[0] = mixRows;

	return NewBuffer(env, &mixTable, sizeof(LPWFSCDMMIXTABLE));
}
