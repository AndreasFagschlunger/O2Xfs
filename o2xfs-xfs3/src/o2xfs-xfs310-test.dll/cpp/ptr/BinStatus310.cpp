#include "ptr/at_o2xfs_xfs_v3_10_ptr_BinStatus310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRBINSTATUS BinStatus;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_BinStatus310Test_buildBinStatus310(JNIEnv *env, jobject obj) {
	BinStatus.usBinNumber = 1;
	BinStatus.wRetractBin = WFS_PTR_RETRACTBINREMOVED;
	return NewBuffer(env, &BinStatus, sizeof(WFSPTRBINSTATUS));
}
