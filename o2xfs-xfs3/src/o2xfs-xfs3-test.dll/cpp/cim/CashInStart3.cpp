#include "cim/at_o2xfs_xfs_cim_v3_00_CashInStart3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINSTART CashInStart;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CashInStart3Test_buildCashInStart3(JNIEnv *env, jobject obj) {
	CashInStart.usTellerID = 0;
	CashInStart.bUseRecycleUnits = TRUE;
	CashInStart.fwOutputPosition = WFS_CIM_POSOUTTOP;
	CashInStart.fwInputPosition = WFS_CIM_POSINTOP;
	return NewBuffer(env, &CashInStart, sizeof(WFSCIMCASHINSTART));
}
