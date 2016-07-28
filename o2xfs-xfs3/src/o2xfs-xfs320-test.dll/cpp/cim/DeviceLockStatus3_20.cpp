#include "cim/at_o2xfs_xfs_cim_v3_20_DeviceLockStatus3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMDEVICELOCKSTATUS DevLockStatus;
static WFSCIMCASHUNITLOCK CashUnitLock[1];
static LPWFSCIMCASHUNITLOCK lpCashUnitLock[2];
static LPSTR lpPhysicalPositionName = "COMPARTMENT2";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_DeviceLockStatus3_120Test_buildDeviceLockStatus3_120(JNIEnv *env, jobject obj) {
	DevLockStatus.wDeviceLockStatus = WFS_CIM_LOCK;
	DevLockStatus.lppCashUnitLock = lpCashUnitLock;
	lpCashUnitLock[0] = &CashUnitLock[0];
	CashUnitLock[0].lpPhysicalPositionName = lpPhysicalPositionName;
	CashUnitLock[0].wCashUnitLockStatus = WFS_CIM_LOCK;
	lpCashUnitLock[1] = NULL;
	return NewBuffer(env, &DevLockStatus, sizeof(WFSCIMDEVICELOCKSTATUS));
}
