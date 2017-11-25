#include "idc/at_o2xfs_xfs_v3_10_idc_PowerSaveChange310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPOWERSAVECHANGE PowerSaveChange;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_PowerSaveChange310Test_buildPowerSaveChange310(JNIEnv *env, jobject obj) {
	PowerSaveChange.usPowerSaveRecoveryTime = 3;
	return NewBuffer(env, &PowerSaveChange, sizeof(WFSIDCPOWERSAVECHANGE));
}
