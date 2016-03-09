#include "at_o2xfs_xfs_cdm_v3_30_ItemInfoSummary3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMITEMINFOSUMMARY ItemInfoSummary;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_ItemInfoSummary3_130Test_buildItemInfoSummary3_130(JNIEnv *env, jobject obj) {
	ItemInfoSummary.usLevel = WFS_CDM_LEVEL_3;
	ItemInfoSummary.usNumOfItems = 3;
	return NewBuffer(env, &ItemInfoSummary, sizeof(WFSCDMITEMINFOSUMMARY));
}