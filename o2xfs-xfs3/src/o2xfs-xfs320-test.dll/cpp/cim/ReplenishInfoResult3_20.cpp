#include "cim/at_o2xfs_xfs_cim_v3_20_ReplenishInfoResult3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMREPINFORES ReplenishInfoResult;
static WFSCIMREPINFOTARGET ReplenishTargets[1];
static LPWFSCIMREPINFOTARGET lpReplenishTargets[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_ReplenishInfoResult3_120Test_buildReplenishInfoResult3_120(JNIEnv *env, jobject obj) {
	ReplenishInfoResult.lppReplenishTargets = lpReplenishTargets;
	lpReplenishTargets[0] = &ReplenishTargets[0];
	ReplenishTargets[0].usNumberTarget = 1234;
	lpReplenishTargets[1] = NULL;
	return NewBuffer(env, &ReplenishInfoResult, sizeof(WFSCIMREPINFORES));
}
