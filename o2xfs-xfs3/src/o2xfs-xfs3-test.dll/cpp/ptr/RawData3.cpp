#include "ptr/at_o2xfs_xfs_v3_00_ptr_RawData3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRRAWDATA RawData;
static BYTE Data[] = { 0xca, 0xfe, 0xba, 0xbe };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_RawData3Test_buildRawData3(JNIEnv *env, jobject obj) {
	RawData.wInputData = WFS_PTR_NOINPUTDATA;
	RawData.ulSize = sizeof(Data) / sizeof(*Data);
	RawData.lpbData = Data;
	return NewBuffer(env, &RawData, sizeof(WFSPTRRAWDATA));
}
