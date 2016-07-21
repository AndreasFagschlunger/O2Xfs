#include "cim/at_o2xfs_xfs_cim_v3_10_ItemInfoSummary3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMITEMINFOSUMMARY ItemInfoSummary;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_ItemInfoSummary3_110Test_buildItemInfoSummary3_110(JNIEnv *env, jobject obj) {
	ItemInfoSummary.usLevel = WFS_CIM_LEVEL_2;
	ItemInfoSummary.usNumOfItems = 1234;
	return NewBuffer(env, &ItemInfoSummary, sizeof(WFSCIMITEMINFOSUMMARY));
}
