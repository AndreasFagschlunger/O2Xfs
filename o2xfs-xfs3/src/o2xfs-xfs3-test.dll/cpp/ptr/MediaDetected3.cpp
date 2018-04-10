#include "ptr/at_o2xfs_xfs_v3_00_ptr_MediaDetected3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIADETECTED MediaDetected;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_MediaDetected3Test_buildMediaDetected3(JNIEnv *env, jobject obj) {
	MediaDetected.wPosition = WFS_PTR_MEDIARETRACTED;
	MediaDetected.usRetractBinNumber = 1;
	return NewBuffer(env, &MediaDetected, sizeof(WFSPTRMEDIADETECTED));
}
