#include "idc/at_o2xfs_xfs_v3_30_idc_EmvClessTxData330Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCEMVCLESSTXDATA ClessTxData;
static WFSIDCHEXDATA Data;
static BYTE DataValue[] = { 0x5F, 0x2A, 0x03, 'E', 'U', 'R' };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_idc_EmvClessTxData330Test_buildEmvClessTxData330(JNIEnv *env, jobject obj) {
	Data.ulLength = 6;
	Data.lpbData = DataValue;
	ClessTxData.lpData = &Data;
	return NewBuffer(env, &ClessTxData, sizeof(WFSIDCEMVCLESSTXDATA));
}
