#include "idc/at_o2xfs_xfs_v3_10_idc_DevicePosition310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCDEVICEPOSITION DevicePosition;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_DevicePosition310Test_buildDevicePosition310(JNIEnv *env, jobject obj) {
	DevicePosition.wPosition = WFS_IDC_DEVICENOTINPOSITION;
	return NewBuffer(env, &DevicePosition, sizeof(WFSIDCDEVICEPOSITION));
}
