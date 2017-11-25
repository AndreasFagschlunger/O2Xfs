#include "idc/at_o2xfs_xfs_v3_20_idc_Status320Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSTATUS Status;
static LPSTR lpszExtra = "0=Normal Operation\0";
static WORD ParkingStationMedia[] = { WFS_IDC_MEDIAPRESENT, NULL };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_idc_Status320Test_buildStatus320(JNIEnv *env, jobject obj) {
	Status.fwDevice = WFS_IDC_DEVONLINE;
	Status.fwMedia = WFS_IDC_MEDIANOTPRESENT;
	Status.fwRetainBin = WFS_IDC_RETAINBINOK;
	Status.fwSecurity = WFS_IDC_SECNOTSUPP;
	Status.usCards = 0;
	Status.fwChipPower = WFS_IDC_CHIPNOCARD;
	Status.lpszExtra = lpszExtra;
	Status.dwGuidLights[WFS_IDC_GUIDANCE_CARDUNIT] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_GREEN;
	Status.dwGuidLights[WFS_IDC_GUIDLIGHTS_MAX] = WFS_IDC_GUIDANCE_SLOW_FLASH | WFS_IDC_GUIDANCE_GREEN;
	Status.fwChipModule = WFS_IDC_CHIPMODOK;
	Status.fwMagReadModule = WFS_IDC_MAGMODOK;
	Status.fwMagWriteModule = WFS_IDC_MAGMODNOTSUPP;
	Status.fwFrontImageModule = WFS_IDC_IMGMODOK;
	Status.fwBackImageModule = WFS_IDC_IMGMODNOTSUPP;
	Status.wDevicePosition = WFS_IDC_DEVICEINPOSITION;
	Status.usPowerSaveRecoveryTime = 3;
	Status.lpwParkingStationMedia = ParkingStationMedia;
	Status.wAntiFraudModule = WFS_IDC_AFMOK;
	return NewBuffer(env, &Status, sizeof(WFSIDCSTATUS));
}
