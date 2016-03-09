#include "at_o2xfs_xfs_cdm_v3_10_DevicePosition3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMDEVICEPOSITION DevicePosition;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_DevicePosition3_110Test_buildDevicePosition3_110(JNIEnv *env, jobject obj) {
	DevicePosition.wPosition = WFS_CDM_DEVICENOTINPOSITION;
	return NewBuffer(env, &DevicePosition, sizeof(WFSCDMDEVICEPOSITION));
}