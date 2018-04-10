#include "ptr/at_o2xfs_xfs_v3_10_ptr_MediaPresented310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIAPRESENTED MediaPresented;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_MediaPresented310Test_buildMediaPresented310(JNIEnv *env, jobject obj) {
	MediaPresented.usWadIndex = 1;
	MediaPresented.usTotalWads = 3;
	return NewBuffer(env, &MediaPresented, sizeof(WFSPTRMEDIAPRESENTED));
}
