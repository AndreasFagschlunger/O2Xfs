#include "ptr/at_o2xfs_xfs_v3_10_ptr_CodelineMappingOut310Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRCODELINEMAPPINGOUT CodelineMapping;
static WFSPTRXDATA CharMapping;
static BYTE Data[] = { 0, 1, 2, 3, 4 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_ptr_CodelineMappingOut310Test_buildCodelineMappingOut310(JNIEnv *env, jobject obj) {
	CodelineMapping.wCodelineFormat = WFS_PTR_CODELINEE13B;
	CodelineMapping.lpxCharMapping = &CharMapping;
	CharMapping.usLength = sizeof(Data) / sizeof(*Data);
	CharMapping.lpbData = Data;
	return NewBuffer(env, &CodelineMapping, sizeof(WFSPTRCODELINEMAPPINGOUT));
}
