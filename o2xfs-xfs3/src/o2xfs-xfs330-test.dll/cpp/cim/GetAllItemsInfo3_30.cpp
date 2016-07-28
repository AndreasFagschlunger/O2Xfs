#include "cim/at_o2xfs_xfs_cim_v3_30_GetAllItemsInfo3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMGETALLITEMSINFO GetAllItemsInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_GetAllItemsInfo3_130Test_buildGetAllItemsInfo3_130(JNIEnv *env, jobject obj) {
	GetAllItemsInfo.usLevel = WFS_CIM_LEVEL_ALL;
	return NewBuffer(env, &GetAllItemsInfo, sizeof(WFSCIMGETALLITEMSINFO));
}
