#include "cim/at_o2xfs_xfs_cim_v3_00_CashUnitError3Test.h"

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

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CashUnitError3Test_buildCashUnitError3(JNIEnv *env, jobject obj) {
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
