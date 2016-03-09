#include "at_o2xfs_xfs_cdm_v3_00_CountsChanged3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCOUNTSCHANGED CountsChanged;
static USHORT usCUNumList[] = { 1, 2, 3 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_CountsChanged3Test_buildCountsChanged3(JNIEnv *env, jobject obj) {
	CountsChanged.usCount = 3;
	CountsChanged.lpusCUNumList = usCUNumList;
	return NewBuffer(env, &CountsChanged, sizeof(WFSCDMCOUNTSCHANGED));
}