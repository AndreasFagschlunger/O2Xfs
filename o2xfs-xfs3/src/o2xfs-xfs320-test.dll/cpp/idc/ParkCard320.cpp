#include "idc/at_o2xfs_xfs_v3_20_idc_ParkCard320Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPARKCARD ParkCard;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_idc_ParkCard320Test_buildParkCard320(JNIEnv *env, jobject obj) {
	ParkCard.wDirection = WFS_IDC_PARK_IN;
	ParkCard.usParkingStation = 1;	
	return NewBuffer(env, &ParkCard, sizeof(WFSIDCPARKCARD));
}
