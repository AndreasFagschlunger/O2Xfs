#include "cim/at_o2xfs_xfs_cim_v3_00_CashInType3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINTYPE cashInType;
static USHORT usNoteIDs[4];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CashInType3Test_buildCashInType3(JNIEnv *env, jobject obj) {
	cashInType.usNumber = 1;
	cashInType.dwType = WFS_CIM_CITYPINDIVIDUAL;
	cashInType.lpusNoteIDs = usNoteIDs;
	usNoteIDs[0] = 1;
	usNoteIDs[1] = 2;
	usNoteIDs[2] = 3;
	return NewBuffer(env, &cashInType, sizeof(WFSCIMCASHINTYPE));
}