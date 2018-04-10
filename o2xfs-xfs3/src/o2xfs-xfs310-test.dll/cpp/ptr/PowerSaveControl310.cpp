#include "ptr/at_o2xfs_xfs_v3_10_ptr_PowerSaveControl310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRPOWERSAVECONTROL PowerSaveControl;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_PowerSaveControl310Test_buildPowerSaveControl310(JNIEnv *env, jobject obj) {
	PowerSaveControl.usMaxPowerSaveRecoveryTime = 500;
	return NewBuffer(env, &PowerSaveControl, sizeof(WFSPTRPOWERSAVECONTROL));
}
