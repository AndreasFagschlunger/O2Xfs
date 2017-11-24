#include "idc/at_o2xfs_xfs_idc_v3_20_ParkCard3_20Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPARKCARD ParkCard;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_120_ParkCard3_120Test_buildParkCard3_120(JNIEnv *env, jobject obj) {
	ParkCard.wDirection = WFS_IDC_PARK_IN;
	ParkCard.usParkingStation = 1;	
	return NewBuffer(env, &ParkCard, sizeof(WFSIDCPARKCARD));
}
