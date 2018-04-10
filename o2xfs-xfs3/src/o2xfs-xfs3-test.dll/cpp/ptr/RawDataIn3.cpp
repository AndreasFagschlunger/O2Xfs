#include "ptr/at_o2xfs_xfs_v3_00_ptr_RawDataIn3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRRAWDATAIN RawDataIn;
static BYTE Data[] = { 0xca, 0xfe, 0xba, 0xbe };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_RawDataIn3Test_buildRawDataIn3(JNIEnv *env, jobject obj) {
	RawDataIn.ulSize = sizeof(Data) / sizeof(*Data);
	RawDataIn.lpbData = Data;
	return NewBuffer(env, &RawDataIn, sizeof(WFSPTRRAWDATAIN));
}
