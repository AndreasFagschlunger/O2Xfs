#include "cim/at_o2xfs_xfs_cim_v3_00_P6Info3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMP6INFO P6Info;
static USHORT usCUNumList[1];
static WFSCIMOUTPUT Output;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_P6Info3Test_buildP6Info3(JNIEnv *env, jobject obj) {
	P6Info.usLevel = WFS_CIM_LEVEL_2;
	P6Info.lpNoteNumberList = NULL;
	P6Info.usNumOfSignatures = 1;
	return NewBuffer(env, &P6Info, sizeof(WFSCIMP6INFO));
}