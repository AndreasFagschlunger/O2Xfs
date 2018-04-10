#include "ptr/at_o2xfs_xfs_v3_00_ptr_BinThreshold3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRBINTHRESHOLD BinThreshold;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_BinThreshold3Test_buildBinThreshold3(JNIEnv *env, jobject obj) {
	BinThreshold.usBinNumber = 1;
	BinThreshold.wRetractBin = WFS_PTR_RETRACTBINHIGH;
	return NewBuffer(env, &BinThreshold, sizeof(WFSPTRBINTHRESHOLD));
}
