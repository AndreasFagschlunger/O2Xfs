#include "cim/at_o2xfs_xfs_cim_v3_00_Status3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMSTATUS Status;
static WFSCIMINPOS Positions[2];
static LPWFSCIMINPOS lppPositions[3];
static LPSTR lpszExtra = "LASTERROR=StClass=00000000\0StCode=00000000\0LastErrorText=OK:none\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_Status3Test_buildStatus3(JNIEnv *env, jobject obj) {
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

	return NewBuffer(env, &Status, sizeof(WFSCIMSTATUS));
}