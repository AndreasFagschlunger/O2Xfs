#include "cim/at_o2xfs_xfs_cim_v3_00_Capabilities3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCAPS caps;
static LPSTR lpszExtra = "P6=2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_Capabilities3Test_buildCapabilities3(JNIEnv *env, jobject obj) {
	caps.wClass = WFS_SERVICE_CLASS_CIM;
	caps.fwType = WFS_CIM_SELFSERVICEBILL;
	caps.wMaxCashInItems = 200;
	caps.bCompound = TRUE;
	caps.bShutter = TRUE;
	caps.bShutterControl = TRUE;
	caps.bSafeDoor = TRUE;
	caps.bCashBox = FALSE;
	caps.fwIntermediateStacker = 200;
	caps.bItemsTakenSensor = TRUE;
	caps.bItemsInsertedSensor = TRUE;
	caps.fwPositions = WFS_CIM_POSINTOP | WFS_CIM_POSOUTTOP;
	caps.fwExchangeType = WFS_CIM_EXBYHAND;
	caps.fwRetractAreas = WFS_CIM_RA_RETRACT | WFS_CIM_RA_TRANSPORT | WFS_CIM_RA_STACKER;
	caps.fwRetractTransportActions = WFS_CIM_RETRACT;
	caps.fwRetractStackerActions = WFS_CIM_RETRACT;
	caps.lpszExtra = lpszExtra;
	return NewBuffer(env, &caps, sizeof(WFSCIMCAPS));
}
