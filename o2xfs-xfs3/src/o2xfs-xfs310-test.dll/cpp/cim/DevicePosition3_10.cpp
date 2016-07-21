#include "cim/at_o2xfs_xfs_cim_v3_10_DevicePosition3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMDEVICEPOSITION DevicePosition;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_DevicePosition3_110Test_buildDevicePosition3_110(JNIEnv *env, jobject obj) {
	DevicePosition.wPosition = WFS_CIM_DEVICENOTINPOSITION;
	return NewBuffer(env, &DevicePosition, sizeof(WFSCIMDEVICEPOSITION));
}
