#include "cim/at_o2xfs_xfs_cim_v3_30_ItemInfo3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMITEMINFO ItemInfo;
static LPWSTR lpszSerialNumber = L"1234-5678-9012";
static WFSCIMP6SIGNATURE P6Signature;
static LPSTR lpszImageFileName = "C:\\note.png";
static char lpSignature[] = { 'C', 'A', 'F', 'E' };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_ItemInfo3_130Test_buildItemInfo3_130(JNIEnv *env, jobject obj) {
	ItemInfo.usNoteID = 1;
	ItemInfo.lpszSerialNumber = lpszSerialNumber;
	ItemInfo.lpP6Signature = &P6Signature;
	P6Signature.usNoteId = 1;
	P6Signature.ulLength = 4;
	P6Signature.dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signature.lpSignature = lpSignature;
	ItemInfo.lpszImageFileName = lpszImageFileName;
	return NewBuffer(env, &ItemInfo, sizeof(WFSCIMITEMINFO));
}
