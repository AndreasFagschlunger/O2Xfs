#include "at_o2xfs_xfs_cdm_v3_10_SetGuidLight3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSETGUIDLIGHT SetGuidLight;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_SetGuidLight3_110Test_buildSetGuidLight3_110(JNIEnv *env, jobject obj) {
	SetGuidLight.wGuidLight = WFS_CDM_GUIDANCE_POSOUTNULL;
	SetGuidLight.dwCommand = WFS_CDM_GUIDANCE_MEDIUM_FLASH | WFS_CDM_GUIDANCE_GREEN;

	return NewBuffer(env, &SetGuidLight, sizeof(WFSCDMSETGUIDLIGHT));
}