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

#include "at_o2xfs_xfs_v3_30_cdm_Blacklist330Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMBLACKLIST Blacklist;
static LPWSTR Version = L"1.0.0";
static WFSCDMBLACKLISTELEMENT BlacklistElements[2];
static LPWFSCDMBLACKLISTELEMENT lppBlacklistElements[2];
static LPWSTR SerialNumber[] = { L"123", L"456" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cdm_Blacklist330Test_buildBlacklist330(JNIEnv *env, jobject obj) {
	Blacklist.lpszVersion = Version;
	Blacklist.usCount = 2;
	Blacklist.lppBlacklistElements = lppBlacklistElements;

	BlacklistElements[0].lpszSerialNumber = SerialNumber[0];
	strcpy(BlacklistElements[0].cCurrencyID, "EUR");
	BlacklistElements[0].ulValue = 10;
	lppBlacklistElements[0] = &BlacklistElements[0];

	BlacklistElements[1].lpszSerialNumber = SerialNumber[1];
	strcpy(BlacklistElements[1].cCurrencyID, "EUR");
	BlacklistElements[1].ulValue = 50;
	lppBlacklistElements[1] = &BlacklistElements[1];

	return NewBuffer(env, &Blacklist, sizeof(WFSCDMBLACKLIST));
}
