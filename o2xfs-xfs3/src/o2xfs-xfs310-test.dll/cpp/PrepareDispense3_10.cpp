#include "at_o2xfs_xfs_cdm_v3_10_PrepareDispense3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMPREPAREDISPENSE PrepareDispense;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_PrepareDispense3_110Test_buildPrepareDispense3_110(JNIEnv *env, jobject obj) {
	PrepareDispense.wAction = WFS_CDM_START;
	return NewBuffer(env, &PrepareDispense, sizeof(WFSCDMPREPAREDISPENSE));
}