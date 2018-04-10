#include "ptr/at_o2xfs_xfs_v3_00_ptr_RetractBin3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRRETRACTBINS RetractBin;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_RetractBin3Test_buildRetractBin3(JNIEnv *env, jobject obj) {
	RetractBin.wRetractBin = WFS_PTR_RETRACTBINOK;
	RetractBin.usRetractCount = 3;
	return NewBuffer(env, &RetractBin, sizeof(WFSPTRRETRACTBINS));
}
