#include "ptr/at_o2xfs_xfs_v3_10_ptr_HexData310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRXDATA HexData;
static BYTE Data[] = { 0xca, 0xfe, 0xba, 0xbe };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_HexData310Test_buildHexData310(JNIEnv *env, jobject obj) {
	HexData.usLength = sizeof(Data) / sizeof(*Data);
	HexData.lpbData = Data;
	return NewBuffer(env, &HexData, sizeof(WFSPTRXDATA));
}
