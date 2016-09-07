#include "bcr/at_o2xfs_xfs_bcr_v3_10_Capabilities3_10Test.h"

#include <Windows.h>
#include <XFSBCR.H>
#include "at.o2xfs.win32.h"

static WFSBCRCAPS Caps;
static WORD wSymbologies[] = { WFS_BCR_SYM_EAN128, WFS_BCR_SYM_JAN13, 0 };
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_bcr_v3_110_Capabilities3_110Test_buildCapabilities3_110(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_BCR;
	Caps.bCompound = FALSE;
	Caps.bCanFilterSymbologies = TRUE;
	Caps.lpwSymbologies = wSymbologies;
	Caps.dwGuidLights[WFS_BCR_GUIDANCE_BCR] = WFS_BCR_GUIDANCE_MEDIUM_FLASH | WFS_BCR_GUIDANCE_GREEN;
	Caps.lpszExtra = lpszExtra;
	Caps.bPowerSaveControl = TRUE;
	return NewBuffer(env, &Caps, sizeof(WFSBCRCAPS));
}
