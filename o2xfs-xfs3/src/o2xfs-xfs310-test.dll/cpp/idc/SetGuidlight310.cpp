#include "idc/at_o2xfs_xfs_v3_10_idc_SetGuidlight310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSETGUIDLIGHT SetGuidLight;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_SetGuidlight310Test_buildSetGuidlight310(JNIEnv *env, jobject obj) {
	SetGuidLight.wGuidLight = WFS_IDC_GUIDANCE_CARDUNIT;
	SetGuidLight.dwCommand = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_BLUE;
	return NewBuffer(env, &SetGuidLight, sizeof(WFSIDCSETGUIDLIGHT));
}
