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

#include "cim/at_o2xfs_xfs_v3_30_cim_ItemInfo330Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMITEMINFO ItemInfo;
static LPWSTR lpszSerialNumber = L"1234-5678-9012";
static WFSCIMP6SIGNATURE P6Signature;
static LPSTR lpszImageFileName = "C:\\note.png";
static char lpSignature[] = { 'C', 'A', 'F', 'E' };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cim_ItemInfo330Test_buildItemInfo330(JNIEnv *env, jobject obj) {
	ItemInfo.usNoteID = 1;
	ItemInfo.lpszSerialNumber = lpszSerialNumber;
	ItemInfo.lpP6Signature = &P6Signature;
	P6Signature.usNoteId = 1;
	P6Signature.ulLength = 4;
	P6Signature.dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signature.lpSignature = lpSignature;
	ItemInfo.lpszImageFileName = lpszImageFileName;
	return NewBuffer(env, &ItemInfo, sizeof(WFSCIMITEMINFO));
}
