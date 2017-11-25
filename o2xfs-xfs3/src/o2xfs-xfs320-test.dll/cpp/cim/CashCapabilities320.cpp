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

#include "cim/at_o2xfs_xfs_v3_20_cim_CashCapabilities320Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHCAPABILITIES CashCaps;
static WFSCIMCASHUNITCAPABILITIES CashUnitCaps[1];
static LPWFSCIMCASHUNITCAPABILITIES lpCashUnitCaps[1];
static WFSCIMPHCUCAPABILITIES Physical[2];
static LPWFSCIMPHCUCAPABILITIES lpPhysical[2];
static LPSTR lpPhysicalPositionName[] = { "SLOT1", "SLOT2" };
static LPSTR lpszExtra = "Key=Value\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_cim_CashCapabilities320Test_buildCashCapabilities320(JNIEnv *env, jobject obj) {
	CashCaps.usCount = 1;
	CashCaps.lppCashUnitCaps = lpCashUnitCaps;
	lpCashUnitCaps[0] = &CashUnitCaps[0];
	CashUnitCaps[0].usNumber = 1;
	CashUnitCaps[0].usNumPhysicalCUs = 2;
	CashUnitCaps[0].lppPhysical = lpPhysical;
	lpPhysical[0] = &Physical[0];
	Physical[0].lpPhysicalPositionName = lpPhysicalPositionName[0];
	Physical[0].ulMaximum = 2400;
	Physical[0].bHardwareSensors = FALSE;
	Physical[0].lpszExtra = NULL;
	lpPhysical[1] = &Physical[1];
	Physical[1].lpPhysicalPositionName = lpPhysicalPositionName[1];
	Physical[1].ulMaximum = 2400;
	Physical[1].bHardwareSensors = FALSE;
	Physical[1].lpszExtra = NULL;
	CashUnitCaps[0].bRetractNoteCountThresholds = TRUE;
	CashUnitCaps[0].lpszExtra = lpszExtra;
	return NewBuffer(env, &CashCaps, sizeof(WFSCIMCASHCAPABILITIES));
}
