#include "at_o2xfs_xfs_cdm_v3_30_GetAllItemsInfo3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMGETALLITEMSINFO GetAllItemsInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_GetAllItemsInfo3_130Test_buildGetAllItemsInfo3_130(JNIEnv *env, jobject obj) {
	GetAllItemsInfo.usLevel = WFS_CDM_LEVEL_4;
	return NewBuffer(env, &GetAllItemsInfo, sizeof(WFSCDMGETALLITEMSINFO));
}