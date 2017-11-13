#include "idc/at_o2xfs_xfs_idc_v3_10_PowerSaveChange3_10Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPOWERSAVECHANGE PowerSaveChange;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_110_PowerSaveChange3_110Test_buildPowerSaveChange3_110(JNIEnv *env, jobject obj) {
	PowerSaveChange.usPowerSaveRecoveryTime = 3;
	return NewBuffer(env, &PowerSaveChange, sizeof(WFSIDCPOWERSAVECHANGE));
}
