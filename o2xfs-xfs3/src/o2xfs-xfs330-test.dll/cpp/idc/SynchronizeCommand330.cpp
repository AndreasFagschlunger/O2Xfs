#include "idc/at_o2xfs_xfs_idc_v3_30_SynchronizeCommand3_30Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSYNCHRONIZECOMMAND SynchronizeCommand;
static WORD ReadData = WFS_IDC_TRACK2 | WFS_IDC_TRACK3;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_130_SynchronizeCommand3_130Test_buildSynchronizeCommand3_130(JNIEnv *env, jobject obj) {
	SynchronizeCommand.dwCommand = WFS_CMD_IDC_READ_RAW_DATA;
	SynchronizeCommand.lpCmdData = &ReadData;
	return NewBuffer(env, &SynchronizeCommand, sizeof(WFSIDCSYNCHRONIZECOMMAND));
}
