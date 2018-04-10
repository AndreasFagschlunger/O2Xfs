#include "ptr/at_o2xfs_xfs_v3_30_ptr_SetBlackMarkMode330Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRSETBLACKMARKMODE SetBlackMarkMode;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_ptr_SetBlackMarkMode330Test_buildSetBlackMarkMode330(JNIEnv *env, jobject obj) {
	SetBlackMarkMode.wBlackMarkMode = WFS_PTR_BLACKMARKDETECTIONOFF;
	return NewBuffer(env, &SetBlackMarkMode, sizeof(WFSPTRSETBLACKMARKMODE));
}
