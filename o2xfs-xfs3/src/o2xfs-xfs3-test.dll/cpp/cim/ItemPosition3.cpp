#include "cim/at_o2xfs_xfs_cim_v3_00_ItemPosition3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMITEMPOSITION ItemPosition;
static WFSCIMRETRACT RetractArea;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_ItemPosition3Test_buildItemPosition3(JNIEnv *env, jobject obj) {
	ItemPosition.usNumber = 1;
	ItemPosition.lpRetractArea = &RetractArea;
	RetractArea.fwOutputPosition = 0;
	RetractArea.usRetractArea = WFS_CIM_RA_RETRACT;
	RetractArea.usIndex = 0;
	ItemPosition.fwOutputPosition = WFS_CIM_POSOUTTOP;
	return NewBuffer(env, &ItemPosition, sizeof(WFSCIMITEMPOSITION));
}
