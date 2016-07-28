#include "cim/at_o2xfs_xfs_cim_v3_20_Replenish3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMREP Replenish;
static WFSCIMREPTARGET ReplenishTargets[1];
static LPWFSCIMREPTARGET lpReplenishTargets[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_Replenish3_120Test_buildReplenish3_120(JNIEnv *env, jobject obj) {
	Replenish.usNumberSource = 1;
	Replenish.lppReplenishTargets = lpReplenishTargets;
	lpReplenishTargets[0] = &ReplenishTargets[0];
	ReplenishTargets[0].usNumberTarget = 1234;
	ReplenishTargets[0].ulNumberOfItemsToMove = 4321;
	ReplenishTargets[0].bRemoveAll = FALSE;
	lpReplenishTargets[1] = NULL;
	return NewBuffer(env, &Replenish, sizeof(WFSCIMREP));
}
