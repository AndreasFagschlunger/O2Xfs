#include "idc/at_o2xfs_xfs_v3_10_idc_EjectCard310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCEJECTCARD EjectCard;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_EjectCard310Test_buildEjectCard310(JNIEnv *env, jobject obj) {
	EjectCard.wEjectPosition = WFS_IDC_EXITPOSITION;
	return NewBuffer(env, &EjectCard, sizeof(WFSIDCEJECTCARD));
}
