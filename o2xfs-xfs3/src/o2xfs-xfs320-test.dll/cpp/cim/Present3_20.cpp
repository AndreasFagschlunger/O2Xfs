#include "cim/at_o2xfs_xfs_cim_v3_20_Present3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMPRESENT Present;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_Present3_120Test_buildPresent3_120(JNIEnv *env, jobject obj) {
	Present.fwPosition = WFS_CIM_POSOUTFRONT;
	return NewBuffer(env, &Present, sizeof(WFSCIMPRESENT));
}
