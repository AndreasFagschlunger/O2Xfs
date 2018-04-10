#include "ptr/at_o2xfs_xfs_v3_20_ptr_ControlPassbook320Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRCONTROLPASSBOOK ControlPassbook;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_ptr_ControlPassbook320Test_buildControlPassbook320(JNIEnv *env, jobject obj) {
	ControlPassbook.wAction = WFS_PTR_PBKCTRLTURNBACKWARD;
	ControlPassbook.usCount = 0;
	return NewBuffer(env, &ControlPassbook, sizeof(WFSPTRCONTROLPASSBOOK));
}
