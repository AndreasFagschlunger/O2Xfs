#include "cim/at_o2xfs_xfs_cim_v3_10_PowerSaveControl3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMPOWERSAVECONTROL PowerSaveControl;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_PowerSaveControl3_110Test_buildPowerSaveControl3_110(JNIEnv *env, jobject obj) {
	PowerSaveControl.usMaxPowerSaveRecoveryTime = 1234;
	return NewBuffer(env, &PowerSaveControl, sizeof(WFSCIMPOWERSAVECONTROL));
}
