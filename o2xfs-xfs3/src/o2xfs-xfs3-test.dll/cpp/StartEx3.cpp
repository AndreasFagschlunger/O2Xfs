#include "at_o2xfs_xfs_cdm_v3_00_StartEx3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSTARTEX startEx;
static USHORT usCUNumList[] = { 1, 2, 3 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_StartEx3Test_buildStartEx3(JNIEnv *env, jobject obj) {
	startEx.fwExchangeType = WFS_CDM_EXBYHAND;
	startEx.usTellerID = 0;
	startEx.usCount = 3;
	startEx.lpusCUNumList = usCUNumList;

	return NewBuffer(env, &startEx, sizeof(WFSCDMSTARTEX));
}