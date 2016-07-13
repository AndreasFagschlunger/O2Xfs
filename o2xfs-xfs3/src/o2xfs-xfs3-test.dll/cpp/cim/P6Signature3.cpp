#include "cim/at_o2xfs_xfs_cim_v3_00_P6Signature3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMP6SIGNATURE P6Signature;
static UCHAR Signature[] = { 0xCA, 0xFE, 0xBA, 0xBE };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_P6Signature3Test_buildP6Signature3(JNIEnv *env, jobject obj) {
	P6Signature.usNoteId = 1;
	P6Signature.ulLength = 4;
	P6Signature.dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signature.lpSignature = Signature;
	return NewBuffer(env, &P6Signature, sizeof(WFSCIMP6SIGNATURE));
}