#include "cim/at_o2xfs_xfs_cim_v3_10_Status3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMSTATUS Status;
WFSCIMINPOS Positions[2];
LPWFSCIMINPOS lppPositions[3];
LPSTR lpszExtra = "LASTERROR=StClass=00000000\0StCode=00000000\0LastErrorText=OK:none\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_Status3_110Test_buildStatus3_110(JNIEnv *env, jobject obj) {

	Status.fwDevice = WFS_CIM_DEVONLINE;
	Status.fwSafeDoor = WFS_CIM_DOORCLOSED;
	Status.fwAcceptor = WFS_CIM_ACCOK;
	Status.fwIntermediateStacker = WFS_CIM_ISEMPTY;
	Status.fwStackerItems = WFS_CIM_NOITEMS;
	Status.fwBanknoteReader = WFS_CIM_BNROK;
	Status.bDropBox = FALSE;
	Status.lppPositions = lppPositions;
	Status.lpszExtra = lpszExtra;

	int positionIndex = 0;
	lppPositions[positionIndex] = &Positions[positionIndex];
	Positions[positionIndex].fwPosition = WFS_CIM_POSOUTFRONT;
	Positions[positionIndex].fwShutter = WFS_CIM_SHTCLOSED;
	Positions[positionIndex].fwPositionStatus = WFS_CIM_PSEMPTY;
	Positions[positionIndex].fwTransport = WFS_CIM_TPOK;
	Positions[positionIndex].fwTransportStatus = WFS_CIM_TPSTATEMPTY;	
	
	positionIndex++;
	lppPositions[positionIndex] = &Positions[positionIndex];
	Positions[positionIndex].fwPosition = WFS_CIM_POSINFRONT;
	Positions[positionIndex].fwShutter = WFS_CIM_SHTCLOSED;
	Positions[positionIndex].fwPositionStatus = WFS_CIM_PSEMPTY;
	Positions[positionIndex].fwTransport = WFS_CIM_TPOK;
	Positions[positionIndex].fwTransportStatus = WFS_CIM_TPSTATNOTSUPPORTED;
	
	positionIndex++;
	lppPositions[positionIndex] = NULL;
	Status.dwGuidLights[WFS_CIM_GUIDANCE_POSINFRONT] = WFS_CIM_GUIDANCE_MEDIUM_FLASH | WFS_CIM_GUIDANCE_BLUE;
	Status.wDevicePosition = WFS_CIM_DEVICEINPOSITION;
	Status.usPowerSaveRecoveryTime = 0;
	return NewBuffer(env, &Status, sizeof(WFSCIMSTATUS));
}
