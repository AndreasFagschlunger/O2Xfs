#include "cim/at_o2xfs_xfs_cim_v3_10_GetItemInfo3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMGETITEMINFO GetItemInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_GetItemInfo3_110Test_buildGetItemInfo3_110(JNIEnv *env, jobject obj) {
	GetItemInfo.usLevel = WFS_CIM_LEVEL_2;
	GetItemInfo.usIndex = 0;
	GetItemInfo.dwItemInfoType = WFS_CIM_ITEM_SIGNATURE;
	return NewBuffer(env, &GetItemInfo, sizeof(WFSCIMGETITEMINFO));
}
