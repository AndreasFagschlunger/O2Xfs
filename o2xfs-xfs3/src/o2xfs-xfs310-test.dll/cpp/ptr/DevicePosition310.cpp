#include "ptr/at_o2xfs_xfs_v3_10_ptr_DevicePosition310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRDEVICEPOSITION DevicePosition;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_DevicePosition310Test_buildDevicePosition310(JNIEnv *env, jobject obj) {
	DevicePosition.wPosition = WFS_PTR_DEVICEINPOSITION;
	return NewBuffer(env, &DevicePosition, sizeof(WFSPTRDEVICEPOSITION));
}
