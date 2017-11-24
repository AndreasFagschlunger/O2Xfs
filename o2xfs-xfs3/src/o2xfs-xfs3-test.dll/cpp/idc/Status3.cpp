#include "idc/at_o2xfs_xfs_v3_00_idc_Status3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSTATUS Status;
static LPSTR lpszExtra = "0=Normal Operation\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_Status3Test_buildStatus3(JNIEnv *env, jobject obj) {
	Status.fwDevice = WFS_IDC_DEVONLINE;
	Status.fwMedia = WFS_IDC_MEDIANOTPRESENT;
	Status.fwRetainBin = WFS_IDC_RETAINBINOK;
	Status.fwSecurity = WFS_IDC_SECNOTSUPP;
	Status.usCards = 0;
	Status.fwChipPower = WFS_IDC_CHIPNOCARD;
	Status.lpszExtra = lpszExtra;
	return NewBuffer(env, &Status, sizeof(WFSIDCSTATUS));
}
