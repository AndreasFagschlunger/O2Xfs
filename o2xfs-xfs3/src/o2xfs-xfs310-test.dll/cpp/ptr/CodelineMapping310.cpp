#include "ptr/at_o2xfs_xfs_v3_10_ptr_CodelineMapping310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRCODELINEMAPPING CodelineMapping;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_CodelineMapping310Test_buildCodelineMapping310(JNIEnv *env, jobject obj) {
	CodelineMapping.wCodelineFormat = WFS_PTR_CODELINEE13B;
	return NewBuffer(env, &CodelineMapping, sizeof(WFSPTRCODELINEMAPPING));
}
