#include "cim/at_o2xfs_xfs_cim_v3_10_PositionCapabilities3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMPOSCAPABILITIES PosCaps;
WFSCIMPOSCAPS PosCapabilities[1];
LPWFSCIMPOSCAPS lpPosCapabilities[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_PositionCapabilities3_110Test_buildPositionCapabilities3_110(JNIEnv *env, jobject obj) {
	PosCaps.lppPosCapabilities = lpPosCapabilities;
	PosCapabilities[0].fwPosition = WFS_CIM_POSOUTFRONT;
	PosCapabilities[0].fwUsage = WFS_CIM_POSREFUSE | WFS_CIM_POSROLLBACK;
	PosCapabilities[0].bShutterControl = FALSE;
	PosCapabilities[0].bItemsTakenSensor = TRUE;
	PosCapabilities[0].bItemsInsertedSensor = TRUE;
	PosCapabilities[0].fwRetractAreas = WFS_CIM_RA_RETRACT | WFS_CIM_RA_TRANSPORT | WFS_CIM_RA_STACKER;
	PosCapabilities[0].lpszExtra = NULL;
	lpPosCapabilities[0] = &PosCapabilities[0];
	lpPosCapabilities[1] = NULL;
	return NewBuffer(env, &PosCaps, sizeof(WFSCIMPOSCAPS));
}
