#include "cim/at_o2xfs_xfs_cim_v3_00_CountsChanged3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCOUNTSCHANGED CountsChanged;
static USHORT usCUNumList[3] = { 1, 2, 3 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CountsChanged3Test_buildCountsChanged3(JNIEnv *env, jobject obj) {
	CountsChanged.usCount = 3;
	CountsChanged.lpusCUNumList = usCUNumList;
	return NewBuffer(env, &CountsChanged, sizeof(WFSCIMCOUNTSCHANGED));
}
