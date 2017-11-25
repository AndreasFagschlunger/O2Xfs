#include "idc/at_o2xfs_xfs_v3_10_idc_PowerSaveControl310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPOWERSAVECONTROL PowerSaveControl;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_PowerSaveControl310Test_buildPowerSaveControl310(JNIEnv *env, jobject obj) {
	PowerSaveControl.usMaxPowerSaveRecoveryTime = 10;
	return NewBuffer(env, &PowerSaveControl, sizeof(WFSIDCPOWERSAVECONTROL));
}
