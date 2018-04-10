#include "ptr/at_o2xfs_xfs_v3_00_ptr_MediaExt3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIAEXT MediaExt;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_MediaExt3Test_buildMediaExt3(JNIEnv *env, jobject obj) {
	MediaExt.ulSizeX = 800;
	MediaExt.ulSizeY = 600;
	return NewBuffer(env, &MediaExt, sizeof(WFSPTRMEDIAEXT));
}
