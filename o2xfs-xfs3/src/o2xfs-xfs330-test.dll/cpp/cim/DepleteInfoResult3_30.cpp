#include "cim/at_o2xfs_xfs_cim_v3_30_DepleteInfoResult3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMDEPINFORES DepleteInfoResult;
static WFSCIMDEPINFOSOURCE DepleteSources[1];
static LPWFSCIMDEPINFOSOURCE lpDepleteSources[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_DepleteInfoResult3_130Test_buildDepleteInfoResult3_130(JNIEnv *env, jobject obj) {
	DepleteInfoResult.lppDepleteSources = lpDepleteSources;
	lpDepleteSources[0] = &DepleteSources[0];
	DepleteSources[0].usNumberSource = 1;
	lpDepleteSources[1] = NULL;
	return NewBuffer(env, &DepleteInfoResult, sizeof(WFSCIMDEPINFORES));
}
