#include "cim/at_o2xfs_xfs_cim_v3_20_Count3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMCOUNT Count;
USHORT usCUNumList[] = { 1, 2, 3 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_Count3_120Test_buildCount3_120(JNIEnv *env, jobject obj) {
	Count.usCount = 3;
	Count.lpusCUNumList = usCUNumList;
	return NewBuffer(env, &Count, sizeof(WFSCIMCOUNT));
}
