#include "ptr/at_o2xfs_xfs_v3_00_ptr_Reset3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRRESET Reset;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_Reset3Test_buildReset3(JNIEnv *env, jobject obj) {
	Reset.dwMediaControl = WFS_PTR_CTRLRETRACT;
	Reset.usRetractBinNumber = 1;
	return NewBuffer(env, &Reset, sizeof(WFSPTRRESET));
}
