#include "idc/at_o2xfs_xfs_idc_v3_00_RetainCard3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCRETAINCARD RetainCard;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_100_RetainCard3Test_buildRetainCard3(JNIEnv *env, jobject obj) {
	RetainCard.usCount = 3;
	RetainCard.fwPosition = WFS_IDC_MEDIAPRESENT;
	return NewBuffer(env, &RetainCard, sizeof(WFSIDCRETAINCARD));
}
