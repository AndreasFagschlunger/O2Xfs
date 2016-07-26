#include "cim/at_o2xfs_xfs_cim_v3_20_PositionCapability3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMPOSCAPS PosCapabilities;
LPSTR lpszExtra = "P6=2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_PositionCapability3_120Test_buildPositionCapability3_120(JNIEnv *env, jobject obj) {
	PosCapabilities.fwPosition = WFS_CIM_POSOUTFRONT;
	PosCapabilities.fwUsage = WFS_CIM_POSREFUSE | WFS_CIM_POSROLLBACK;
	PosCapabilities.bShutterControl = FALSE;
	PosCapabilities.bItemsTakenSensor = TRUE;
	PosCapabilities.bItemsInsertedSensor = TRUE;
	PosCapabilities.fwRetractAreas = WFS_CIM_RA_RETRACT | WFS_CIM_RA_TRANSPORT | WFS_CIM_RA_STACKER;
	PosCapabilities.lpszExtra = lpszExtra;
	PosCapabilities.bPresentControl = TRUE;
	return NewBuffer(env, &PosCapabilities, sizeof(WFSCIMPOSCAPS));
}
