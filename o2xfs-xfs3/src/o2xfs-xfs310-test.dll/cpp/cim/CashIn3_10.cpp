#include "cim/at_o2xfs_xfs_cim_v3_10_CashIn3_10Test.h"

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

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_CashIn3_110Test_buildCashIn3_110(JNIEnv *env, jobject obj) {
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
	CashIn.usCDMType = WFS_CDM_TYPEREJECTCASSETTE;
	CashIn.ulInitialCount = 0;
	CashIn.ulDispensedCount = 0;
	CashIn.ulPresentedCount = 0;
	CashIn.ulRetractedCount = 0;
	CashIn.ulRejectCount = 0;
	CashIn.ulMinimum = 0;
	return NewBuffer(env, &CashIn, sizeof(WFSCIMCASHIN));
}
