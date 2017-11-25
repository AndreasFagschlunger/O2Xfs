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

#include "cim/at_o2xfs_xfs_v3_10_cim_CashIn310Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHIN CashIn;
static WFSCIMNOTENUMBERLIST NoteNumberList;
static WFSCIMNOTENUMBER NoteNumber[4];
static LPWFSCIMNOTENUMBER lpNoteNumber[4];
static WFSCIMPHCU Physical;
static LPWFSCIMPHCU pPhysical[1];
static LPSTR lpPhysicalPositionName = "COMPARTMENT2";
static USHORT usNoteIDs[] = { 1, 2, 3, 4, 0 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cim_CashIn310Test_buildCashIn310(JNIEnv *env, jobject obj) {
	CashIn.usNumber = 9;
	CashIn.fwType = WFS_CIM_TYPECDMSPECIFIC;
	strcpy(CashIn.cUnitID, "66889");
	strcpy(CashIn.cCurrencyID, "   ");
	CashIn.ulValues = 0;
	CashIn.ulCashInCount = 0;
	CashIn.ulCount = 5;
	CashIn.ulMaximum = 0;
	CashIn.usStatus = WFS_CIM_STATCUOK;
	CashIn.bAppLock = FALSE;
	CashIn.lpNoteNumberList = &NoteNumberList;
	NoteNumberList.usNumOfNoteNumbers = 4;
	NoteNumberList.lppNoteNumber = lpNoteNumber;
	int noteNumberIndex = 0;
	NoteNumber[noteNumberIndex].usNoteID = 25606;
	NoteNumber[noteNumberIndex].ulCount = 2;
	lpNoteNumber[noteNumberIndex] = &NoteNumber[noteNumberIndex];
	noteNumberIndex++;
	NoteNumber[noteNumberIndex].usNoteID = 25610;
	NoteNumber[noteNumberIndex].ulCount = 1;
	lpNoteNumber[noteNumberIndex] = &NoteNumber[noteNumberIndex];
	noteNumberIndex++;
	NoteNumber[noteNumberIndex].usNoteID = 25614;
	NoteNumber[noteNumberIndex].ulCount = 1;
	lpNoteNumber[noteNumberIndex] = &NoteNumber[noteNumberIndex];
	noteNumberIndex++;
	NoteNumber[noteNumberIndex].usNoteID = 25616;
	NoteNumber[noteNumberIndex].ulCount = 1;
	lpNoteNumber[noteNumberIndex] = &NoteNumber[noteNumberIndex];
	CashIn.usNumPhysicalCUs = 1;
	CashIn.lppPhysical = pPhysical;
	pPhysical[0] = &Physical;
	Physical.lpPhysicalPositionName = lpPhysicalPositionName;
	strcpy(Physical.cUnitID, "66889");
	Physical.ulCashInCount = 0;
	Physical.ulCount = 5;
	Physical.ulMaximum = 100;
	Physical.usPStatus = WFS_CIM_STATCUOK;
	Physical.bHardwareSensors = FALSE;
	Physical.ulInitialCount = 0;
	Physical.ulDispensedCount = 0;
	Physical.ulPresentedCount = 0;
	Physical.ulRetractedCount = 0;
	Physical.ulRejectCount = 0;
	CashIn.lpusNoteIDs = usNoteIDs;
	CashIn.usCDMType = WFS_CDM_TYPEREJECTCASSETTE;
	CashIn.ulInitialCount = 0;
	CashIn.ulDispensedCount = 0;
	CashIn.ulPresentedCount = 0;
	CashIn.ulRetractedCount = 0;
	CashIn.ulRejectCount = 0;
	CashIn.ulMinimum = 0;
	return NewBuffer(env, &CashIn, sizeof(WFSCIMCASHIN));
}
