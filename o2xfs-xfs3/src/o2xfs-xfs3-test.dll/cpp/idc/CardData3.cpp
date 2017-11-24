#include "idc/at_o2xfs_xfs_v3_00_idc_CardData3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCARDDATA cardData;
static UCHAR Data[] = { 0xCA, 0xFE, 0xBA, 0xBE };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_CardData3Test_buildCardData3(JNIEnv *env, jobject obj) {
	cardData.wDataSource = WFS_IDC_TRACK2;
	cardData.ulDataLength = 4;
	cardData.lpbData = Data;
	cardData.wStatus = WFS_IDC_DATAOK;
	return NewBuffer(env, &cardData, sizeof(WFSIDCCARDDATA));
}
