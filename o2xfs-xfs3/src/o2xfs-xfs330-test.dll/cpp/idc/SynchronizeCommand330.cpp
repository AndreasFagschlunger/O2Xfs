#include "idc/at_o2xfs_xfs_v3_30_idc_SynchronizeCommand330Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSYNCHRONIZECOMMAND SynchronizeCommand;
static WORD ReadData = WFS_IDC_TRACK2 | WFS_IDC_TRACK3;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_idc_SynchronizeCommand330Test_buildSynchronizeCommand330(JNIEnv *env, jobject obj) {
	SynchronizeCommand.dwCommand = WFS_CMD_IDC_READ_RAW_DATA;
	SynchronizeCommand.lpCmdData = &ReadData;
	return NewBuffer(env, &SynchronizeCommand, sizeof(WFSIDCSYNCHRONIZECOMMAND));
}
