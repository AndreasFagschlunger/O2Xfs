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

#include "at_o2xfs_xfs_v3_30_cdm_ItemInfo330Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMITEMINFO ItemInfo;
static LPWSTR lpszSerialNumber = L"1234";
static WFSCDMSIGNATURE Signature;
static LPSTR lpszImageFileName = "C:\\Temp\\cash123456.jpg";
static char Data[] = { 0xAB, 0xCD, 0xEF };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cdm_ItemInfo330Test_buildItemInfo330(JNIEnv *env, jobject obj) {
	Signature.ulLength = 3;
	Signature.lpData = Data;

	strcpy(ItemInfo.cCurrencyID, "EUR");
	ItemInfo.ulValue = 10;
	ItemInfo.usRelease = 1;
	ItemInfo.lpszSerialNumber = lpszSerialNumber;
	ItemInfo.lpSignature = &Signature;
	ItemInfo.lpszImageFileName = lpszImageFileName;

	return NewBuffer(env, &ItemInfo, sizeof(WFSCDMITEMINFO));
}
