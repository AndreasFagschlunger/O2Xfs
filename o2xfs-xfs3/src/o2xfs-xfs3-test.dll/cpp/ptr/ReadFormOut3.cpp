#include "ptr/at_o2xfs_xfs_v3_00_ptr_ReadFormOut3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRREADFORMOUT ReadFormOut;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_ReadFormOut3Test_buildReadFormOut3(JNIEnv *env, jobject obj) {
	ReadFormOut.lpszFields = NULL;
	ReadFormOut.lpszUNICODEFields = NULL;
	return NewBuffer(env, &ReadFormOut, sizeof(WFSPTRREADFORMOUT));
}
