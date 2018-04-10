#include "ptr/at_o2xfs_xfs_v3_30_ptr_SynchronizeCommand330Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRSYNCHRONIZECOMMAND SynchronizeCommand;
static WFSPTRREADFORMOUT ReadFormOut;
static LPSTR Fields = "FieldName=FieldValue\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_ptr_SynchronizeCommand330Test_buildSynchronizeCommand330(JNIEnv *env, jobject obj) {
	SynchronizeCommand.dwCommand = WFS_CMD_PTR_READ_FORM;
	ReadFormOut.lpszFields = Fields;
	ReadFormOut.lpszUNICODEFields = NULL;
	SynchronizeCommand.lpCmdData = &ReadFormOut;
	return NewBuffer(env, &SynchronizeCommand, sizeof(WFSPTRSYNCHRONIZECOMMAND));
}
