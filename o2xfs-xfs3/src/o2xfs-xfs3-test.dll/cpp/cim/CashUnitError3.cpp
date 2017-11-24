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

#include "cim/at_o2xfs_xfs_v3_00_cim_CashUnitError3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCUERROR CashUnitError;
static WFSCIMCASHIN CashUnit;
static WFSCIMNOTENUMBERLIST NoteNumberList[1];
static WFSCIMNOTENUMBER NoteNumber[1];
static LPWFSCIMNOTENUMBER lpNoteNumber[1];
static WFSCIMPHCU Physical[1];
static LPWFSCIMPHCU lpPhysical[1];
static LPSTR PhysicalPositionName[] = { "SLOT1" };
static LPSTR Extra[] = { "Key1=Value1\0Key2=Value2\0", "CUKey1=CUValue1\0CUKey2=CUValue2\0" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cim_CashUnitError3Test_buildCashUnitError3(JNIEnv *env, jobject obj) {
	CashUnitError.wFailure = WFS_CIM_CASHUNITERROR;
	CashUnitError.lpCashUnit = &CashUnit;

	CashUnit.usNumber = 2;
	CashUnit.fwType = WFS_CIM_TYPERECYCLING;
	CashUnit.fwItemType = WFS_CIM_CITYPINDIVIDUAL;
	strcpy(CashUnit.cUnitID, "12346");
	strcpy(CashUnit.cCurrencyID, "EUR");
	CashUnit.ulValues = 1000;
	CashUnit.ulCashInCount = 1234;
	CashUnit.ulCount = 4321;
	CashUnit.ulMaximum = 0;
	CashUnit.usStatus = WFS_CIM_STATCUOK;
	CashUnit.bAppLock = FALSE;
	CashUnit.lpNoteNumberList = &NoteNumberList[0];
	NoteNumberList[0].usNumOfNoteNumbers = 1;
	NoteNumberList[0].lppNoteNumber = &lpNoteNumber[0];
	lpNoteNumber[0] = &NoteNumber[0];
	NoteNumber[0].usNoteID = 4321;
	NoteNumber[0].ulCount = 1234;
	CashUnit.lppPhysical = &lpPhysical[0];
	lpPhysical[0] = &Physical[0];

	Physical[0].lpPhysicalPositionName = PhysicalPositionName[0];
	strcpy(Physical[0].cUnitID, CashUnit.cUnitID);
	Physical[0].ulCashInCount = CashUnit.ulCashInCount;
	Physical[0].ulCount = CashUnit.ulCount;
	Physical[0].ulMaximum = 2400;
	Physical[0].usPStatus = WFS_CIM_STATCUOK;
	Physical[0].bHardwareSensors = FALSE;
	Physical[0].lpszExtra = Extra[0];
	CashUnit.lpszExtra = Extra[1];

	return NewBuffer(env, &CashUnitError, sizeof(WFSCIMCUERROR));
}
