#include "cim/at_o2xfs_xfs_cim_v3_30_IncompleteDeplete3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMINCOMPLETEDEPLETE IncompleteDeplete;
static WFSCIMDEPRES Deplete;
static WFSCIMDEPSOURCERES DepleteSourceResults[1];
static LPWFSCIMDEPSOURCERES lpDepleteSourceResults[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_IncompleteDeplete3_130Test_buildIncompleteDeplete3_130(JNIEnv *env, jobject obj) {
	IncompleteDeplete.lpDeplete = &Deplete;
	Deplete.ulNumberOfItemsReceived = 1234;
	Deplete.ulNumberOfItemsRejected = 0;
	Deplete.lppDepleteSourceResults = lpDepleteSourceResults;
	lpDepleteSourceResults[0] = &DepleteSourceResults[0];
	DepleteSourceResults[0].usNumberSource = 1;
	DepleteSourceResults[0].usNoteID = 1234;
	DepleteSourceResults[0].ulNumberOfItemsRemoved = 1234;
	lpDepleteSourceResults[1] = NULL;
	return NewBuffer(env, &IncompleteDeplete, sizeof(WFSCIMINCOMPLETEDEPLETE));
}
