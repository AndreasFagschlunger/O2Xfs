#include "cim/at_o2xfs_xfs_cim_v3_00_CashInfo3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINFO CashInfo;
static WFSCIMCASHIN CashIn[9];
static LPWFSCIMCASHIN lpCashIn[9];
static WFSCIMPHCU Physical[9];
static LPWFSCIMPHCU lpPhysical[9];
static LPSTR PhysicalPositionName[] = { "COMPARTMENT1", "SLOT1", "SLOT2", "SLOT3", "SLOT4", "HEADUNIT", "COMPARTMENT3", "SLOT5", "COMPARTMENT2" };
static LPSTR Extra[] = { "Basetype=BIG_COMPARTMENT\0DispenseLockStatus=NOT_LOCKED\0", "Basetype=STD_CASSETTE\0" };
static WFSCIMNOTENUMBERLIST NoteNumberList[8];
static WFSCIMNOTENUMBER NoteNumber[8];
static LPWFSCIMNOTENUMBER lpNoteNumber[8];


JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CashInfo3Test_buildCashInfo3(JNIEnv *env, jobject obj) {
	CashInfo.usCount = 2;
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

	return NewBuffer(env, &CashInfo, sizeof(WFSCIMCASHINFO));
}
