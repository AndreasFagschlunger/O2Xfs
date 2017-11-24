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

#include "cim/at_o2xfs_xfs_v3_00_cim_CashInfo3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINFO CashInfo;
static WFSCIMCASHIN CashIn[9];
static LPWFSCIMCASHIN lpCashIn[9];
static WFSCIMPHCU Physical[9];
static LPWFSCIMPHCU lpPhysical[9];
static LPSTR PhysicalPositionName[] = { "COMPARTMENT1", "SLOT1", "SLOT2", "SLOT3", "SLOT4", "HEADUNIT", "COMPARTMENT3", "SLOT5" };
static LPSTR Extra[] = { "Basetype=BIG_COMPARTMENT\0DispenseLockStatus=NOT_LOCKED\0", "Basetype=STD_CASSETTE\0", "Basetype=STD_CASSETTE\0", "Basetype=STD_CASSETTE\0", "Basetype=SMALL_BOX\0", "Basetype=SMALL_COMPARTMENT\0", "Basetype=STD_CASSETTE\0", "Basetype=STD_CASSETTE\0" };
static WFSCIMNOTENUMBERLIST NoteNumberList[8];
static WFSCIMNOTENUMBER NoteNumber[11];
static LPWFSCIMNOTENUMBER lpNoteNumber[11];


JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cim_CashInfo3Test_buildCashInfo3(JNIEnv *env, jobject obj) {
	CashInfo.usCount = 8;
	CashInfo.lppCashIn = lpCashIn;

	for(int i = 0; i < CashInfo.usCount; i++) {
		lpCashIn[i] = &CashIn[i];
		lpPhysical[i] = &Physical[i];
		CashIn[i].usNumPhysicalCUs = 1;
		CashIn[i].lppPhysical = &lpPhysical[i];
	}

	int index = 0;
	CashIn[index].usNumber = 1;
	CashIn[index].fwType = WFS_CIM_TYPERETRACTCASSETTE;
	CashIn[index].fwItemType = WFS_CIM_CITYPALL | WFS_CIM_CITYPUNFIT;
	strcpy(CashIn[index].cUnitID, "12345");
	strcpy(CashIn[index].cCurrencyID, "   ");
	CashIn[index].ulValues = 0;
	CashIn[index].ulCashInCount = 0;
	CashIn[index].ulCount = 0;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = NULL;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = 0;
	Physical[index].ulCount = 0;
	Physical[index].ulMaximum = 150;
	Physical[index].usPStatus = WFS_CIM_STATCUOK;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 2;
	CashIn[index].fwType = WFS_CIM_TYPERECYCLING;
	CashIn[index].fwItemType = WFS_CIM_CITYPINDIVIDUAL;
	strcpy(CashIn[index].cUnitID, "12346");
	strcpy(CashIn[index].cCurrencyID, "EUR");
	CashIn[index].ulValues = 1000;
	CashIn[index].ulCashInCount = 1234;
	CashIn[index].ulCount = 4321;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = &NoteNumberList[0];
	NoteNumberList[0].usNumOfNoteNumbers = 1;
	NoteNumberList[0].lppNoteNumber = &lpNoteNumber[0];
	lpNoteNumber[0] = &NoteNumber[0];
	NoteNumber[0].usNoteID = 4321;
	NoteNumber[0].ulCount = 1234;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 2400;
	Physical[index].usPStatus = WFS_CIM_STATCUOK;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 3;
	CashIn[index].fwType = WFS_CIM_TYPERECYCLING;
	CashIn[index].fwItemType = WFS_CIM_CITYPINDIVIDUAL;
	strcpy(CashIn[index].cUnitID, "12347");
	strcpy(CashIn[index].cCurrencyID, "EUR");
	CashIn[index].ulValues = 2000;
	CashIn[index].ulCashInCount = 1234;
	CashIn[index].ulCount = 4321;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = &NoteNumberList[1];
	NoteNumberList[1].usNumOfNoteNumbers = 1;
	NoteNumberList[1].lppNoteNumber = &lpNoteNumber[1];
	lpNoteNumber[1] = &NoteNumber[1];
	NoteNumber[1].usNoteID = 4321;
	NoteNumber[1].ulCount = 1234;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 2400;
	Physical[index].usPStatus = WFS_CIM_STATCUOK;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 4;
	CashIn[index].fwType = WFS_CIM_TYPERECYCLING;
	CashIn[index].fwItemType = WFS_CIM_CITYPINDIVIDUAL;
	strcpy(CashIn[index].cUnitID, "12348");
	strcpy(CashIn[index].cCurrencyID, "EUR");
	CashIn[index].ulValues = 5000;
	CashIn[index].ulCashInCount = 1234;
	CashIn[index].ulCount = 4321;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = &NoteNumberList[2];
	NoteNumberList[2].usNumOfNoteNumbers = 1;
	NoteNumberList[2].lppNoteNumber = &lpNoteNumber[2];
	lpNoteNumber[2] = &NoteNumber[2];
	NoteNumber[2].usNoteID = 4321;
	NoteNumber[2].ulCount = 1234;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 2400;
	Physical[index].usPStatus = WFS_CIM_STATCUOK;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 5;
	CashIn[index].fwType = WFS_CIM_TYPERECYCLING;
	CashIn[index].fwItemType = WFS_CIM_CITYPINDIVIDUAL;
	strcpy(CashIn[index].cUnitID, "12349");
	strcpy(CashIn[index].cCurrencyID, "EUR");
	CashIn[index].ulValues = 10000;
	CashIn[index].ulCashInCount = 1234;
	CashIn[index].ulCount = 4321;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = &NoteNumberList[3];
	NoteNumberList[3].usNumOfNoteNumbers = 1;
	NoteNumberList[3].lppNoteNumber = &lpNoteNumber[3];
	lpNoteNumber[3] = &NoteNumber[3];
	NoteNumber[3].usNoteID = 4321;
	NoteNumber[3].ulCount = 1234;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 2400;
	Physical[index].usPStatus = WFS_CIM_STATCUOK;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 6;
	CashIn[index].fwType = WFS_CIM_TYPECASHIN;
	CashIn[index].fwItemType = WFS_CIM_CITYPLEVEL2;
	strcpy(CashIn[index].cUnitID, "12350");
	strcpy(CashIn[index].cCurrencyID, "   ");
	CashIn[index].ulValues = 0;
	CashIn[index].ulCashInCount = 0;
	CashIn[index].ulCount = 0;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = NULL;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 20;
	Physical[index].usPStatus = WFS_CIM_STATCUEMPTY;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 7;
	CashIn[index].fwType = WFS_CIM_TYPECASHIN;
	CashIn[index].fwItemType = 0x2000;
	strcpy(CashIn[index].cUnitID, "12351");
	strcpy(CashIn[index].cCurrencyID, "   ");
	CashIn[index].ulValues = 0;
	CashIn[index].ulCashInCount = 0;
	CashIn[index].ulCount = 0;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = NULL;
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 100;
	Physical[index].usPStatus = WFS_CIM_STATCUEMPTY;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	index++;
	CashIn[index].usNumber = 8;
	CashIn[index].fwType = WFS_CIM_TYPECASHIN;
	CashIn[index].fwItemType = WFS_CIM_CITYPALL | WFS_CIM_CITYPUNFIT | WFS_CIM_CITYPLEVEL3;
	strcpy(CashIn[index].cUnitID, "12352");
	strcpy(CashIn[index].cCurrencyID, "   ");
	CashIn[index].ulValues = 0;
	CashIn[index].ulCashInCount = 0;
	CashIn[index].ulCount = 0;
	CashIn[index].ulMaximum = 0;
	CashIn[index].usStatus = WFS_CIM_STATCUOK;
	CashIn[index].bAppLock = FALSE;
	CashIn[index].lpNoteNumberList = &NoteNumberList[7];
	NoteNumberList[7].usNumOfNoteNumbers = 7;
	NoteNumberList[7].lppNoteNumber = &lpNoteNumber[4];
	NoteNumber[4].usNoteID = 25602;
	NoteNumber[4].ulCount = 708;
	lpNoteNumber[4] = &NoteNumber[4];
	NoteNumber[5].usNoteID = 25606;
	NoteNumber[5].ulCount = 66;
	lpNoteNumber[5] = &NoteNumber[5];
	NoteNumber[6].usNoteID = 25610;
	NoteNumber[6].ulCount = 48;
	lpNoteNumber[6] = &NoteNumber[6];
	NoteNumber[7].usNoteID = 25614;
	NoteNumber[7].ulCount = 120;
	lpNoteNumber[7] = &NoteNumber[7];
	NoteNumber[8].usNoteID = 25616;
	NoteNumber[8].ulCount = 28;
	lpNoteNumber[8] = &NoteNumber[8];
	NoteNumber[9].usNoteID = 25618;
	NoteNumber[9].ulCount = 13;
	lpNoteNumber[9] = &NoteNumber[9];
	NoteNumber[10].usNoteID = 25620;
	NoteNumber[10].ulCount = 61;
	lpNoteNumber[10] = &NoteNumber[10];
	Physical[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(Physical[index].cUnitID, CashIn[index].cUnitID);
	Physical[index].ulCashInCount = CashIn[index].ulCashInCount;
	Physical[index].ulCount = CashIn[index].ulCount;
	Physical[index].ulMaximum = 100;
	Physical[index].usPStatus = WFS_CIM_STATCUEMPTY;
	Physical[index].bHardwareSensors = FALSE;
	Physical[index].lpszExtra = Extra[index];
	CashIn[index].lpszExtra = NULL;

	return NewBuffer(env, &CashInfo, sizeof(WFSCIMCASHINFO));
}
