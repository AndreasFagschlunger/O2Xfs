#include "ptr/at_o2xfs_xfs_v3_10_ptr_MediaRejected310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIAREJECTED MediaRejected;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_MediaRejected310Test_buildMediaRejected310(JNIEnv *env, jobject obj) {
	MediaRejected.wMediaRejected = WFS_PTR_REJECT_LONG;
	return NewBuffer(env, &MediaRejected, sizeof(WFSPTRMEDIAREJECTED));
}
