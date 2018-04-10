#include "ptr/at_o2xfs_xfs_v3_10_ptr_SetGuidlight310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRSETGUIDLIGHT SetGuidLight;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_SetGuidlight310Test_buildSetGuidlight310(JNIEnv *env, jobject obj) {
	SetGuidLight.wGuidLight = WFS_PTR_GUIDANCE_PRINTER;
	SetGuidLight.dwCommand = WFS_PTR_GUIDANCE_RED | WFS_PTR_GUIDANCE_CONTINUOUS;
	return NewBuffer(env, &SetGuidLight, sizeof(WFSPTRSETGUIDLIGHT));
}
