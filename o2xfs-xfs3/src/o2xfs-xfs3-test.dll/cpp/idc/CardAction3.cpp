#include "idc/at_o2xfs_xfs_v3_00_idc_CardAction3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCARDACT cardAct;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_CardAction3Test_buildCardAction3(JNIEnv *env, jobject obj) {
	cardAct.wAction = WFS_IDC_CARDRETAINED;
	cardAct.wPosition = WFS_IDC_MEDIAPRESENT;
	return NewBuffer(env, &cardAct, sizeof(WFSIDCCARDACT));
}
