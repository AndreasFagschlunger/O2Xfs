#include "at_o2xfs_xfs_cdm_v3_10_PowerSaveChange3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMPOWERSAVECHANGE PowerSaveChange;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_PowerSaveChange3_110Test_buildPowerSaveChange3_110(JNIEnv *env, jobject obj) {
	PowerSaveChange.usPowerSaveRecoveryTime = 5;
	return NewBuffer(env, &PowerSaveChange, sizeof(WFSCDMPOWERSAVECHANGE));
}