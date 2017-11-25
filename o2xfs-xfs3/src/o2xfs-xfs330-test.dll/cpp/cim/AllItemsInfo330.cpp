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

#include "cim/at_o2xfs_xfs_v3_30_cim_AllItemsInfo330Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMALLITEMSINFO AllItemsInfo;
static WFSCIMITEMINFOALL ItemsList[1];
static LPWFSCIMITEMINFOALL lpItemsList[1];
static LPWSTR lpszSerialNumber = L"12?4";
static LPSTR lpszP6SignatureFileName = "P6.txt";
static LPSTR lpszImageFileName = "note.jpg";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cim_AllItemsInfo330Test_buildAllItemsInfo330(JNIEnv *env, jobject obj) {
	AllItemsInfo.usCount = 1;
	AllItemsInfo.lppItemsList = lpItemsList;
	lpItemsList[0] = &ItemsList[0];
	ItemsList[0].usLevel = WFS_CIM_LEVEL_4;
	ItemsList[0].usNoteID = 1;
	ItemsList[0].lpszSerialNumber = lpszSerialNumber;
	ItemsList[0].dwOrientation = WFS_CIM_ORBACKBOTTOM;
	ItemsList[0].lpszP6SignatureFileName = lpszP6SignatureFileName;
	ItemsList[0].lpszImageFileName = lpszImageFileName;
	ItemsList[0].wOnBlacklist = WFS_CIM_ONBLACKLIST;
	ItemsList[0].wItemLocation = WFS_CIM_LOCATION_CASHUNIT;
	ItemsList[0].usNumber = 1;
	return NewBuffer(env, &AllItemsInfo, sizeof(WFSCIMALLITEMSINFO));
}
