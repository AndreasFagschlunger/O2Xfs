#include "ptr/at_o2xfs_xfs_v3_10_ptr_LoadDefinition310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRLOADDEFINITION LoadDefinition;
static LPSTR FileName = "C:\\Temp\\FrontImage.bmp";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_LoadDefinition310Test_buildLoadDefinition310(JNIEnv *env, jobject obj) {
	LoadDefinition.lpszFileName = "";
	LoadDefinition.bOverwrite = TRUE;
	return NewBuffer(env, &LoadDefinition, sizeof(WFSPTRLOADDEFINITION));
}
