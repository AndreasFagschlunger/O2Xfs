#include "cim/at_o2xfs_xfs_cim_v3_00_StartEx3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMSTARTEX StartEx;
static USHORT usCUNumList[1];
static WFSCIMOUTPUT Output;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_StartEx3Test_buildStartEx3(JNIEnv *env, jobject obj) {
	StartEx.fwExchangeType = WFS_CIM_CLEARRECYCLER;
	StartEx.usTellerID = 0;
	StartEx.usCount = 1;
	StartEx.lpusCUNumList = usCUNumList;
	usCUNumList[0] = 1;
	StartEx.lpOutput = &Output;
	Output.usLogicalNumber = 1;
	Output.fwPosition = WFS_CIM_POSOUTFRONT;
	Output.usNumber = 1;
	return NewBuffer(env, &StartEx, sizeof(WFSCIMSTARTEX));
}