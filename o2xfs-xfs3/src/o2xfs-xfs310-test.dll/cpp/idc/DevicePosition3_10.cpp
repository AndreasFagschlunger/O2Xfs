#include "idc/at_o2xfs_xfs_idc_v3_10_DevicePosition3_10Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCDEVICEPOSITION DevicePosition;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_110_DevicePosition3_110Test_buildDevicePosition3_110(JNIEnv *env, jobject obj) {
	DevicePosition.wPosition = WFS_IDC_DEVICENOTINPOSITION;
	return NewBuffer(env, &DevicePosition, sizeof(WFSIDCDEVICEPOSITION));
}
