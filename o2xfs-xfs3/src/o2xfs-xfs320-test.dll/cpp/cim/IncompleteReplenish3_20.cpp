#include "cim/at_o2xfs_xfs_cim_v3_20_IncompleteReplenish3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMINCOMPLETEREPLENISH IncompleteReplenish;
static WFSCIMREPRES Replenish;
static WFSCIMREPTARGETRES ReplenishTargetResults[1];
static LPWFSCIMREPTARGETRES lpReplenishTargetResults[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_IncompleteReplenish3_120Test_buildIncompleteReplenish3_120(JNIEnv *env, jobject obj) {
	IncompleteReplenish.lpReplenish = &Replenish;
	Replenish.ulNumberOfItemsRemoved = 1234;
	Replenish.ulNumberOfItemsRejected = 1;
	Replenish.lppReplenishTargetResults = lpReplenishTargetResults;
	lpReplenishTargetResults[0] = &ReplenishTargetResults[0];
	ReplenishTargetResults[0].usNumberTarget = 1;
	ReplenishTargetResults[0].usNoteID = 1234;
	ReplenishTargetResults[0].ulNumberOfItemsReceived = 4321;
	lpReplenishTargetResults[1] = NULL;
	return NewBuffer(env, &IncompleteReplenish, sizeof(WFSCIMINCOMPLETEREPLENISH));
}
