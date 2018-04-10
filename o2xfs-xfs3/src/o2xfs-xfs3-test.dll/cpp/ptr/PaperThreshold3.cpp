#include "ptr/at_o2xfs_xfs_v3_00_ptr_PaperThreshold3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRPAPERTHRESHOLD PaperThreshold;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_PaperThreshold3Test_buildPaperThreshold3(JNIEnv *env, jobject obj) {
	PaperThreshold.wPaperSource = WFS_PTR_PAPERUPPER;
	PaperThreshold.wPaperThreshold = WFS_PTR_PAPERLOW;
	return NewBuffer(env, &PaperThreshold, sizeof(WFSPTRPAPERTHRESHOLD));
}
