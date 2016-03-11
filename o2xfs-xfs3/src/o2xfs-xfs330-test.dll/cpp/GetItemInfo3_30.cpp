#include "at_o2xfs_xfs_cdm_v3_30_GetItemInfo3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMGETITEMINFO GetItemInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_GetItemInfo3_130Test_buildGetItemInfo3_130(JNIEnv *env, jobject obj) {
	GetItemInfo.usLevel = WFS_CDM_LEVEL_2;
	GetItemInfo.usIndex = 0;
	GetItemInfo.dwItemInfoType = WFS_CDM_ITEM_SERIALNUMBER;
	return NewBuffer(env, &GetItemInfo, sizeof(WFSCDMGETITEMINFO));
}