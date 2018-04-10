#include "ptr/at_o2xfs_xfs_v3_10_ptr_PowerSaveChange310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRPOWERSAVECHANGE PowerSaveChange;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_PowerSaveChange310Test_buildPowerSaveChange310(JNIEnv *env, jobject obj) {
	PowerSaveChange.usPowerSaveRecoveryTime = 100;
	return NewBuffer(env, &PowerSaveChange, sizeof(WFSPTRPOWERSAVECHANGE));
}
