#include "ptr/at_o2xfs_xfs_v3_00_ptr_MediaUnit3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIAUNIT MediaUnit;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_MediaUnit3Test_buildMediaUnit3(JNIEnv *env, jobject obj) {
	MediaUnit.wBase = WFS_FRM_ROWCOLUMN;
	MediaUnit.wUnitX = 10;
	MediaUnit.wUnitY = 20;
	return NewBuffer(env, &MediaUnit, sizeof(WFSPTRMEDIAUNIT));
}
