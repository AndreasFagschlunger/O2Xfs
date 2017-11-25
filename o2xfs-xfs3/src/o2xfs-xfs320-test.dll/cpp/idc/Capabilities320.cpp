#include "idc/at_o2xfs_xfs_v3_20_idc_Capabilities320Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCAPS Caps;
static LPSTR lpszExtra = "XFS_MIB_VERSION=<0x00000001>\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_idc_Capabilities320Test_buildCapabilities320(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_IDC;
	Caps.fwType = WFS_IDC_TYPEMOTOR;
	Caps.bCompound = FALSE;
	Caps.fwReadTracks = WFS_IDC_TRACK1 | WFS_IDC_TRACK2 | WFS_IDC_TRACK3 | WFS_IDC_CHIP;
	Caps.fwWriteTracks = WFS_IDC_TRACK1 | WFS_IDC_TRACK2 | WFS_IDC_TRACK3;
	Caps.fwChipProtocols = WFS_IDC_CHIPT0 | WFS_IDC_CHIPT1;
	Caps.usCards = 31;
	Caps.fwSecType = WFS_IDC_SECCIM86;
	Caps.fwPowerOnOption = WFS_IDC_EJECT | WFS_IDC_RETAIN;
	Caps.fwPowerOffOption = WFS_IDC_EJECT | WFS_IDC_RETAIN;
	Caps.bFluxSensorProgrammable = TRUE;
	Caps.fwWriteMode = WFS_IDC_LOCO | WFS_IDC_HICO;
	Caps.fwChipPower = WFS_IDC_CHIPPOWERCOLD | WFS_IDC_CHIPPOWERWARM | WFS_IDC_CHIPPOWEROFF;
	Caps.lpszExtra = lpszExtra;
	Caps.fwDIPMode = WFS_IDC_DIP_ENTRY_EXIT;
	Caps.lpwMemoryChipProtocols = NULL;
	Caps.dwGuidLights[WFS_IDC_GUIDANCE_CARDUNIT] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_MEDIUM_FLASH | WFS_IDC_GUIDANCE_QUICK_FLASH | WFS_IDC_GUIDANCE_CONTINUOUS | WFS_IDC_GUIDANCE_GREEN | WFS_IDC_GUIDANCE_RED | WFS_IDC_GUIDANCE_YELLOW;
	Caps.dwGuidLights[WFS_IDC_GUIDLIGHTS_MAX] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_MEDIUM_FLASH | WFS_IDC_GUIDANCE_QUICK_FLASH | WFS_IDC_GUIDANCE_BLUE;
	Caps.fwEjectPosition = WFS_IDC_EXITPOSITION | WFS_IDC_TRANSPORTPOSITION;
	Caps.bPowerSaveControl = FALSE;
	Caps.usParkingStations = 1;
	Caps.bAntiFraudModule = TRUE;
	return NewBuffer(env, &Caps, sizeof(WFSIDCCAPS));
}
