#include "cim/at_o2xfs_xfs_cim_v3_30_Deplete3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMDEP Deplete;
static WFSCIMDEPSOURCE DepleteSources[1];
static LPWFSCIMDEPSOURCE lpDepleteSources[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_Deplete3_130Test_buildDeplete3_130(JNIEnv *env, jobject obj) {	
	Deplete.lppDepleteSources = lpDepleteSources;
	lpDepleteSources[0] = &DepleteSources[0];
	DepleteSources[0].usNumberSource = 1;
	DepleteSources[0].ulNumberOfItemsToMove = 0;
	DepleteSources[0].bRemoveAll = TRUE;
	lpDepleteSources[1] = NULL;
	Deplete.usNumberTarget = 2;
	return NewBuffer(env, &Deplete, sizeof(WFSCIMDEP));
}
