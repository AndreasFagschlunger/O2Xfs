#include "ptr/at_o2xfs_xfs_v3_10_ptr_SupplyReplenish310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRSUPPLYREPLEN SupplyReplen;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_SupplyReplenish310Test_buildSupplyReplenish310(JNIEnv *env, jobject obj) {
	SupplyReplen.fwSupplyReplen = WFS_PTR_REPLEN_PAPERUPPER | WFS_PTR_REPLEN_PAPERLOWER;
	return NewBuffer(env, &SupplyReplen, sizeof(WFSPTRSUPPLYREPLEN));
}
