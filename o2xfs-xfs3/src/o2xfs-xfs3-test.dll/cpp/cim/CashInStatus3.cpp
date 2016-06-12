#include "cim/at_o2xfs_xfs_cim_v3_00_CashInStatus3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINSTATUS CashInStatus;
static WFSCIMNOTENUMBERLIST NoteNumberList;
static LPWFSCIMNOTENUMBER lppNoteNumber[1];
static WFSCIMNOTENUMBER NoteNumber[1];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CashInStatus3Test_buildCashInStatus3(JNIEnv *env, jobject obj) {
	lppNoteNumber[0] = &NoteNumber[0];

	CashInStatus.wStatus = WFS_CIM_CIOK;
	CashInStatus.usNumOfRefused = 3;
	CashInStatus.lpNoteNumberList = &NoteNumberList;
	CashInStatus.lpszExtra = NULL;

	NoteNumberList.usNumOfNoteNumbers = 1;
	NoteNumberList.lppNoteNumber = lppNoteNumber;

	NoteNumber[0].usNoteID = 1234;
	NoteNumber[0].ulCount = 1000;

	return NewBuffer(env, &CashInStatus, sizeof(WFSCIMCASHINSTATUS));
}
