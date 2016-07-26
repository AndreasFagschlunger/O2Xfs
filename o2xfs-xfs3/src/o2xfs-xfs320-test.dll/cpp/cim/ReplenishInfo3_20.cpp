#include "cim/at_o2xfs_xfs_cim_v3_20_ReplenishInfo3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMREPINFO ReplenishInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_ReplenishInfo3_120Test_buildReplenishInfo3_120(JNIEnv *env, jobject obj) {
	ReplenishInfo.usNumberSource = 1;
	return NewBuffer(env, &ReplenishInfo, sizeof(WFSCIMREPINFO));
}
