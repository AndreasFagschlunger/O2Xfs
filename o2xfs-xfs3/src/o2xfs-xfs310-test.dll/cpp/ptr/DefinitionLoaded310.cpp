#include "ptr/at_o2xfs_xfs_v3_10_ptr_DefinitionLoaded310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRDEFINITIONLOADED DefinitionLoaded;
static LPSTR DefinitionName = "XFSFORM";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_DefinitionLoaded310Test_buildDefinitionLoaded310(JNIEnv *env, jobject obj) {
	DefinitionLoaded.lpszDefinitionName = DefinitionName;
	DefinitionLoaded.dwDefinitionType = WFS_PTR_FORMLOADED;
	return NewBuffer(env, &DefinitionLoaded, sizeof(WFSPTRDEFINITIONLOADED));
}
