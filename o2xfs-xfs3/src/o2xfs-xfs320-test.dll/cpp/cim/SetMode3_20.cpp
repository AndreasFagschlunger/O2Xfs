#include "cim/at_o2xfs_xfs_cim_v3_20_SetMode3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMSETMODE Mode;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_SetMode3_120Test_buildSetMode3_120(JNIEnv *env, jobject obj) {
	Mode.wMixedMode = WFS_CIM_IPMMIXEDMEDIA;
	return NewBuffer(env, &Mode, sizeof(WFSCIMSETMODE));
}
