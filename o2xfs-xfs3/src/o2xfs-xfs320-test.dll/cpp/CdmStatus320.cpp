#include "at_o2xfs_xfs_cdm_v3_20_CdmStatus3_20Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSTATUS status;
static WFSCDMOUTPOS position;
static LPWFSCDMOUTPOS positions[2];
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_120_CdmStatus3_120Test_buildCdmStatus3_120(JNIEnv *env, jobject object) {
	position.fwPosition = WFS_CDM_POSFRONT;
	position.fwShutter = WFS_CDM_SHTCLOSED;
	position.fwPositionStatus = WFS_CDM_PSEMPTY;
	position.fwTransport = WFS_CDM_TPOK;
	position.fwTransportStatus = WFS_CDM_TPSTATEMPTY;
	positions[0] = &position;
	
	status.fwDevice = WFS_CDM_DEVONLINE;
	status.fwDispenser = WFS_CDM_DISPOK;
	status.fwIntermediateStacker = WFS_CDM_ISEMPTY;
	status.fwSafeDoor = WFS_CDM_DOORCLOSED;
	status.lppPositions = positions;
	status.lpszExtra = lpszExtra;
	status.dwGuidLights[WFS_CDM_GUIDANCE_POSOUTFRONT] = WFS_CDM_GUIDANCE_MEDIUM_FLASH | WFS_CDM_GUIDANCE_GREEN;
	status.wDevicePosition = WFS_CDM_DEVICEINPOSITION;
	status.usPowerSaveRecoveryTime = 3;
	status.wAntiFraudModule = WFS_CDM_AFMOK;

	return NewBuffer(env, &status, sizeof(WFSCDMSTATUS));
}