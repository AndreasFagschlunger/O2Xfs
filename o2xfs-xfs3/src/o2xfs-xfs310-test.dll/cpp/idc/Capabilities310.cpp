#include "idc/at_o2xfs_xfs_v3_10_idc_Capabilities310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCAPS caps;
static LPSTR lpszExtra = "XFS_MIB_VERSION=<0x00000001>\0";
static WORD MemoryChipProtocols[] = { WFS_IDC_MEM_SIEMENS4442, WFS_IDC_MEM_GPM896, NULL };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_Capabilities310Test_buildCapabilities310(JNIEnv *env, jobject obj) {
	caps.wClass = WFS_SERVICE_CLASS_IDC;
	caps.fwType = WFS_IDC_TYPEMOTOR;
	caps.bCompound = FALSE;
	caps.fwReadTracks = WFS_IDC_TRACK1 | WFS_IDC_TRACK2 | WFS_IDC_TRACK3 | WFS_IDC_CHIP;
	caps.fwWriteTracks = WFS_IDC_TRACK1 | WFS_IDC_TRACK2 | WFS_IDC_TRACK3;
	caps.fwChipProtocols = WFS_IDC_CHIPT0 | WFS_IDC_CHIPT1;
	caps.usCards = 31;
	caps.fwSecType = WFS_IDC_SECCIM86;
	caps.fwPowerOnOption = WFS_IDC_EJECT | WFS_IDC_RETAIN;
	caps.fwPowerOffOption = WFS_IDC_EJECT | WFS_IDC_RETAIN;
	caps.bFluxSensorProgrammable = TRUE;
	caps.fwWriteMode = WFS_IDC_LOCO | WFS_IDC_HICO;
	caps.fwChipPower = WFS_IDC_CHIPPOWERCOLD | WFS_IDC_CHIPPOWERWARM | WFS_IDC_CHIPPOWEROFF;
	caps.lpszExtra = lpszExtra;
	caps.fwDIPMode = WFS_IDC_DIP_ENTRY_EXIT;
	caps.lpwMemoryChipProtocols = MemoryChipProtocols;
	caps.dwGuidLights[WFS_IDC_GUIDANCE_CARDUNIT] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_MEDIUM_FLASH | WFS_IDC_GUIDANCE_QUICK_FLASH | WFS_IDC_GUIDANCE_CONTINUOUS | WFS_IDC_GUIDANCE_GREEN | WFS_IDC_GUIDANCE_RED | WFS_IDC_GUIDANCE_YELLOW;
	caps.dwGuidLights[WFS_IDC_GUIDLIGHTS_MAX] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_MEDIUM_FLASH | WFS_IDC_GUIDANCE_QUICK_FLASH | WFS_IDC_GUIDANCE_BLUE;
	caps.fwEjectPosition = WFS_IDC_EXITPOSITION | WFS_IDC_TRANSPORTPOSITION;
	caps.bPowerSaveControl = FALSE;
	return NewBuffer(env, &caps, sizeof(WFSIDCCAPS));
}
