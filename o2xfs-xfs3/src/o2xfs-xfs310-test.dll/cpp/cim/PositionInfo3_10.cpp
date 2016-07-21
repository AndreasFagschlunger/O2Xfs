#include "cim/at_o2xfs_xfs_cim_v3_10_PositionInfo3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMPOSITIONINFO PositionInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_PositionInfo3_110Test_buildPositionInfo3_110(JNIEnv *env, jobject obj) {
	PositionInfo.wPosition = WFS_CIM_POSINFRONT;
	PositionInfo.wAdditionalBunches = WFS_CIM_ADDBUNCHONEMORE;
	PositionInfo.usBunchesRemaining = 1;
	return NewBuffer(env, &PositionInfo, sizeof(WFSCIMPOSITIONINFO));
}
