#include "cim/at_o2xfs_xfs_cim_v3_00_GetP6Signature3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMGETP6SIGNATURE GetP6Signature;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_GetP6Signature3Test_buildGetP6Signature3(JNIEnv *env, jobject obj) {
	GetP6Signature.usLevel = WFS_CIM_LEVEL_2;
	GetP6Signature.usIndex = 1234;
	return NewBuffer(env, &GetP6Signature, sizeof(WFSCIMGETP6SIGNATURE));
}
