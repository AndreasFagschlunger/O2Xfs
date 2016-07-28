#include "cim/at_o2xfs_xfs_cim_v3_30_DepleteInfo3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMDEPINFO DepleteInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_DepleteInfo3_130Test_buildDepleteInfo3_130(JNIEnv *env, jobject obj) {
	DepleteInfo.usNumberTarget = 1;
	return NewBuffer(env, &DepleteInfo, sizeof(WFSCIMDEPINFO));
}
