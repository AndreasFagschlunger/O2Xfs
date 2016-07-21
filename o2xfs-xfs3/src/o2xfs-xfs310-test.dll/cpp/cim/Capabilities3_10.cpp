#include "cim/at_o2xfs_xfs_cim_v3_10_Capabilities3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCAPS Caps;
static LPSTR lpszExtra = "Key=Value\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_Capabilities3_110Test_buildCapabilities3_110(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_CIM;
	Caps.fwType = WFS_CIM_SELFSERVICEBILL;
	Caps.wMaxCashInItems = 200;
	Caps.bCompound = TRUE;
	Caps.bShutter = TRUE;
	Caps.bShutterControl = FALSE;
	Caps.bSafeDoor = FALSE;
	Caps.bCashBox = FALSE;
	Caps.fwIntermediateStacker = 200;
	Caps.bItemsTakenSensor = TRUE;
	Caps.bItemsInsertedSensor = TRUE;
	Caps.fwPositions = WFS_CIM_POSINFRONT | WFS_CIM_POSOUTFRONT;
	Caps.fwExchangeType = WFS_CIM_EXBYHAND;
	Caps.fwRetractAreas = WFS_CIM_RA_TRANSPORT;
	Caps.fwRetractTransportActions = WFS_CIM_PRESENT | WFS_CIM_RETRACT;
	Caps.fwRetractStackerActions = WFS_CIM_NOTSUPP;
	Caps.lpszExtra = lpszExtra;
	Caps.dwGuidLights[WFS_CIM_POSINFRONT] = WFS_CIM_GUIDANCE_SLOW_FLASH | WFS_CIM_GUIDANCE_MEDIUM_FLASH | WFS_CIM_GUIDANCE_QUICK_FLASH | WFS_CIM_GUIDANCE_CONTINUOUS | WFS_CIM_GUIDANCE_GREEN | WFS_CIM_GUIDANCE_RED | WFS_CIM_GUIDANCE_YELLOW;
	Caps.dwItemInfoTypes = WFS_CIM_ITEM_SERIALNUMBER | WFS_CIM_ITEM_SIGNATURE;
	Caps.bCompareSignatures = FALSE;
	Caps.bPowerSaveControl = FALSE;
	return NewBuffer(env, &Caps, sizeof(WFSCIMCAPS));
}
