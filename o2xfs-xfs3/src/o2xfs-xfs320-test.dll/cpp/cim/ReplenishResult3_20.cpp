#include "cim/at_o2xfs_xfs_cim_v3_20_ReplenishResult3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMREPRES ReplenishResult;
static WFSCIMREPTARGETRES ReplenishTargetResults[1];
static LPWFSCIMREPTARGETRES lpReplenishTargetResults[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_ReplenishResult3_120Test_buildReplenishResult3_120(JNIEnv *env, jobject obj) {
	ReplenishResult.ulNumberOfItemsRemoved = 1234;
	ReplenishResult.ulNumberOfItemsRejected = 1;
	ReplenishResult.lppReplenishTargetResults = lpReplenishTargetResults;
	lpReplenishTargetResults[0] = &ReplenishTargetResults[0];
	ReplenishTargetResults[0].usNumberTarget = 1234;
	ReplenishTargetResults[0].usNoteID = 1;
	ReplenishTargetResults[0].ulNumberOfItemsReceived = 4321;
	lpReplenishTargetResults[1] = NULL;
	return NewBuffer(env, &ReplenishResult, sizeof(WFSCIMREPRES));
}
