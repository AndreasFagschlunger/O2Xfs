#include "idc/at_o2xfs_xfs_v3_00_idc_Capabilities3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCAPS caps;
static LPSTR lpszExtra = "XFS_MIB_VERSION=<0x00000001>\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_Capabilities3Test_buildCapabilities3(JNIEnv *env, jobject obj) {
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
	return NewBuffer(env, &caps, sizeof(WFSIDCCAPS));
}
