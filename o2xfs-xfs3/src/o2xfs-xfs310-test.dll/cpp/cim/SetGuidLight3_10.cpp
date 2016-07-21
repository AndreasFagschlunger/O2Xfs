#include "cim/at_o2xfs_xfs_cim_v3_10_SetGuidLight3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMSETGUIDLIGHT SetGuidLight;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_SetGuidLight3_110Test_buildSetGuidLight3_110(JNIEnv *env, jobject obj) {
	SetGuidLight.wGuidLight = 1;
	SetGuidLight.dwCommand = WFS_CIM_GUIDANCE_MEDIUM_FLASH | WFS_CIM_GUIDANCE_GREEN;
	return NewBuffer(env, &SetGuidLight, sizeof(WFSCIMSETGUIDLIGHT));
}
