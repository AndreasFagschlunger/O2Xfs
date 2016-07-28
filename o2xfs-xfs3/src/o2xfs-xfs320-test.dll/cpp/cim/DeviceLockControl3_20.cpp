#include "cim/at_o2xfs_xfs_cim_v3_20_DeviceLockControl3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMDEVICELOCKCONTROL DeviceLockControl;
static WFSCIMUNITLOCKCONTROL UnitLockControl[1];
static LPWFSCIMUNITLOCKCONTROL lpUnitLockControl[2];
static LPSTR lpPhysicalPositionName = "COMPARTMENT1";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_DeviceLockControl3_120Test_buildDeviceLockControl3_120(JNIEnv *env, jobject obj) {
	DeviceLockControl.wDeviceAction = WFS_CIM_LOCK;
	DeviceLockControl.wCashUnitAction = WFS_CIM_LOCKINDIVIDUAL;
	DeviceLockControl.lppUnitLockControl = lpUnitLockControl;
	lpUnitLockControl[0] = &UnitLockControl[0];
	UnitLockControl[0].lpPhysicalPositionName = lpPhysicalPositionName;
	UnitLockControl[0].wUnitAction = WFS_CIM_LOCK;
	lpUnitLockControl[1] = NULL;
	return NewBuffer(env, &DeviceLockControl, sizeof(WFSCIMDEVICELOCKCONTROL));
}
