#include "cim/at_o2xfs_xfs_cim_v3_20_CashCapabilities3_20Test.h"

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

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_CashCapabilities3_120Test_buildCashCapabilities3_120(JNIEnv *env, jobject obj) {
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
