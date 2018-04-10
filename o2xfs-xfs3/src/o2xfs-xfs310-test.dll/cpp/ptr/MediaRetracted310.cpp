#include "ptr/at_o2xfs_xfs_v3_10_ptr_MediaRetracted310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRMEDIARETRACTED MediaRetracted;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_MediaRetracted310Test_buildMediaRetracted310(JNIEnv *env, jobject obj) {
	MediaRetracted.wRetractResult = WFS_PTR_AUTO_RETRACT_OK;
	MediaRetracted.usBinNumber = 1;
	return NewBuffer(env, &MediaRetracted, sizeof(WFSPTRMEDIARETRACTED));
}
